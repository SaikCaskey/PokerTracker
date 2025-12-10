package com.github.saikcaskey.pokertracker.feature.onboarding.components

import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables.OnboardingFeatureDestination
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation.OnboardingNavigationRoute
import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.factory.ComponentFactory

interface OnboardingComponentFactory :
    ComponentFactory<OnboardingFeatureDestination, OnboardingNavigationRoute>
