package com.github.saikcaskey.pokertracker.domain.factory

import com.github.saikcaskey.pokertracker.domain.presentation.component.factory.ComponentFactory
import com.github.saikcaskey.pokertracker.domain.presentation.NavigationRoute
import com.github.saikcaskey.pokertracker.presentation.RootDestination

interface ComponentFactory : ComponentFactory<RootDestination, NavigationRoute>
