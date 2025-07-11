package com.github.saikcaskey.data.driver

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase

object AndroidSqliteDriverFactory {

    fun create(context: Context): AndroidSqliteDriver {
        return AndroidSqliteDriver(
            PokerTrackerDatabase.Schema,
            context,
            "poker-tracker-database.db",
            callback = object : AndroidSqliteDriver.Callback(PokerTrackerDatabase.Schema) {
                override fun onConfigure(db: SupportSQLiteDatabase) {
                    super.onConfigure(db)
                    db.execSQL("PRAGMA foreign_keys = ON;")
                }
            }
        )
    }
}
