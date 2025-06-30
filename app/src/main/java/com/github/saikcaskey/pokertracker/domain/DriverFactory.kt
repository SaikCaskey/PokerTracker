package com.github.saikcaskey.pokertracker.domain

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase

interface DriverFactory {
    fun createDriver(): SqlDriver
}



fun createDatabase(driverFactory: DriverFactory): PokerTrackerDatabase {
    val driver = driverFactory.createDriver()
    return PokerTrackerDatabase(driver)
//        .apply { SampleDataSeederImpl().seedSampleData(this) }
}