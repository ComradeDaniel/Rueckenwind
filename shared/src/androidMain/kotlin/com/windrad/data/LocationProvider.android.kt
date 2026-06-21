package com.windrad.data

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import com.windrad.database.DatabaseDriverFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationProvider(private val driverFactory: DatabaseDriverFactory) {
    actual suspend fun getCurrentLocation(): Pair<Double, Double>? = suspendCoroutine { continuation ->
        val context = driverFactory.context
        val hasFine = context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
        val hasCoarse = context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
        
        if (!hasFine && !hasCoarse) {
            continuation.resume(null)
            return@suspendCoroutine
        }
        
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        if (locationManager == null) {
            continuation.resume(null)
            return@suspendCoroutine
        }
        
        // Always request a fresh single location update to avoid stale cached locations
        val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationManager.GPS_PROVIDER
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LocationManager.NETWORK_PROVIDER
        } else {
            continuation.resume(null)
            return@suspendCoroutine
        }

        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationManager.removeUpdates(this)
                continuation.resume(Pair(location.latitude, location.longitude))
            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        try {
            locationManager.requestSingleUpdate(provider, listener, Looper.getMainLooper())
        } catch (e: SecurityException) {
            continuation.resume(null)
        } catch (e: Exception) {
            continuation.resume(null)
        }
    }
}
