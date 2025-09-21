package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.settings.di.settingsDataSourceModule
import com.github.saikcaskey.settings.di.settingsDataStoreModule
import com.github.saikcaskey.settings.di.settingsRepositoryModule
import org.koin.core.module.Module

fun appModules(): List<Module> = listOf(
    databaseModule,
    dispatchersProvidersModule,
    coreRepositoryModule,
    settingsRepositoryModule,
    settingsDataStoreModule,
    settingsDataSourceModule,
)
