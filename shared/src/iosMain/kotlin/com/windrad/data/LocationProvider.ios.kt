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
            if (delegate.onContinuousLocationUpdate != null) {
                // If continuous updates are active, just wait for the next update
                // Since we already set onLocationUpdated, it will fire automatically on the next tick
                // Or if we already have a location, we could return it immediately, but let's wait
            } else {
                locationManager.requestLocation()
            }
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
            // Check if we already have a location from continuous updates
            val lastLoc = delegate.lastLocation
            if (lastLoc != null) {
                cleanUp()
                if (continuation.isActive) {
                    val lat = lastLoc.coordinate.useContents { latitude }
                    val lon = lastLoc.coordinate.useContents { longitude }
                    continuation.resume(Pair(lat, lon))
                }
            } else {
                startRequestingLocation()
            }
        }
    }
    actual fun startLocationUpdates(onUpdate: (Double, Double) -> Unit) {
        delegate.onContinuousLocationUpdate = { location ->
            if (location != null) {
                val lat = location.coordinate.useContents { latitude }
                val lon = location.coordinate.useContents { longitude }
                onUpdate(lat, lon)
            }
        }
        
        fun startRequestingLocation() {
            locationManager.startUpdatingLocation()
        }

        val status = CLLocationManager.authorizationStatus()
        if (status == kCLAuthorizationStatusNotDetermined) {
            delegate.onAuthorizationChanged = { newStatus ->
                if (newStatus != kCLAuthorizationStatusNotDetermined) {
                    delegate.onAuthorizationChanged = null
                    if (newStatus == kCLAuthorizationStatusAuthorizedWhenInUse || newStatus == kCLAuthorizationStatusAuthorizedAlways) {
                        startRequestingLocation()
                    }
                }
            }
            locationManager.requestWhenInUseAuthorization()
        } else if (status == kCLAuthorizationStatusAuthorizedWhenInUse || status == kCLAuthorizationStatusAuthorizedAlways) {
            startRequestingLocation()
        }
    }

    actual fun stopLocationUpdates() {
        locationManager.stopUpdatingLocation()
        delegate.onContinuousLocationUpdate = null
    }
}

private class iOSLocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
    var onLocationUpdated: ((CLLocation?) -> Unit)? = null
    var onContinuousLocationUpdate: ((CLLocation?) -> Unit)? = null
    var onAuthorizationChanged: ((CLAuthorizationStatus) -> Unit)? = null
    var lastLocation: CLLocation? = null

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        onAuthorizationChanged?.invoke(manager.authorizationStatus)
    }

    override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
        onAuthorizationChanged?.invoke(didChangeAuthorizationStatus)
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val location = didUpdateLocations.lastOrNull() as? CLLocation
        if (location != null) {
            lastLocation = location
        }
        onLocationUpdated?.invoke(location)
        onContinuousLocationUpdate?.invoke(location)
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: platform.Foundation.NSError) {
        onLocationUpdated?.invoke(null)
        onContinuousLocationUpdate?.invoke(null)
    }
}
