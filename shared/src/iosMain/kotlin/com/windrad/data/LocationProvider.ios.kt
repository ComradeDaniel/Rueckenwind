@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlinx.cinterop.BetaInteropApi::class)
package com.windrad.data

import platform.CoreLocation.*
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.cinterop.useContents
import com.windrad.database.DatabaseDriverFactory

actual class LocationProvider actual constructor(driverFactory: DatabaseDriverFactory) {
    private val locationManager = CLLocationManager()
    private val delegate = iOSLocationDelegate()

    init {
        locationManager.delegate = delegate
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }

    actual suspend fun getCurrentLocation(): Pair<Double, Double>? = suspendCoroutine { continuation ->
        delegate.onLocationUpdated = { location ->
            if (location != null) {
                val lat = location.coordinate.useContents { latitude }
                val lon = location.coordinate.useContents { longitude }
                continuation.resume(Pair(lat, lon))
            } else {
                continuation.resume(null)
            }
        }

        val status = CLLocationManager.authorizationStatus()
        if (status == kCLAuthorizationStatusDenied || status == kCLAuthorizationStatusRestricted) {
            continuation.resume(null)
            return@suspendCoroutine
        }
        if (status == kCLAuthorizationStatusNotDetermined) {
            locationManager.requestWhenInUseAuthorization()
        }
        
        locationManager.requestLocation()
    }
}

private class iOSLocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
    var onLocationUpdated: ((CLLocation?) -> Unit)? = null

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val location = didUpdateLocations.lastOrNull() as? CLLocation
        onLocationUpdated?.invoke(location)
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: platform.Foundation.NSError) {
        onLocationUpdated?.invoke(null)
    }
}
