package com.windrad.ui.map

data class MapUiState(
    val centerLatitude: Double = 51.1657, // Germany Center
    val centerLongitude: Double = 10.4515,
    val zoom: Double = 5.5,
    val windparkCount: Int = 0,
    val isScanning: Boolean = false
)
