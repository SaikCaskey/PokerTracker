package com.github.saikcaskey.pokertracker.domain.factory

import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.factory.ComponentFactory
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigationRoute
import com.github.saikcaskey.pokertracker.presentation.navigation.RootDestination

interface RootComponentFactory : ComponentFactory<RootDestination, RootNavigationRoute>
