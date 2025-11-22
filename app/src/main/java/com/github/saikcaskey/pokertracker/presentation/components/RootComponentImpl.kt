package com.github.saikcaskey.pokertracker.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DecomposeExperimentFlags
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.domain.component.RootComponent
import com.github.saikcaskey.pokertracker.domain.factory.RootComponentFactory
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigationRoute
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.presentation.navigation.RootDestination

class RootComponentImpl(
    componentContext: ComponentContext,
    private val rootNavigator: RootNavigator,
    private val componentFactory: RootComponentFactory,
) : RootComponent, ComponentContext by componentContext {

    init {
        // Enable duplicate destinations in the stack
        DecomposeExperimentFlags.duplicateConfigurationsEnabled = true
    }

    override val rootNavigationStack: Value<ChildStack<*, RootDestination>> = childStack(
        source = rootNavigator.navigationSource,
        serializer = RootNavigationRoute.serializer(),
        initialConfiguration = RootNavigationRoute.MainRoute,
        handleBackButton = true,
        childFactory = { r, ctx -> componentFactory.buildComponent(ctx,r) },
    )

    override fun onBackClicked(toIndex: Int) = rootNavigator.popTo(index = toIndex)
}
