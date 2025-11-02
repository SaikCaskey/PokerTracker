package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DecomposeExperimentFlags
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value

class RootComponentImpl(
    componentContext: ComponentContext,
    private val rootNavigator: RootNavigator,
    private val componentFactory: ComponentFactory,
) : RootComponent, ComponentContext by componentContext {

    init {
        // Enable duplicate destinations in the stack
        DecomposeExperimentFlags.duplicateConfigurationsEnabled = true
    }

    override val rootNavigationStack: Value<ChildStack<*, RootDestination>> = childStack(
        source = rootNavigator.navigationSource,
        serializer = NavigationRoute.serializer(),
        initialConfiguration = NavigationRoute.MainRoute,
        handleBackButton = true,
        childFactory = componentFactory::buildChild,
    )

    override fun onBackClicked(toIndex: Int) = rootNavigator.popTo(index = toIndex)
}

private fun ComponentFactory.buildChild(
    r: NavigationRoute,
    ctx: ComponentContext,
): RootDestination = buildComponent(ctx, r)
