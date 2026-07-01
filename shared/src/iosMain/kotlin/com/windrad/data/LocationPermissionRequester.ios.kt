package com.windrad.data

import androidx.compose.runtime.Composable

/**
 * iOS: keep existing behavior. We intentionally do not request permission here.
 * The UI will behave like before (iOS permission flow handled elsewhere or by the OS).
 */
@Composable
actual fun rememberLocationPermissionRequester(
    onResult: (granted: Boolean) -> Unit
): () -> Unit {
    return {
        // No-op: keep behavior unchanged.
        onResult(true)
    }
}

