package com.windrad.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import java.io.File
import java.io.FileOutputStream

/**
 * Android-Implementierung der DatabaseDriverFactory.
 *
 * Kopiert die pre-populated windrad.db aus dem Assets-Ordner
 * in das Datenbank-Verzeichnis der App, falls sie dort noch nicht existiert.
 * Bei App-Updates wird die DB NICHT überschrieben, damit der
 * Sammelfortschritt des Nutzers erhalten bleibt.
 */
actual class DatabaseDriverFactory actual constructor(val context: Any) {

    val androidContext = context as Context

    actual fun createDriver(): SqlDriver {
        val dbName = "windrad.db"
        val dbFile = androidContext.getDatabasePath(dbName)

        if (!dbFile.exists()) {
            copyDatabaseFromAssets(dbFile, dbName)
        }

        return AndroidSqliteDriver(
            schema = WindradDatabase.Schema,
            context = androidContext,
            name = dbName
        )
    }

    private fun copyDatabaseFromAssets(dbFile: File, dbName: String) {
        dbFile.parentFile?.mkdirs()
        androidContext.assets.open("databases/$dbName").use { input ->
            FileOutputStream(dbFile).use { output ->
                input.copyTo(output)
            }
        }
    }
}
