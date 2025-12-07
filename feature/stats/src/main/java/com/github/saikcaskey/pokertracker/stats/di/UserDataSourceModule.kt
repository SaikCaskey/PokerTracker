package com.github.saikcaskey.pokertracker.stats.di

import com.github.saikcaskey.pokertracker.libs.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.stats.data.datasource.UserDataSourceImpl
import org.koin.dsl.module

val userDataSourceModule = module {
    single<UserDataSource> { UserDataSourceImpl(get(), get(), get()) }
}
