package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.windrad.database.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    val driverFactory = remember { DatabaseDriverFactory() }
    App(driverFactory = driverFactory)
}