package com.github.saikcaskey.pokertracker.di

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.github.saikcaskey.pokertracker.data.database.DriverFactoryImpl
import com.github.saikcaskey.pokertracker.domain.DriverFactory
import com.github.saikcaskey.pokertracker.shared.database.PokerTrackerDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val databaseModule = module {
    single<PokerTrackerDatabase> {
        PokerTrackerDatabase(
            AndroidSqliteDriver(
                PokerTrackerDatabase.Schema,
                get(),
                "test.db",
                callback = object : AndroidSqliteDriver.Callback(PokerTrackerDatabase.Schema) {
                    override fun onConfigure(db: SupportSQLiteDatabase) {
                        super.onConfigure(db)
                        db.execSQL("PRAGMA foreign_keys = ON;")
                    }
                }
            )
        )
    }
    single<DriverFactory> { DriverFactoryImpl(get()) }
}

object DriverFactoryProvider : KoinComponent {
    fun provide(): DriverFactory = get()
}

object DatabaseProvider : KoinComponent {
    fun provide(): PokerTrackerDatabase = get()
}