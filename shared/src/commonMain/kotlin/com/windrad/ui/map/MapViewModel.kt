package com.windrad.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun onScanClicked() {
        if (_uiState.value.isScanning) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true) }
            delay(2500)
            _uiState.update { it.copy(isScanning = false, windparkCount = 3) }
        }
    }
}
