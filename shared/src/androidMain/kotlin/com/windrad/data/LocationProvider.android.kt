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
        
        // 1. Try GPS Last Known Location
        val gpsLoc = try { locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) } catch (e: SecurityException) { null }
        if (gpsLoc != null) {
            continuation.resume(Pair(gpsLoc.latitude, gpsLoc.longitude))
            return@suspendCoroutine
        }
        
        // 2. Try Network Last Known Location
        val netLoc = try { locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) } catch (e: SecurityException) { null }
        if (netLoc != null) {
            continuation.resume(Pair(netLoc.latitude, netLoc.longitude))
            return@suspendCoroutine
        }
        
        // 3. Fallback to requesting a single update
        val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationManager.GPS_PROVIDER
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            LocationManager.NETWORK_PROVIDER
        } else {
            null
        }
        
        if (provider == null) {
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
