package com.windrad.ui.collection.list

enum class CollectionRoute {
    KPI,
    STATE_OVERVIEW,
    TURBINE_LIST,
}

enum class CollectionFilter {
    FOUND,
    NOT_FOUND,
}

data class FederalStateSummary(
    val name: String,
    val collectedCount: Int,
    val totalCount: Int,
)

data class CollectionOverviewData(
    val totalCollected: Int,
    val totalCount: Int,
    val topStateName: String?,
    val states: List<FederalStateSummary>,
)

data class TurbineListItem(
    val id: String,
    val municipality: String,
    val displayName: String,
)

data class CollectionListUiState(
    val route: CollectionRoute = CollectionRoute.KPI,
    val selectedState: String? = null,
    val filter: CollectionFilter = CollectionFilter.FOUND,
    val overview: CollectionOverviewData? = null,
    val turbines: List<TurbineListItem> = emptyList(),
    val letterIndex: Map<Char, Int> = emptyMap(),
    val expandedTurbineId: String? = null,
    val isLoading: Boolean = false,
)

fun buildLetterIndex(turbines: List<TurbineListItem>): Map<Char, Int> {
    val index = linkedMapOf<Char, Int>()
    turbines.forEachIndexed { i, item ->
        val letter = indexLetter(item.municipality)
        if (letter !in index) {
            index[letter] = i
        }
    }
    return index
}

fun indexLetter(municipality: String): Char {
    val first = municipality.trim().firstOrNull() ?: return '#'
    return when {
        first.isLetter() -> first.uppercaseChar()
        else -> '#'
    }
}
