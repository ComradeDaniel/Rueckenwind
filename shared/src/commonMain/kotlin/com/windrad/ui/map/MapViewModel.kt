package com.windrad.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windrad.data.LocationProvider
import com.windrad.data.WindradRepository
import com.windrad.database.DatabaseDriverFactory
import com.windrad.data.WeatherRepository
import com.windrad.database.createDatabase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

/**
 * ViewModel für den Karten-Screen.
 *
 * Verantwortlichkeiten:
 *  1. Beim Start alle Windräder aus der DB als GeoJSON laden (für MapLibre-Clustering)
 *  2. Scan-Funktion verwalten
 *
 * @param driverFactory Plattformspezifische DB-Treiber-Factory
 *                      (Android: mit Context, iOS: ohne Argument)
 */


class MapViewModel(driverFactory: DatabaseDriverFactory) : ViewModel() {

    private val db = createDatabase(driverFactory)
    private val repository = WindradRepository(db)
    private val locationProvider = LocationProvider(driverFactory)
    private val weatherRepository = WeatherRepository()
    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadWindmillGeoJson()
        startLocationTracking()
    }

    private var lastTrackedLat: Double? = null
    private var lastTrackedLon: Double? = null

    private fun startLocationTracking() {
        locationProvider.startLocationUpdates { lat, lon ->
            val last = lastTrackedLat
            val lastLon = lastTrackedLon
            // Only update if > 10 meters distance (haversine approximation)
            if (last != null && lastLon != null) {
                val dlat = lat - last
                val dlon = lon - lastLon
                val distSq = dlat * dlat + dlon * dlon
                if (distSq < (0.0001 * 0.0001)) return@startLocationUpdates
            }
            lastTrackedLat = lat
            lastTrackedLon = lon
            _uiState.update { it.copy(userLatitude = lat, userLongitude = lon) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationProvider.stopLocationUpdates()
    }

    /**
     * Lädt alle Windräder aus der DB und erzeugt die GeoJSON-FeatureCollection.
     * Läuft auf dem Default-Dispatcher (Hintergrundthread), blockiert die UI nicht.
     */
    private fun loadWindmillGeoJson() {
        viewModelScope.launch {
            try {
                println("MapViewModel: Starte GeoJSON-Generierung für MapLibre-Clustering...")
                val geoJson = repository.loadAllWindmillsAsGeoJson()
                println("MapViewModel: GeoJSON fertig (${geoJson.length} Zeichen)")
                _uiState.update { it.copy(windmillGeoJson = geoJson, isGeoJsonReady = true) }
            } catch (e: Exception) {
                println("MapViewModel: FEHLER beim GeoJSON-Aufbau: $e")
            }
        }
    }

    fun onScanClicked() {
        println("MapViewModel: onScanClicked triggered!")
        if (_uiState.value.isScanning) {
            println("MapViewModel: already scanning, skipping.")
            return
        }
        viewModelScope.launch {
            println("MapViewModel: scan coroutine started")
            _uiState.update { it.copy(isScanning = true) }
            val startTime = Clock.System.now().toEpochMilliseconds()
            
            // Try to get GPS location from continuous tracking first
            var gpsLocation = _uiState.value.let { state ->
                if (state.userLatitude != null && state.userLongitude != null) {
                    Pair(state.userLatitude, state.userLongitude)
                } else null
            }
            
            if (gpsLocation == null) {
                gpsLocation = try {
                    println("MapViewModel: querying locationProvider as fallback...")
                    locationProvider.getCurrentLocation()
                } catch (e: Exception) {
                    println("MapViewModel: locationProvider query failed: $e")
                    e.printStackTrace()
                    null
                }
            }
            println("MapViewModel: gpsLocation obtained: $gpsLocation")
            
            val nearby = if (gpsLocation != null) {
                println("MapViewModel: scanning coordinates: lat=${gpsLocation.first}, lon=${gpsLocation.second}")
                // Measure DB query time
                val dbStart = Clock.System.now().toEpochMilliseconds()
                val result = repository.findWindmillsWithinRadius(gpsLocation.first, gpsLocation.second, _uiState.value.scanRadiusKm)
                val dbElapsed = Clock.System.now().toEpochMilliseconds() - dbStart
                println("MapViewModel: DB query took ${dbElapsed}ms, returned ${result.size} windmills")
                result
            } else {
                println("MapViewModel: GPS location is unavailable, returning empty list (no fallback)")
                emptyList()
            }
            println("MapViewModel: found ${nearby.size} nearby windmills")
            
            // Ensure at least 0.5 s of scan animation for visual feedback
            val elapsed = Clock.System.now().toEpochMilliseconds() - startTime
            val minimumDelay = 500L // 0.5 seconds
            val remainingDelay = (minimumDelay - elapsed).coerceAtLeast(0L)
            println("MapViewModel: waiting remaining delay: ${remainingDelay}ms")
            delay(remainingDelay)
            
            val radius = _uiState.value.scanRadiusKm
            val lat = gpsLocation?.first ?: 50.0
            val latRad = lat * kotlin.math.PI / 180.0
            val paddingFactor = 1.5
            val dynamicZoom = kotlin.math.log2(
                (40075.0 * kotlin.math.cos(latRad)) / (2.0 * radius * paddingFactor)
            ).coerceIn(1.0, 20.0)

            _uiState.update {
                it.copy(
                    isScanning = false,
                    windparkCount = nearby.size,
                    scannedWindmills = nearby,
                    centerLatitude = gpsLocation?.first ?: it.centerLatitude,
                    centerLongitude = gpsLocation?.second ?: it.centerLongitude,
                    zoom = if (gpsLocation != null) dynamicZoom else it.zoom
                )
            }
            println("MapViewModel: scan completed, updated state and focused region at lat=${gpsLocation?.first}, lon=${gpsLocation?.second}")
        }
    }

    /**
     * Wählt ein Windrad aus, um dessen Detailkarte anzuzeigen.
     * Nutzt vorerst Dummy-Daten gemäß Figma-Spezifikation.
     */
    fun selectWindmill(windmillId: String?) {
        if (windmillId == null) {
            _uiState.update { it.copy(selectedWindmill = null, weatherInfo = null) }
        } else {
            viewModelScope.launch {
                val details = repository.loadWindmillDetails(windmillId)
                _uiState.update { it.copy(selectedWindmill = details, weatherInfo = null) }
                if (details != null) {
                    val weather = weatherRepository.fetchCurrentWeather(
                        lat = details.latitude,
                        lon = details.longitude,
                        cacheKey = windmillId
                    )
                    _uiState.update { it.copy(weatherInfo = weather) }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.update { it.copy(searchSuggestions = emptyList()) }
        } else {
            searchJob = viewModelScope.launch {
                // 1. Lokale DB-Suche sofort ausführen & anzeigen
                val localSuggestions = repository.searchLocations(query)
                _uiState.update { it.copy(searchSuggestions = localSuggestions) }
                
                // 2. Debounce für Online-Suche (500ms warten)
                delay(500)
                
                // 3. Online-Ortssuche via Nominatim ausführen
                try {
                    val onlineSuggestions = repository.searchOnlinePlaces(query)
                    val combined = (localSuggestions + onlineSuggestions)
                        .associateBy { it.name.lowercase() + "|" + it.type }
                        .values
                        .toList()
                    _uiState.update { it.copy(searchSuggestions = combined) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun onSearchResultSelected(suggestion: SearchSuggestion) {
        _uiState.update {
            it.copy(
                centerLatitude = suggestion.latitude,
                centerLongitude = suggestion.longitude,
                zoom = 12.5,
                searchQuery = suggestion.name,
                searchSuggestions = emptyList()
            )
        }
    }

    fun clearSearch() {
        _uiState.update {
            it.copy(
                searchQuery = "",
                searchSuggestions = emptyList()
            )
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            val scannedList = _uiState.value.scannedWindmills
            val item = scannedList.find { it.id == id } ?: return@launch
            val newFavorite = !item.isFavorite
            repository.updateCollectionStatus(id, newFavorite)
            
            _uiState.update { state ->
                state.copy(
                    scannedWindmills = state.scannedWindmills.map { w ->
                        if (w.id == id) w.copy(isFavorite = newFavorite) else w
                    }
                )
            }
            loadWindmillGeoJson()
        }
    }

    fun collectAllScanned() {
        val scannedIds = _uiState.value.scannedWindmills.map { it.id }
        if (scannedIds.isEmpty()) return
        viewModelScope.launch {
            repository.collectAll(scannedIds)
            _uiState.update { state ->
                state.copy(
                    scannedWindmills = state.scannedWindmills.map { item ->
                        item.copy(isFavorite = true)
                    }
                )
            }
            loadWindmillGeoJson()
        }
    }
}

