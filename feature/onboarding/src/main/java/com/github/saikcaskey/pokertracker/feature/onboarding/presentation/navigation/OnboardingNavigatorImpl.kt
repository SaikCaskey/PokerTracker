package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push

class OnboardingNavigatorImpl : OnboardingNavigator {

    private val navigator = StackNavigation<OnboardingNavigationRoute>()

    override val navigationSource: NavigationSource<StackNavigation.Event<OnboardingNavigationRoute>> get() = navigator

    override fun <R : OnboardingNavigationRoute> push(route: R, onComplete: () -> Unit) {
        navigator.push(route, onComplete)
    }

    override fun pop(onComplete: (Boolean) -> Unit) {
        navigator.pop(onComplete)
    }

    override fun popTo(index: Int, onComplete: (Boolean) -> Unit) {
        navigator.popTo(index)
    }
}
