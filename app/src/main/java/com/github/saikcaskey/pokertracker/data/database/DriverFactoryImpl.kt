package com.github.saikcaskey.pokertracker.data.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.saikcaskey.pokertracker.domain.DriverFactory
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase

class DriverFactoryImpl(private val context: Context) : DriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            PokerTrackerDatabase.Schema,
            context,
            "poker-tracker-database",
            callback = object : AndroidSqliteDriver.Callback(PokerTrackerDatabase.Schema) {
                override fun onConfigure(db: SupportSQLiteDatabase) {
                    super.onConfigure(db)
                    // Enable foreign_keys between tables
                    db.execSQL("PRAGMA foreign_keys = ON;")
                }
            }
        )
    }
}