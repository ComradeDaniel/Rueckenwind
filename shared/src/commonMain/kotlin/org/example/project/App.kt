package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.windrad.data.rememberLocationPermissionRequester
import com.windrad.database.DatabaseDriverFactory
import com.windrad.ui.collection.CollectionScreen
import com.windrad.ui.collection.CollectionViewModel
import com.windrad.ui.collection.list.CollectionRoute
import com.windrad.ui.collection.list.StateOverviewScreen
import com.windrad.ui.collection.list.TurbineListScreen
import com.windrad.ui.faq.FaqScreen
import com.windrad.ui.map.MapScreen
import com.windrad.ui.map.MapViewModel
import com.windrad.ui.navigation.WindradBottomBar
import com.windrad.ui.navigation.WindradDestination
import com.windrad.ui.theme.WindradTheme

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    WindradTheme {
        var currentDestination by remember { mutableStateOf(WindradDestination.MAP) }
        val mapViewModel = remember { MapViewModel(driverFactory) }
        val mapUiState by mapViewModel.uiState.collectAsState()
        val collectionViewModel = remember { CollectionViewModel(driverFactory) }
        val collectionUiState by collectionViewModel.uiState.collectAsState()
        val collectionListUiState by collectionViewModel.listUiState.collectAsState()

        val requestLocationPermissionThenScan = rememberLocationPermissionRequester { granted ->
            if (granted) {
                mapViewModel.onScanClicked()
            }
        }

        val showBottomBar = currentDestination != WindradDestination.MAP &&
                collectionListUiState.route != CollectionRoute.TURBINE_LIST

        Box(modifier = Modifier.fillMaxSize()) {
            // Screen Content - keep all screens in composition tree for caching

            // Map Screen
            Box(
                modifier = if (currentDestination == WindradDestination.MAP) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.size(0.dp)
                }
            ) {
                MapScreen(
                    uiState = mapUiState,
                    onScanClicked = { requestLocationPermissionThenScan() },
                    onWindmillSelected = { mapViewModel.selectWindmill(it) },
                    onSearchQueryChanged = { mapViewModel.onSearchQueryChanged(it) },
                    onSearchResultSelected = { mapViewModel.onSearchResultSelected(it) },
                    onClearSearch = { mapViewModel.clearSearch() },
                    onToggleFavorite = { mapViewModel.toggleFavorite(it) },
                    onCollectAllClicked = { mapViewModel.collectAllScanned() },
                    currentDestination = currentDestination,
                    onDestinationSelected = { currentDestination = it },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            // Collection Screen
            Box(
                modifier = if (currentDestination == WindradDestination.COLLECTION) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.size(0.dp)
                }
            ) {
                when (collectionListUiState.route) {
                    CollectionRoute.KPI -> {
                        CollectionScreen(
                            uiState = collectionUiState,
                            onViewCollectionClicked = { collectionViewModel.openStateOverview() },
                            onFaqClicked = { currentDestination = WindradDestination.FAQ },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    CollectionRoute.STATE_OVERVIEW -> {
                        StateOverviewScreen(
                            uiState = collectionListUiState,
                            onBackClicked = { collectionViewModel.back() },
                            onStateSelected = { collectionViewModel.openState(it) },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    CollectionRoute.TURBINE_LIST -> {
                        TurbineListScreen(
                            uiState = collectionListUiState,
                            onBackClicked = { collectionViewModel.back() },
                            onFilterChanged = { collectionViewModel.setFilter(it) },
                            onSearchQueryChanged = { collectionViewModel.onSearchQueryChanged(it) },
                            onTurbineClicked = { collectionViewModel.toggleTurbineExpanded(it) },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }

            // FAQ Screen
            Box(
                modifier = if (currentDestination == WindradDestination.FAQ) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.size(0.dp)
                }
            ) {
                FaqScreen(modifier = Modifier.fillMaxSize())
            }

            if (showBottomBar) {
                WindradBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = { destination ->
                        if (destination != WindradDestination.COLLECTION) {
                            collectionViewModel.resetToKpi()
                        } else {
                            collectionViewModel.reloadOverview()
                        }
                        currentDestination = destination
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 20.dp),
                )
            }
        }
    }
}