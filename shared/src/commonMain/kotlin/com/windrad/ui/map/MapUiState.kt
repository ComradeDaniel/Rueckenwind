package com.windrad.ui.map

data class MapUiState(
    val centerLatitude: Double = 51.1657, // Deutschland-Zentrum
    val centerLongitude: Double = 10.4515,
    val zoom: Double = 5.5,
    val windparkCount: Int = 0,
    val isScanning: Boolean = false,

    /**
     * Fertige GeoJSON-FeatureCollection für MapLibre als String.
     * null = Daten werden noch aus der DB geladen.
     * Jedes Feature enthält ein "collected"-Property (0/1) als Grundlage
     * für spätere farbliche Unterscheidung gesammelter Windräder.
     */
    val windmillGeoJson: String? = null,

    /** Wird true, sobald der GeoJSON-String vollständig aufgebaut ist. */
    val isGeoJsonReady: Boolean = false,

    /** Das aktuell auf der Karte ausgewählte Windrad für die Detailkarte. */
    val selectedWindmill: WindmillDetails? = null,
)
