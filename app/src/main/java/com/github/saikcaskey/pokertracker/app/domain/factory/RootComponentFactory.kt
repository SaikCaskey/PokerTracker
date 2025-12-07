package com.github.saikcaskey.pokertracker.app.domain.factory

import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination
import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.factory.ComponentFactory
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigationRoute

interface RootComponentFactory : ComponentFactory<RootDestination, RootNavigationRoute>
