package com.windrad.data

import com.windrad.domain.WeatherInfo
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Clock

class WeatherRepository {

    private val json = Json { ignoreUnknownKeys = true }

    // Einfaches In-Memory-Cache: windmillId -> (WeatherInfo, timestampMs)
    private val cache = mutableMapOf<String, Pair<WeatherInfo, Long>>()
    private val cacheTtlMs = 10 * 60 * 1000L // 10 Minuten

    suspend fun fetchCurrentWeather(
        lat: Double,
        lon: Double,
        cacheKey: String
    ): WeatherInfo? {
        val now = Clock.System.now().toEpochMilliseconds()
        cache[cacheKey]?.let { (info, ts) ->
            if (now - ts < cacheTtlMs) return info
        }
        val url = "https://api.brightsky.dev/current_weather?lat=$lat&lon=$lon"
        val response = HttpUtil.get(url) ?: return null
        return try {
            val parsed = json.decodeFromString<BrightSkyResponse>(response)
            val w = parsed.weather ?: return null
            val info = WeatherInfo(
                windSpeed = w.wind_speed_10 ?: return null,
                windDirection = w.wind_direction_10 ?: 0,
                windGustSpeed = w.wind_gust_speed_10
            )
            cache[cacheKey] = info to now
            info
        } catch (e: Exception) {
            null
        }
    }

    @Serializable private data class BrightSkyResponse(val weather: BrightSkyWeather? = null)
    @Serializable private data class BrightSkyWeather(
        val wind_speed_10: Double? = null,
        val wind_direction_10: Int? = null,
        val wind_gust_speed_10: Double? = null
    )
}
