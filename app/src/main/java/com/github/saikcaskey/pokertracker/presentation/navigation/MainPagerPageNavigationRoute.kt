package com.github.saikcaskey.pokertracker.presentation.navigation

import com.github.saikcaskey.pokertracker.domain.presentation.navigation.NavigationRoute
import kotlinx.serialization.Serializable

@Serializable
sealed class MainPagerPageNavigationRoute : NavigationRoute {

    @Serializable
    data object Dashboard : MainPagerPageNavigationRoute()

    @Serializable
    data object Planner : MainPagerPageNavigationRoute()

    @Serializable
    data object Stats : MainPagerPageNavigationRoute()

    @Serializable
    data object Account : MainPagerPageNavigationRoute()
}
