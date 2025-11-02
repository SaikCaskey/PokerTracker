package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.presentation.ComponentFactory
import com.github.saikcaskey.pokertracker.presentation.ComponentFactoryImpl
import com.github.saikcaskey.pokertracker.presentation.RootNavigator
import com.github.saikcaskey.pokertracker.presentation.RootNavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val componentFactoryModule = module {
    single<ComponentFactory> {
        ComponentFactoryImpl(
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

object ComponentFactoryProvider : KoinComponent {
    fun provide(): ComponentFactory = get()
}
