package com.github.saikcaskey.pokertracker.app.di

import com.github.saikcaskey.pokertracker.app.domain.factory.RootComponentFactory
import com.github.saikcaskey.pokertracker.app.presentation.components.factory.RootComponentFactoryImpl
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
            userRepository = get(),
            accountSettingsRepository = get(),
            usernameGenerator = get(),
        )
    }
}

object RootComponentFactoryProvider : KoinComponent {
    fun provide(): RootComponentFactory = get()
}
