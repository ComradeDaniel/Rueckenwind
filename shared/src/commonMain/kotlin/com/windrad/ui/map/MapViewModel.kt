package com.windrad.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windrad.data.WindradRepository
import com.windrad.database.DatabaseDriverFactory
import com.windrad.database.createDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

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
    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadWindmillGeoJson()
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
        if (_uiState.value.isScanning) return
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true) }
            delay(2500)
            _uiState.update { it.copy(isScanning = false, windparkCount = 3) }
        }
    }

    /**
     * Wählt ein Windrad aus, um dessen Detailkarte anzuzeigen.
     * Nutzt vorerst Dummy-Daten gemäß Figma-Spezifikation.
     */
    fun selectWindmill(windmillId: String?) {
        if (windmillId == null) {
            _uiState.update { it.copy(selectedWindmill = null) }
        } else {
            viewModelScope.launch {
                val details = repository.loadWindmillDetails(windmillId)
                _uiState.update { it.copy(selectedWindmill = details) }
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
}

