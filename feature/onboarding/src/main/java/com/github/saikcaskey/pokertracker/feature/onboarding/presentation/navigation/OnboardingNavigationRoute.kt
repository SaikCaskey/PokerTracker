package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation

import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.NavigationRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface OnboardingNavigationRoute : NavigationRoute {

    @Serializable
    data object IntroRoute : OnboardingNavigationRoute

    @Serializable
    data object CreateAccountRoute : OnboardingNavigationRoute

    @Serializable
    data object InstructionsRoute : OnboardingNavigationRoute
}
