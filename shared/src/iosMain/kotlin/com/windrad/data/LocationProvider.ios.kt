@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlinx.cinterop.BetaInteropApi::class)
package com.windrad.data

import platform.CoreLocation.*
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.cinterop.useContents
import com.windrad.database.DatabaseDriverFactory

actual class LocationProvider actual constructor(driverFactory: DatabaseDriverFactory) {
    private val locationManager = CLLocationManager()
    private val delegate = iOSLocationDelegate()

    init {
        locationManager.delegate = delegate
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }

    actual suspend fun getCurrentLocation(): Pair<Double, Double>? = suspendCancellableCoroutine { continuation ->
        val cleanUp = {
            delegate.onLocationUpdated = null
            delegate.onAuthorizationChanged = null
        }

        delegate.onLocationUpdated = { location ->
            cleanUp()
            if (continuation.isActive) {
                if (location != null) {
                    val lat = location.coordinate.useContents { latitude }
                    val lon = location.coordinate.useContents { longitude }
                    continuation.resume(Pair(lat, lon))
                } else {
                    continuation.resume(null)
                }
            }
        }

        continuation.invokeOnCancellation {
            cleanUp()
        }

        fun startRequestingLocation() {
            locationManager.requestLocation()
        }

        val status = CLLocationManager.authorizationStatus()
        if (status == kCLAuthorizationStatusDenied || status == kCLAuthorizationStatusRestricted) {
            cleanUp()
            if (continuation.isActive) {
                continuation.resume(null)
            }
        } else if (status == kCLAuthorizationStatusNotDetermined) {
            delegate.onAuthorizationChanged = { newStatus ->
                if (newStatus != kCLAuthorizationStatusNotDetermined) {
                    delegate.onAuthorizationChanged = null
                    if (newStatus == kCLAuthorizationStatusAuthorizedWhenInUse || newStatus == kCLAuthorizationStatusAuthorizedAlways) {
                        startRequestingLocation()
                    } else {
                        cleanUp()
                        if (continuation.isActive) {
                            continuation.resume(null)
                        }
                    }
                }
            }
            locationManager.requestWhenInUseAuthorization()
        } else {
            startRequestingLocation()
        }
    }
}

private class iOSLocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
    var onLocationUpdated: ((CLLocation?) -> Unit)? = null
    var onAuthorizationChanged: ((CLAuthorizationStatus) -> Unit)? = null

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        onAuthorizationChanged?.invoke(manager.authorizationStatus)
    }

    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
        onAuthorizationChanged?.invoke(didChangeAuthorizationStatus)
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val location = didUpdateLocations.lastOrNull() as? CLLocation
        onLocationUpdated?.invoke(location)
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: platform.Foundation.NSError) {
        onLocationUpdated?.invoke(null)
    }
}
