package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation

interface RootNavigator {

    val navigationSource: NavigationSource<StackNavigation.Event<NavigationRoute>>

    fun push(route: NavigationRoute, onComplete: () -> Unit = {})
    fun pop(onComplete: (Boolean) -> Unit = {})
    fun popTo(index: Int, onComplete: (Boolean) -> Unit = {})
}
