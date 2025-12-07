package com.github.saikcaskey.pokertracker.account.di

import com.github.saikcaskey.pokertracker.account.data.datasource.AccountSettingsDataSourceImpl
import com.github.saikcaskey.pokertracker.domain.datasource.AccountSettingsDataSource
import org.koin.dsl.module

val accountSettingsDataSourceModule = module {
    single<AccountSettingsDataSource> { AccountSettingsDataSourceImpl(get(), get()) }
}
