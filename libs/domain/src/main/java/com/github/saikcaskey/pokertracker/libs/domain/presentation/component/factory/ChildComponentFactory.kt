package com.github.saikcaskey.pokertracker.libs.domain.presentation.component.factory

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.FeatureComponent

fun interface ChildComponentFactory<Route> {
    fun buildChildComponent(ctx: ComponentContext, route: Route): FeatureComponent
}
