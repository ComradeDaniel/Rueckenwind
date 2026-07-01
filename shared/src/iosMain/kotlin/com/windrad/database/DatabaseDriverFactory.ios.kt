@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.windrad.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

/**
 * iOS-Implementierung der DatabaseDriverFactory.
 *
 * Kopiert die pre-populated windrad.db aus dem App-Bundle
 * in das Documents-Verzeichnis, falls sie dort noch nicht existiert.
 * Bei App-Updates wird die DB NICHT überschrieben, damit der
 * Sammelfortschritt des Nutzers erhalten bleibt.
 */
actual class DatabaseDriverFactory actual constructor(context: Any) {

    actual fun createDriver(): SqlDriver {
        val dbName = "windrad.db"
        val fileManager = NSFileManager.defaultManager

        val documentsPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String

        val dbPath = "$documentsPath/$dbName"

        if (!fileManager.fileExistsAtPath(dbPath)) {
            val bundlePath = requireNotNull(
                NSBundle.mainBundle.pathForResource("windrad", ofType = "db")
            ) { "Pre-populated database windrad.db not found in app bundle resources." }

            fileManager.copyItemAtPath(bundlePath, toPath = dbPath, error = null)

            check(fileManager.fileExistsAtPath(dbPath)) {
                "Failed to copy pre-populated database to $dbPath"
            }
        }

return NativeSqliteDriver(
    schema = WindradDatabase.Schema,
    name = dbPath
)
    }
}
