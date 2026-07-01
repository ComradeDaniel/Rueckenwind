@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.windrad.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSUserDomainMask

/**
 * iOS-Implementierung der DatabaseDriverFactory.
 *
 * Kopiert die pre-populated windrad.db aus dem App-Bundle
 * in das Application Support/databases Verzeichnis, falls sie dort noch nicht existiert.
 * Bei App-Updates wird die DB NICHT überschrieben, damit der
 * Sammelfortschritt des Nutzers erhalten bleibt.
 */
actual class DatabaseDriverFactory actual constructor(context: Any) {

    actual fun createDriver(): SqlDriver {
        val dbName = "windrad.db"
        val fileManager = NSFileManager.defaultManager

        val appSupportPath = NSSearchPathForDirectoriesInDomains(
            NSApplicationSupportDirectory,
            NSUserDomainMask,
            true
        ).first() as String

        val dbDir = "$appSupportPath/databases"
        val dbPath = "$dbDir/$dbName"

        if (!fileManager.fileExistsAtPath(dbPath)) {
            if (!fileManager.fileExistsAtPath(dbDir)) {
                fileManager.createDirectoryAtPath(
                    path = dbDir,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = null
                )
            }

            val bundlePath = NSBundle.mainBundle.pathForResource("windrad", ofType = "db")
            if (bundlePath != null) {
                fileManager.copyItemAtPath(bundlePath, toPath = dbPath, error = null)
            } else {
                println("Warning: Pre-populated database windrad.db not found in app bundle resources. Proceeding with empty database.")
            }

            if (bundlePath != null) {
                check(fileManager.fileExistsAtPath(dbPath)) {
                    "Failed to copy pre-populated database to $dbPath"
                }
            }
        }

return NativeSqliteDriver(
    schema = WindradDatabase.Schema,
    name = dbName
)
    }
}
