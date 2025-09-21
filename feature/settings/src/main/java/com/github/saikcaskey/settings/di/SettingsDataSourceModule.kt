package com.github.saikcaskey.settings.di

import com.github.saikcaskey.settings.data.datasource.SettingsItemsDataSourceImpl
import com.github.saikcaskey.settings.domain.datasource.datasource.UserSettingsDataSource
import com.github.saikcaskey.settings.domain.datasource.datasource.SettingsItemsDataSource
import com.github.saikcaskey.settings.data.datasource.SettingsUserPreferencesDataSourceImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val settingsDataSourceModule = module {
    single<UserSettingsDataSource> { SettingsUserPreferencesDataSourceImpl(get(), get()) }
    single<SettingsItemsDataSource> { SettingsItemsDataSourceImpl(get(), get()) }
}
