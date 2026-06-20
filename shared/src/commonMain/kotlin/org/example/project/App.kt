package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.windrad.database.DatabaseDriverFactory
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

        Box(modifier = Modifier.fillMaxSize()) {
            // Screen Content
            when (currentDestination) {
                WindradDestination.MAP -> {
                    MapScreen(
                        uiState = mapUiState,
                        onScanClicked = { mapViewModel.onScanClicked() },
                        onWindmillSelected = { mapViewModel.selectWindmill(it) },
                        currentDestination = currentDestination,
                        onDestinationSelected = { currentDestination = it },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                WindradDestination.COLLECTION -> {
                    CollectionScreen(modifier = Modifier.fillMaxSize())
                }
                WindradDestination.FAQ -> {
                    FaqScreen(modifier = Modifier.fillMaxSize())
                }
            }

            // Floating Bottom Navigation Bar (only visible on non-map screens)
            if (currentDestination != WindradDestination.MAP) {
                WindradBottomBar(
                    currentDestination = currentDestination,
                    onDestinationSelected = { currentDestination = it },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}