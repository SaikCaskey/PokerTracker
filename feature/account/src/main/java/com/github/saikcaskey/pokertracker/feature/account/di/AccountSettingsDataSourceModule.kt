package com.github.saikcaskey.pokertracker.feature.account.di

import com.github.saikcaskey.pokertracker.feature.account.data.datasource.AccountSettingsDataSourceImpl
import com.github.saikcaskey.pokertracker.libs.domain.datasource.AccountSettingsDataSource
import org.koin.dsl.module

val accountSettingsDataSourceModule = module {
    single<AccountSettingsDataSource> { AccountSettingsDataSourceImpl(get(), get()) }
}
