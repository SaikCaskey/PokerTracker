package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation

interface OnboardingNavigator {

    val navigationSource: NavigationSource<StackNavigation.Event<OnboardingNavigationRoute>>

    fun <R : OnboardingNavigationRoute> push(route: R, onComplete: () -> Unit = {})
    fun pop(onComplete: (Boolean) -> Unit = {})
    fun popTo(index: Int, onComplete: (Boolean) -> Unit = {})

}
