package com.github.saikcaskey.stats.presentation.navigation

import com.github.saikcaskey.pokertracker.domain.presentation.navigation.NavigationRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface StatsPagerNavigationRoute : NavigationRoute {

    @Serializable
    data object EventsRoute: StatsPagerNavigationRoute

    @Serializable
    data object ExpensesRoute : StatsPagerNavigationRoute

    @Serializable
    data object VenuesRoute : StatsPagerNavigationRoute
}
