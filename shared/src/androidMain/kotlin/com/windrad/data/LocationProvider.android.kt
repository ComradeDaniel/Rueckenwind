package com.windrad.data

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import com.windrad.database.DatabaseDriverFactory
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull

actual class LocationProvider(private val driverFactory: DatabaseDriverFactory) {
    actual suspend fun getCurrentLocation(): Pair<Double, Double>? {
        val context = driverFactory.context
        val hasFine = context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
        val hasCoarse = context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
        
        if (!hasFine && !hasCoarse) {
            return null
        }
        
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        if (locationManager == null) {
            return null
        }

        // Helper to get best last known location
        fun getLastKnown(): Pair<Double, Double>? {
            try {
                val gpsLoc = if (hasFine) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) else null
                val netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                val best = listOfNotNull(gpsLoc, netLoc).maxByOrNull { it.time }
                if (best != null) {
                    return Pair(best.latitude, best.longitude)
                }
            } catch (e: SecurityException) {
                // ignore
            }
            return null
        }

        // 1. If we have a very recent location (within 30 seconds), return it immediately
        try {
            val gpsLoc = if (hasFine) locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) else null
            val netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val best = listOfNotNull(gpsLoc, netLoc).maxByOrNull { it.time }
            if (best != null && Math.abs(System.currentTimeMillis() - best.time) < 30_000) {
                println("LocationProvider: returning very recent last known location (${System.currentTimeMillis() - best.time}ms old)")
                return Pair(best.latitude, best.longitude)
            }
        } catch (e: Exception) {
            // ignore
        }

        val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationManager.GPS_PROVIDER
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LocationManager.NETWORK_PROVIDER
        } else {
            return getLastKnown()
        }

        // 2. Try to get a fresh location with a 5-second timeout
        val freshLocation = withTimeoutOrNull(5000L) {
            suspendCancellableCoroutine<Pair<Double, Double>?> { continuation ->
                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        locationManager.removeUpdates(this)
                        if (continuation.isActive) {
                            continuation.resume(Pair(location.latitude, location.longitude))
                        }
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }

                continuation.invokeOnCancellation {
                    try {
                        locationManager.removeUpdates(listener)
                    } catch (e: Exception) {
                        // ignore
                    }
                }

                try {
                    locationManager.requestSingleUpdate(provider, listener, Looper.getMainLooper())
                } catch (e: SecurityException) {
                    if (continuation.isActive) continuation.resume(null)
                } catch (e: Exception) {
                    if (continuation.isActive) continuation.resume(null)
                }
            }
        }

        // 3. Fallback to last known if fresh location timed out or failed
        if (freshLocation != null) {
            return freshLocation
        }
        val fallback = getLastKnown()
        println("LocationProvider: fresh location query timed out or failed, fallback to last known: $fallback")
        return fallback
    }
}
