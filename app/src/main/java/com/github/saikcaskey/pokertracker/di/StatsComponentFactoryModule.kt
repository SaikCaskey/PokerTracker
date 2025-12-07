package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.feature.stats.components.factory.StatsComponentFactory
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.factory.StatsComponentFactoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val statsComponentFactoryModule = module {
    single<StatsComponentFactory> {
        StatsComponentFactoryImpl(
            dispatchers = get(),
            navigator = get(),
            eventRepository = get(),
            expenseRepository = get(),
            venueRepository = get(),
        )
    }
}

object StatsComponentFactoryProvider : KoinComponent {
    fun provide(): StatsComponentFactory = get()
}
