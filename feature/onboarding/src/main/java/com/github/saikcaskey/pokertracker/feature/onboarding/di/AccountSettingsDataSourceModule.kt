package com.github.saikcaskey.pokertracker.feature.onboarding.di

import com.github.saikcaskey.pokertracker.feature.onboarding.components.OnboardingComponentFactory
import com.github.saikcaskey.pokertracker.feature.onboarding.components.OnboardingComponentFactoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val onboardingComponentFactoryModule = module {
    single<OnboardingComponentFactory> {
        OnboardingComponentFactoryImpl(
            dispatchers = get(),
            navigator = get(),
        )
    }
}

object OnboardingComponentFactoryProvider : KoinComponent {
    fun provide(): OnboardingComponentFactory = get()
}
