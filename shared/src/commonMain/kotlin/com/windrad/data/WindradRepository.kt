package com.windrad.data

import com.windrad.database.WindradDatabase
import com.windrad.ui.map.WindmillDetails
import com.windrad.ui.map.SearchSuggestion
import com.windrad.ui.map.NearbyWindmill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Repository für den Zugriff auf Windrad-Daten aus der SQLite-Datenbank.
 *
 * Hauptaufgabe: Alle Windräder aus der DB laden und als GeoJSON-FeatureCollection
 * (JSON-String) zurückgeben, damit MapLibre das native Clustering übernehmen kann.
 */
class WindradRepository(private val db: WindradDatabase) {

    /**
     * Lädt alle Windräder mit gültigen Koordinaten und erzeugt eine
     * GeoJSON-FeatureCollection als String.
     *
     * Jedes Feature enthält folgende Properties:
     *   - id         : unit_MaStR_ID        (String)
     *   - name       : unit_display_name    (String | null)
     *   - collected  : 0 oder 1            (Long)
     *
     * Das `collected`-Property ist bereits vorbereitet, damit spätere
     * Implementierungen die Farbgebung (gesammelt = grün) einfach hinzufügen können.
     *
     * @return GeoJSON-FeatureCollection als String (kompatibel mit GeoJsonData.JsonString)
     */
    suspend fun loadAllWindmillsAsGeoJson(): String = withContext(Dispatchers.Default) {
        // selectWindmillCoordinates liefert: id, label, latitude, longitude, collected
        val rows = db.windradDbQueries.selectWindmillCoordinates().executeAsList()
        println("WindradRepository: ${rows.size} Windräder aus DB geladen, baue GeoJSON...")

        buildString(capacity = rows.size * 120) {
            append("""{"type":"FeatureCollection","features":[""")
            rows.forEachIndexed { index, row ->
                if (index > 0) append(',')

                // GeoJSON-Koordinaten: [Longitude, Latitude]  (NICHT lat/lon – das ist GeoJSON-Spec!)
                append("""{"type":"Feature","geometry":{"type":"Point","coordinates":[""")
                append(row.longitude)
                append(',')
                append(row.latitude)
                append("""]},"properties":{""")

                // ID (immer vorhanden – Primary Key)
                append(""""id":""")
                append(jsonString(row.id))

                // Anzeigename
                append(""","name":""")
                append(jsonString(row.label))

                // Status
                append(""","status":""")
                append(jsonString(row.status))

                // Sammelstatus – GRUNDLAGE für spätere farbliche Unterscheidung (gesammelt = grün)
                // 0 = nicht gesammelt, 1 = gesammelt
                // Für Farbgebung in WindradMap.kt: filter = feature["collected"].asNumber() eq const(1f)
                append(""","collected":""")
                append(row.collected)

                append("}}")
            }
            append("]}")
        }
    }

