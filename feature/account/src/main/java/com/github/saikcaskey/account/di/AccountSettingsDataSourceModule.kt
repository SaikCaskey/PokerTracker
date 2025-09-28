package com.github.saikcaskey.account.di

import com.github.saikcaskey.account.data.datasource.AccountSettingsDataSourceImpl
import com.github.saikcaskey.account.domain.datasource.AccountSettingsDataSource
import org.koin.dsl.module

val accountSettingsDataSourceModule = module {
    single<AccountSettingsDataSource> { AccountSettingsDataSourceImpl(get(), get()) }
}
