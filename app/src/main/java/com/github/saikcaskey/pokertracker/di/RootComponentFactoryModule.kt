package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.domain.factory.RootComponentFactory
import com.github.saikcaskey.pokertracker.presentation.components.factory.RootComponentFactoryImpl
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigationRoute
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val rootComponentFactoryModule = module {
    single<RootComponentFactory> {
        RootComponentFactoryImpl(
            dispatchers = get(),
            navigator = get(),
            eventRepository = get(),
            expenseRepository = get(),
            venueRepository = get(),
            accountSettingsRepository = get(),
            userRepository = get()
        )
    }
}

object RootComponentFactoryProvider : KoinComponent {
    fun provide(): RootComponentFactory = get()
}
