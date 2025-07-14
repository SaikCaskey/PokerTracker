package com.github.saikcaskey.pokertracker.di

import app.cash.sqldelight.db.SqlDriver
import com.github.saikcaskey.data.database.DriverFactoryImpl
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> { DriverFactoryImpl(get()).createDriver() }
    single<PokerTrackerDatabase> { PokerTrackerDatabase(get()) }
}

object PokerTrackerDatabaseProvider : KoinComponent {
    fun provide(): PokerTrackerDatabase = get()
}

