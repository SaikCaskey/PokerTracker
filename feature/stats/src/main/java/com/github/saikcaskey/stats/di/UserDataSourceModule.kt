package com.github.saikcaskey.stats.di

import com.github.saikcaskey.pokertracker.domain.datasource.UserDataSource
import com.github.saikcaskey.stats.data.datasource.UserDataSourceImpl
import org.koin.dsl.module

val userDataSourceModule = module {
    single<UserDataSource> { UserDataSourceImpl(get(), get(), get()) }
}