    /**
     * Lädt die Details einer einzelnen Windkraftanlage aus der Datenbank.
     * Berechnet den Betriebsstatus (Aktiv/Inaktiv/Geplant) und das zugehörige
     * Datum relativ zum aktuellen Systemdatum.
     */
    suspend fun loadWindmillDetails(id: String): WindmillDetails? = withContext(Dispatchers.Default) {
        val row = db.windradDbQueries.selectWindmillById(id).executeAsOneOrNull() ?: return@withContext null
        val displayName = row.unit_display_name ?: row.unit_MaStR_ID
        
        val (operatingStatus, commissioningDate) = calculatePeriodAndDate(
            status = row.operating_status,
            commissioningDateStr = row.unit_commissioning_date,
            finalDecommissioningDateStr = row.final_decommissioning_date,
            plannedCommissioningDateStr = row.planned_commissioning_date
        )
        
        val householdsText = calculateHouseholdsFedText(
            netCapacity = row.unit_net_nominal_capacity,
            onshoreOffshoreType = row.onshore_offshore_type
        )
        
        val rotorText = calculateRotorComparisonText(
            diameter = row.turbine_rotor_diameter
        )
        
        val isOnshore = row.onshore_offshore_type != "Windkraft auf See"
        val locationText = calculateLocationText(row.onshore_offshore_type)
        val totalHeight = (row.turbine_hub_height ?: 0.0) + (row.turbine_rotor_diameter ?: 0.0) / 2.0
        
        WindmillDetails(
            name = displayName,
            operatingStatus = operatingStatus,
            commissioningDate = commissioningDate,
            householdsFedText = householdsText,
            rotorComparisonText = rotorText,
            technologyText = row.power_generation_technology ?: "Horizontalläufer",
            locationText = locationText,
            isOnshore = isOnshore,
            totalHeight = totalHeight,
            latitude = row.latitude_wgs84 ?: 0.0,
            longitude = row.longitude_wgs84 ?: 0.0,
            rotorDiameter = row.turbine_rotor_diameter
        )
    }

    /**
     * Sucht nach Orten, Windparks und Postleitzahlen in der Datenbank.
     */
    suspend fun searchLocations(query: String): List<SearchSuggestion> = withContext(Dispatchers.Default) {
        if (query.isBlank()) return@withContext emptyList()
        val matchQuery = "%$query%"
        db.windradDbQueries.searchLocations(matchQuery).executeAsList().map { row ->
            SearchSuggestion(
                type = row.type,
                name = row.name ?: "",
                latitude = row.latitude ?: 0.0,
                longitude = row.longitude ?: 0.0
            )
        }
    }

    private val jsonParser = Json { ignoreUnknownKeys = true }

