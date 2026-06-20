package com.windrad.data

import com.windrad.database.WindradDatabase
import com.windrad.ui.map.WindmillDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

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
        
        WindmillDetails(
            name = displayName,
            operatingStatus = operatingStatus,
            commissioningDate = commissioningDate,
            householdsFedText = householdsText,
            rotorComparisonText = rotorText,
            locationText = locationText,
            isOnshore = isOnshore
        )
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
