package com.windrad.data

import com.windrad.database.WindradDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