    /**
     * Sucht online nach Orten über die Nominatim API (OpenStreetMap).
     */
    suspend fun searchOnlinePlaces(query: String): List<SearchSuggestion> = withContext(Dispatchers.Default) {
        if (query.isBlank()) return@withContext emptyList()
        val encodedQuery = urlEncode(query)
        val url = "https://nominatim.openstreetmap.org/search?q=$encodedQuery&format=json&limit=5&addressdetails=1"
        val response = HttpUtil.get(url) ?: return@withContext emptyList()
        try {
            val results = jsonParser.decodeFromString<List<NominatimResult>>(response)
            results.mapNotNull { res ->
                val lat = res.lat.toDoubleOrNull() ?: return@mapNotNull null
                val lon = res.lon.toDoubleOrNull() ?: return@mapNotNull null
                
                val parts = res.display_name.split(",")
                val name = parts.firstOrNull()?.trim() ?: res.display_name
                val subtitle = parts.drop(1).joinToString(",").trim()
                
                SearchSuggestion(
                    type = "city",
                    name = name,
                    latitude = lat,
                    longitude = lon,
                    subtitle = subtitle.ifEmpty { "Ort" }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun findWindmillsWithinRadius(lat: Double, lon: Double, radiusKm: Double): List<NearbyWindmill> = withContext(Dispatchers.Default) {
        val latDiff = radiusKm / 111.0
        val lonDiff = radiusKm / (111.0 * kotlin.math.cos(lat * kotlin.math.PI / 180.0))
        
        val minLat = lat - latDiff
        val maxLat = lat + latDiff
        val minLon = lon - lonDiff
        val maxLon = lon + lonDiff
        
        val rows = db.windradDbQueries.selectWindmillsInBoundingBox(
            minLat = minLat,
            maxLat = maxLat,
            minLon = minLon,
            maxLon = maxLon
        ).executeAsList()
        
        rows.mapNotNull { row ->
            val wLat = row.latitude_wgs84 ?: return@mapNotNull null
            val wLon = row.longitude_wgs84 ?: return@mapNotNull null
            val distance = calculateDistanceKm(lat, lon, wLat, wLon)
            if (distance <= radiusKm) {
                val displayName = row.unit_display_name ?: row.unit_MaStR_ID
                
                val statusText = when {
                    row.operating_status?.contains("Planung", ignoreCase = true) == true -> "In Planung"
                    row.operating_status?.contains("Betrieb", ignoreCase = true) == true -> "Aktiv"
                    else -> "Inaktiv"
                }
                
                val capacityText = if (row.unit_net_nominal_capacity != null && row.unit_net_nominal_capacity > 0.0) {
                    formatCapacity(row.unit_net_nominal_capacity)
                } else {
                    "Leistung unbekannt"
                }
                
                NearbyWindmill(
                    id = row.unit_MaStR_ID,
                    name = displayName,
                    city = row.city ?: "",
                    distanceText = formatDistance(distance),
                    status = statusText,
                    capacityText = capacityText,
                    distance = distance,
                    isFavorite = row.collected == 1L
                )
            } else {
                null
            }
        }.sortedBy { it.distance }
    }

    suspend fun collectAll(ids: List<String>) = withContext(Dispatchers.Default) {
        db.windradDbQueries.transaction {
            ids.forEach { id ->
                db.windradDbQueries.updateCollectionStatus(id, true)
            }
        }
    }

    suspend fun updateCollectionStatus(id: String, collected: Boolean) = withContext(Dispatchers.Default) {
        db.windradDbQueries.updateCollectionStatus(id, collected)
    }

    private fun formatCapacity(capacity: Double): String {
        val value = capacity.toInt()
        return if (value >= 1000) {
            val thousands = value / 1000
            val remainder = value % 1000
            val remainderStr = remainder.toString().padStart(3, '0')
            "$thousands.$remainderStr kW"
        } else {
            "$value kW"
        }
    }

    private fun calculateDistanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0 // Earth radius in km
        val dLat = (lat2 - lat1) * kotlin.math.PI / 180.0
        val dLon = (lon2 - lon1) * kotlin.math.PI / 180.0
        val a = kotlin.math.sin(dLat / 2.0) * kotlin.math.sin(dLat / 2.0) +
                kotlin.math.cos(lat1 * kotlin.math.PI / 180.0) * kotlin.math.cos(lat2 * kotlin.math.PI / 180.0) *
                kotlin.math.sin(dLon / 2.0) * kotlin.math.sin(dLon / 2.0)
        val c = 2.0 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1.0 - a))
        return r * c
    }

    private fun formatDistance(distance: Double): String {
        return if (distance < 1.0) {
            val meters = (distance * 1000).roundToInt()
            "$meters m entfernt"
        } else {
            val rounded = (distance * 10.0).roundToInt() / 10.0
            val formatted = rounded.toString().replace('.', ',')
            "$formatted km entfernt"
        }
    }




    /**
     * Hilfsfunktion: Konvertiert einen Kotlin-String in einen JSON-String-Wert.
     * null wird als JSON-null kodiert, Sonderzeichen werden escaped.
     */
    private fun jsonString(value: String?): String {
        if (value == null) return "null"
        val escaped = value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
        return "\"$escaped\""
    }
}

// ── Hilfsfunktionen für KMP-Datumsberechnung (Format: D.M.YYYY) ──────────────────

internal fun parseLocalDate(dateStr: String?): LocalDate? {
    if (dateStr.isNullOrBlank()) return null
    val parts = dateStr.split(".")
    if (parts.size != 3) return null
    val day = parts[0].toIntOrNull() ?: return null
    val month = parts[1].toIntOrNull() ?: return null
    val year = parts[2].toIntOrNull() ?: return null
    return try {
        LocalDate(year, month, day)
    } catch (e: Exception) {
        null
    }
}

internal fun formatDisplayDate(date: LocalDate): String {
    val dayStr = date.day.toString().padStart(2, '0')
    val monthStr = (date.month.ordinal + 1).toString().padStart(2, '0')
    return "$dayStr.$monthStr.${date.year}"
}

internal fun calculatePeriodAndDate(
    status: String?,
    commissioningDateStr: String?,
    finalDecommissioningDateStr: String?,
    plannedCommissioningDateStr: String?,
    today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
): Pair<String, String> {
    val statusClean = status?.trim() ?: ""
    val isPlanned = statusClean.equals("In Planung", ignoreCase = true)
    val isActive = statusClean.equals("In Betrieb", ignoreCase = true)
    val isInactive = statusClean.equals("Endgültig stillgelegt", ignoreCase = true) ||
                     statusClean.equals("Vorübergehend stillgelegt", ignoreCase = true)

    val sourceDateStr = when {
        isPlanned -> plannedCommissioningDateStr
        isInactive -> finalDecommissioningDateStr ?: commissioningDateStr
        else -> commissioningDateStr
    }

    val eventDate = parseLocalDate(sourceDateStr)
    if (eventDate == null) {
        val fallbackLabel = when {
            isPlanned -> "Geplant"
            isInactive -> "Inaktiv"
            else -> "Aktiv"
        }
        return Pair(fallbackLabel, "Datum unbekannt")
    }

    // Berechnung des Zeitraums in Monaten
    val yearsDiff = today.year - eventDate.year
    val monthsDiff = today.month.ordinal - eventDate.month.ordinal
    val totalMonths = yearsDiff * 12 + monthsDiff

    val absMonths = kotlin.math.abs(totalMonths)

    val prefix = when {
        isPlanned -> "Geplant: in "
        isInactive -> "Inaktiv: seit "
        else -> "Aktiv: seit "
    }

    val formattedPeriod = if (absMonths < 12) {
        val unit = if (absMonths == 1) "Monat" else "Monaten"
        "$prefix$absMonths $unit"
    } else {
        val years = absMonths / 12
        val unit = if (years == 1) "Jahr" else "Jahren"
        "$prefix$years $unit"
    }

    val datePrefix = when {
        isPlanned -> "Inbetriebnahme am "
        isInactive && finalDecommissioningDateStr != null -> "Außerbetriebnahme am "
        else -> "Inbetriebnahme am "
    }
    val formattedDateSubtext = "$datePrefix${formatDisplayDate(eventDate)}"

    return Pair(formattedPeriod, formattedDateSubtext)
}

internal fun calculateHouseholdsFedText(
    netCapacity: Double?,
    onshoreOffshoreType: String?
): String {
    if (netCapacity == null) return "Versorgt: Wert leider nicht bekannt"
    val isOnshore = onshoreOffshoreType != "Windkraft auf See"
    val fullLoadHours = if (isOnshore) 1770 else 3278
    val households = ((netCapacity * fullLoadHours) / 2277.0).roundToInt()
    return "Versorgt ca. $households Haushalte (${netCapacity.toInt()}kW)"
}

internal fun calculateRotorComparisonText(
    diameter: Double?
): String {
    if (diameter == null) return "Wert leider nicht bekannt"
    val radius = diameter / 2.0
    val area = radius * radius * kotlin.math.PI
    val fields = area / 7140.0
    val rounded = (fields * 10.0).roundToInt()
    val integerPart = rounded / 10
    val fractionalPart = kotlin.math.abs(rounded % 10)
    val formattedFields = "$integerPart,$fractionalPart"
    return "${diameter.toInt()}m ~ $formattedFields Fußballfelder"
}

internal fun calculateLocationText(onshoreOffshoreType: String?): String {
    return when (onshoreOffshoreType) {
        "Windkraft an Land" -> "An Land"
        "Windkraft auf See" -> "Auf See"
        else -> "Wert leider nicht bekannt"
    }
}

@Serializable
private data class NominatimResult(
    val lat: String,
    val lon: String,
    val display_name: String,
    val type: String = "",
    val `class`: String = ""
)

