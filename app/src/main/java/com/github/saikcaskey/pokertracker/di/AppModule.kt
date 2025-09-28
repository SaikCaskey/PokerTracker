package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.account.di.accountSettingsDataSourceModule
import com.github.saikcaskey.account.di.accountSettingsDataStoreModule
import com.github.saikcaskey.account.di.accountSettingsRepositoryModule
import org.koin.core.module.Module

fun appModules(): List<Module> {
    return listOf(
        appInfoModule,
        databaseModule,
        dispatchersProvidersModule,
        coreRepositoryModule,
        accountSettingsRepositoryModule,
        accountSettingsDataStoreModule,
        accountSettingsDataSourceModule,
    )
}
