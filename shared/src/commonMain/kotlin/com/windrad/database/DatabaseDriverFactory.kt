package com.windrad.database

import app.cash.sqldelight.db.SqlDriver

/**
 * Plattformspezifische Factory zum Erstellen des SQLite-Drivers.
 * Jede Plattform (Android/iOS) liefert ihre eigene Implementierung,
 * die die pre-populated windrad.db aus den Assets/Bundle-Ressourcen kopiert.
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

/**
 * Erstellt eine [WindradDatabase]-Instanz mit dem plattformspezifischen Driver.
 */
fun createDatabase(driverFactory: DatabaseDriverFactory): WindradDatabase {
    val driver = driverFactory.createDriver()
    return WindradDatabase(driver)
}
