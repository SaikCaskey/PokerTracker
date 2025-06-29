package com.github.saikcaskey.pokertracker.di

import org.koin.core.module.Module

fun appModule(): List<Module> = listOf(
    dispatchersProvidersModule,
    databaseModule,
    repositoryModule,
)
