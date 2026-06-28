package com.windrad.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windrad.data.WindradRepository
import com.windrad.database.DatabaseDriverFactory
import com.windrad.database.createDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(driverFactory: DatabaseDriverFactory) : ViewModel() {

    private val repository = WindradRepository(createDatabase(driverFactory))

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    init {
        loadOverview()
    }

    private fun loadOverview() {
        viewModelScope.launch {
            val count = repository.getCollectedCount()
            _uiState.update {
                if (count > 0) {
                    it.copy(
                        collectedCount = count,
                        windparksCount = count,
                        householdsCount = HOUSEHOLDS_PLACEHOLDER,
                        mwhValue = MWH_PLACEHOLDER,
                        co2Value = CO2_PLACEHOLDER,
                        coolingWaterValue = COOLING_WATER_PLACEHOLDER,
                        ecoAreaValue = ECO_AREA_PLACEHOLDER,
                        eCarChargesValue = E_CAR_CHARGES_PLACEHOLDER,
                        fullLoadHoursValue = FULL_LOAD_HOURS_PLACEHOLDER,
                    )
                } else {
                    it.copy(collectedCount = 0)
                }
            }
        }
    }

    companion object {
        const val MWH_PLACEHOLDER = 1240.0
        const val CO2_PLACEHOLDER = 845.0
        const val HOUSEHOLDS_PLACEHOLDER = 4_000
        const val COOLING_WATER_PLACEHOLDER = 2457.0
        const val ECO_AREA_PLACEHOLDER = 423.0
        const val E_CAR_CHARGES_PLACEHOLDER = 40000.0
        const val FULL_LOAD_HOURS_PLACEHOLDER = 1920.0
    }
}
