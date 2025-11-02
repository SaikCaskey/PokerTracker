package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.ComponentContext

fun interface ComponentFactory {
    fun buildComponent(ctx: ComponentContext, route: NavigationRoute): RootDestination
}
