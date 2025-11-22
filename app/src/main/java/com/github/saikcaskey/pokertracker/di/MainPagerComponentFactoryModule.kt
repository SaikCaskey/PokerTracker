package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.domain.factory.MainPagerComponentFactory
import com.github.saikcaskey.pokertracker.presentation.components.factory.MainPagerComponentFactoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val mainPagerComponentFactoryModule = module {
    single<MainPagerComponentFactory> {
        MainPagerComponentFactoryImpl(
            dispatchers = get(),
            navigator = get(),
            eventRepository = get(),
            expenseRepository = get(),
            accountSettingsRepository = get(),
            userRepository = get(),
            venueRepository = get(),
        )
    }
}

object MainPagerComponentFactoryProvider : KoinComponent {
    fun provide(): MainPagerComponentFactory = get()
}
