package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.data.database.DriverFactoryImpl
import com.github.saikcaskey.pokertracker.domain.DriverFactory
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<PokerTrackerDatabase> { PokerTrackerDatabase(get()) }
    single<DriverFactory> { DriverFactoryImpl(get()) }
}
