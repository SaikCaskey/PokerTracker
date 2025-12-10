package com.github.saikcaskey.pokertracker.app.di

import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootNavigatorImpl
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation.OnboardingNavigator
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation.OnboardingNavigatorImpl
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val navigationModule = module {
    single<RootNavigator> { RootNavigatorImpl() }
    single<OnboardingNavigator> { OnboardingNavigatorImpl() }
}

object RootNavigatorProvider : KoinComponent {
    fun provide(): RootNavigator = get()
}
object OnboardingNavigatorProvider : KoinComponent {
    fun provide(): OnboardingNavigator = get()
}
