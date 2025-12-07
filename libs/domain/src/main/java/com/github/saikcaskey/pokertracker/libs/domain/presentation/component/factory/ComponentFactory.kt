package com.github.saikcaskey.pokertracker.libs.domain.presentation.component.factory

import com.arkivanov.decompose.ComponentContext

/**
 * Generic Component Factory for building top level Components that are [Destination]s when they
 * are navigated to by using [Route]s, to conveniently contain the logic for building those components
 */
fun interface ComponentFactory<Destination, Route> {
    fun buildComponent(ctx: ComponentContext, route: Route): Destination
}
