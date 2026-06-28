package com.windrad.data

import androidx.compose.runtime.Composable

/**
 * Provides an Android permission request launcher to the common UI.
 * iOS implementation is a no-op (returns immediately without changing behavior).
 */
@Composable
expect fun rememberLocationPermissionRequester(
    onResult: (granted: Boolean) -> Unit
): () -> Unit

