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
}
