package org.example.project

import com.windrad.data.calculatePeriodAndDate
import com.windrad.data.calculateHouseholdsFedText
import com.windrad.data.calculateRotorComparisonText
import com.windrad.data.calculateLocationText
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedCommonTest {

    private val today = LocalDate(2026, 6, 20)

    @Test
    fun testPlannedWindmill() {
        val (status, date) = calculatePeriodAndDate(
            status = "In Planung",
            commissioningDateStr = null,
            finalDecommissioningDateStr = null,
            plannedCommissioningDateStr = "1.10.2026",
            today = today
        )
        assertEquals("Geplant: in 4 Monaten", status)
        assertEquals("Inbetriebnahme am 01.10.2026", date)
    }

    @Test
    fun testActiveWindmill() {
        val (status, date) = calculatePeriodAndDate(
            status = "In Betrieb",
            commissioningDateStr = "1.9.2017",
            finalDecommissioningDateStr = null,
            plannedCommissioningDateStr = null,
            today = today
        )
        assertEquals("Aktiv: seit 8 Jahren", status)
        assertEquals("Inbetriebnahme am 01.09.2017", date)
    }

    @Test
    fun testInactiveWindmillWithDecommissioningDate() {
        val (status, date) = calculatePeriodAndDate(
            status = "Endgültig stillgelegt",
            commissioningDateStr = "3.8.2000",
            finalDecommissioningDateStr = "1.3.2021",
            plannedCommissioningDateStr = null,
            today = today
        )
        assertEquals("Inaktiv: seit 5 Jahren", status)
        assertEquals("Außerbetriebnahme am 01.03.2021", date)
    }

    @Test
    fun testInactiveWindmillWithoutDecommissioningDate() {
        val (status, date) = calculatePeriodAndDate(
            status = "Vorübergehend stillgelegt",
            commissioningDateStr = "9.6.1999",
            finalDecommissioningDateStr = null,
            plannedCommissioningDateStr = null,
            today = today
        )
        assertEquals("Inaktiv: seit 27 Jahren", status)
        assertEquals("Inbetriebnahme am 09.06.1999", date)
    }

    @Test
    fun testOnshoreHouseholds() {
        val text = calculateHouseholdsFedText(
            netCapacity = 3450.0,
            onshoreOffshoreType = "Windkraft an Land"
        )
        assertEquals("Versorgt ca. 2682 Haushalte (3450kW)", text)
    }

    @Test
    fun testOffshoreHouseholds() {
        val text = calculateHouseholdsFedText(
            netCapacity = 5000.0,
            onshoreOffshoreType = "Windkraft auf See"
        )
        assertEquals("Versorgt ca. 7201 Haushalte (5000kW)", text)
    }

    @Test
    fun testUnknownCapacityHouseholds() {
        val text = calculateHouseholdsFedText(
            netCapacity = null,
            onshoreOffshoreType = "Windkraft an Land"
        )
        assertEquals("Versorgt: Wert leider nicht bekannt", text)
    }

    @Test
    fun testRotorComparisonValid() {
        val text = calculateRotorComparisonText(diameter = 126.0)
        assertEquals("126m ~ 1,7 Fußballfelder", text)
    }

    @Test
    fun testRotorComparisonNull() {
        val text = calculateRotorComparisonText(diameter = null)
        assertEquals("Wert leider nicht bekannt", text)
    }

    @Test
    fun testLocationTextOnshore() {
        val text = calculateLocationText("Windkraft an Land")
        assertEquals("An Land", text)
    }

    @Test
    fun testLocationTextOffshore() {
        val text = calculateLocationText("Windkraft auf See")
        assertEquals("Auf See", text)
    }

    @Test
    fun testLocationTextUnknown() {
        val text = calculateLocationText(null)
        assertEquals("Wert leider nicht bekannt", text)
    }

    @Test
    fun testUrlEncode() {
        val encoded = com.windrad.data.urlEncode("München & Hamburg")
        // Check that German umlaut 'ü' and special character '&' and space ' ' are encoded
        kotlin.test.assertTrue(encoded.contains("M%C3%BCnchen") || encoded.contains("M%FCnchen"))
        kotlin.test.assertTrue(encoded.contains("%20"))
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun testHttpUtilAndNominatimParser() = kotlinx.coroutines.test.runTest {
        val encoded = com.windrad.data.urlEncode("Erfurt")
        val url = "https://nominatim.openstreetmap.org/search?q=$encoded&format=json&limit=2&addressdetails=1"
        val response = com.windrad.data.HttpUtil.get(url)
        println("testHttpUtilAndNominatimParser: Response = $response")
        
        if (response != null) {
            val jsonParser = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            val results = jsonParser.decodeFromString<List<TestNominatimResult>>(response)
            kotlin.test.assertTrue(results.isNotEmpty())
            val first = results.first()
            kotlin.test.assertTrue(first.lat.isNotEmpty())
            kotlin.test.assertTrue(first.lon.isNotEmpty())
            kotlin.test.assertTrue(first.display_name.isNotEmpty())
        }
    }
}

@kotlinx.serialization.Serializable
private data class TestNominatimResult(
    val lat: String,
    val lon: String,
    val display_name: String
)