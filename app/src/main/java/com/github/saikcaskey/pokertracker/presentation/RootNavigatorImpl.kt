package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push

class RootNavigatorImpl : RootNavigator {

    private val navigator = StackNavigation<NavigationRoute>()

    override val navigationSource: NavigationSource<StackNavigation.Event<NavigationRoute>> get() = navigator

    override fun push(route: NavigationRoute, onComplete: () -> Unit) {
        navigator.push(route, onComplete)
    }

    override fun pop(onComplete: (Boolean) -> Unit) {
        navigator.pop(onComplete)
    }

    override fun popTo(index: Int, onComplete: (Boolean) -> Unit) {
        navigator.popTo(index)
    }

}
