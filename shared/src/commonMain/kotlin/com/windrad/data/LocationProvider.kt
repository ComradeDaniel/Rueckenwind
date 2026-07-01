package com.windrad.data

import com.windrad.database.DatabaseDriverFactory

expect class LocationProvider(driverFactory: DatabaseDriverFactory) {
    suspend fun getCurrentLocation(): Pair<Double, Double>?
    fun startLocationUpdates(onUpdate: (Double, Double) -> Unit)
    fun stopLocationUpdates()
}
