package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import com.windrad.database.DatabaseDriverFactory
import com.windrad.data.rememberLocationPermissionRequester
import com.windrad.ui.collection.CollectionScreen
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

        // Android: request location permission before running the scan.
        // iOS: no-op (keeps existing behavior unchanged).
        val requestLocationPermissionThenScan = rememberLocationPermissionRequester { granted ->
            if (granted) {
                mapViewModel.onScanClicked()
            } else {
                // Permission denied: keep behavior safe by doing nothing.
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // Screen Content - keep all screens in composition tree for caching
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
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = if (currentDestination == WindradDestination.COLLECTION) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.size(0.dp)
                }
            ) {
                CollectionScreen(modifier = Modifier.fillMaxSize())
            }

            Box(
                modifier = if (currentDestination == WindradDestination.FAQ) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier.size(0.dp)
                }
            ) {
                FaqScreen(modifier = Modifier.fillMaxSize())
            }

            // Floating Bottom Navigation Bar (only visible on non-map screens)
            if (currentDestination != WindradDestination.MAP) {
                WindradBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = { currentDestination = it },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 20.dp)
                )
            }
        }
    }
}