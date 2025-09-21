package com.github.saikcaskey.pokertracker.di

import org.koin.core.module.Module

fun appModules(): List<Module> = listOf(
    databaseModule,
    repositoryModule,
    dispatchersProvidersModule,
)
