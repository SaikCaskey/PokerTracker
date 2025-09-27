package com.github.saikcaskey.settings.di

import com.github.saikcaskey.settings.data.datasource.SettingsDataSourceImpl
import com.github.saikcaskey.settings.domain.datasource.SettingsDataSource
import org.koin.dsl.module

val settingsDataSourceModule = module {
    single<SettingsDataSource> { SettingsDataSourceImpl(get(), get()) }
}
