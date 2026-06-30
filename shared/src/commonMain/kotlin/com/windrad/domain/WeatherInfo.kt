package com.windrad.domain

data class WeatherInfo(
    val windSpeed: Double,       // km/h
    val windDirection: Int,      // Grad (0–360)
    val windGustSpeed: Double?   // km/h, nullable
)
