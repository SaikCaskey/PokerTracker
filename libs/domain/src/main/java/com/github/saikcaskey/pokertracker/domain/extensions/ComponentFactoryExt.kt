package com.github.saikcaskey.pokertracker.domain.extensions

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.presentation.component.factory.ComponentFactory

fun <Destination, Route> ComponentFactory<Destination, Route>.buildChild(
    r: Route,
    ctx: ComponentContext,
): Destination = buildComponent(ctx, r)
