package com.windrad.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windrad.data.WindradRepository
import com.windrad.database.DatabaseDriverFactory
import com.windrad.database.createDatabase
import com.windrad.ui.collection.list.CollectionFilter
import com.windrad.ui.collection.list.CollectionListUiState
import com.windrad.ui.collection.list.CollectionRoute
import com.windrad.ui.collection.list.TurbineListItem
import com.windrad.ui.collection.list.buildLetterIndex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(driverFactory: DatabaseDriverFactory) : ViewModel() {

    private val repository = WindradRepository(createDatabase(driverFactory))

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    private val _listUiState = MutableStateFlow(CollectionListUiState())
    val listUiState: StateFlow<CollectionListUiState> = _listUiState.asStateFlow()

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

    fun openStateOverview() {
        _listUiState.update {
            it.copy(
                route = CollectionRoute.STATE_OVERVIEW,
                isLoading = true,
            )
        }
        viewModelScope.launch {
            val overview = repository.getCollectionOverview()
            _listUiState.update {
                it.copy(
                    overview = overview,
                    isLoading = false,
                )
            }
        }
    }

    fun openState(state: String?) {
        _listUiState.update {
            it.copy(
                route = CollectionRoute.TURBINE_LIST,
                selectedState = state,
                filter = CollectionFilter.FOUND,
                searchQuery = "",
                expandedTurbineId = null,
                expandedDetails = null,
                isDetailsLoading = false,
                isLoading = true,
            )
        }
        loadTurbines(state, CollectionFilter.FOUND)
    }

    fun setFilter(filter: CollectionFilter) {
        val state = _listUiState.value.selectedState
        _listUiState.update {
            it.copy(
                filter = filter,
                expandedTurbineId = null,
                expandedDetails = null,
                isDetailsLoading = false,
                isLoading = true,
            )
        }
        loadTurbines(state, filter)
    }

    fun toggleTurbineExpanded(id: String) {
        val current = _listUiState.value
        if (current.expandedTurbineId == id) {
            _listUiState.update {
                it.copy(
                    expandedTurbineId = null,
                    expandedDetails = null,
                    isDetailsLoading = false,
                )
            }
            return
        }

        _listUiState.update {
            it.copy(
                expandedTurbineId = id,
                expandedDetails = null,
                isDetailsLoading = true,
            )
        }
        viewModelScope.launch {
            val details = repository.loadCollectionTurbineDetails(id)
            _listUiState.update { state ->
                if (state.expandedTurbineId == id) {
                    state.copy(
                        expandedDetails = details,
                        isDetailsLoading = false,
                    )
                } else {
                    state
                }
            }
        }
    }

    fun back() {
        when (_listUiState.value.route) {
            CollectionRoute.TURBINE_LIST -> {
                _listUiState.update {
                    it.copy(
                        route = CollectionRoute.STATE_OVERVIEW,
                        selectedState = null,
                        turbines = emptyList(),
                        letterIndex = emptyMap(),
                        searchQuery = "",
                        expandedTurbineId = null,
                        expandedDetails = null,
                        isDetailsLoading = false,
                    )
                }
            }

            CollectionRoute.STATE_OVERVIEW -> {
                _listUiState.update {
                    CollectionListUiState(route = CollectionRoute.KPI)
                }
            }

            CollectionRoute.KPI -> Unit
        }
    }

    fun resetToKpi() {
        _listUiState.update {
            CollectionListUiState(route = CollectionRoute.KPI)
        }
    }

    private var fullTurbineList: List<TurbineListItem> = emptyList()

    private fun loadTurbines(state: String?, filter: CollectionFilter) {
        viewModelScope.launch {
            fullTurbineList = repository.getTurbines(state, filter)
            applyFilters()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _listUiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    private fun applyFilters() {
        val query = _listUiState.value.searchQuery.trim().lowercase()
        val processedQuery = query.replace(" ", "").replace("-", "")

        val filtered = if (query.isEmpty()) {
            fullTurbineList
        } else {
            fullTurbineList.filter { turbine ->
                val municipalityMatch = if (turbine.municipality != "Unbekannt") {
                    turbine.municipality.lowercase().startsWith(query)
                } else false

                val processedDisplayName =
                    turbine.displayName.lowercase().replace(" ", "").replace("-", "")
                val displayNameMatch = processedDisplayName.startsWith(processedQuery)

                municipalityMatch || displayNameMatch
            }.sortedWith(
                compareBy<TurbineListItem> { turbine ->
                    val municipalityDirectMatch = if (turbine.municipality != "Unbekannt") {
                        turbine.municipality.lowercase() == query
                    } else false

                    val processedDisplayName =
                        turbine.displayName.lowercase().replace(" ", "").replace("-", "")
                    val displayNameDirectMatch = processedDisplayName == processedQuery

                    if (municipalityDirectMatch || displayNameDirectMatch) 0 else 1
                }.thenBy { turbine ->
                    val municipalityMatch = if (turbine.municipality != "Unbekannt") {
                        turbine.municipality.lowercase().startsWith(query)
                    } else false

                    val processedDisplayName =
                        turbine.displayName.lowercase().replace(" ", "").replace("-", "")
                    val displayNameMatch = processedDisplayName.startsWith(processedQuery)

                    val lengths = mutableListOf<Int>()
                    if (municipalityMatch) lengths.add(turbine.municipality.length)
                    if (displayNameMatch) lengths.add(turbine.displayName.length)

                    lengths.minOrNull() ?: Int.MAX_VALUE
                }
            )
        }

        _listUiState.update {
            it.copy(
                turbines = filtered,
                letterIndex = buildLetterIndex(filtered),
                isLoading = false,
            )
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
