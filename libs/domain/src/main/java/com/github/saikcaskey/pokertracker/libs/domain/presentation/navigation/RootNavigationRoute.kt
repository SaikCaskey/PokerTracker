package com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed interface RootNavigationRoute : NavigationRoute {

    @Serializable
    data object OnboardingRoute : RootNavigationRoute

    @Serializable
    data object DashboardRoute : RootNavigationRoute

    @Serializable
    data object AccountRoute : RootNavigationRoute

    @Serializable
    data object StatsRoute : RootNavigationRoute

    @Serializable
    data object PlannerRoute : RootNavigationRoute

    @Serializable
    data class DayDetailRoute(val date: LocalDate) : RootNavigationRoute

    @Serializable
    data class VenueDetailRoute(val venueId: Long) : RootNavigationRoute

    @Serializable
    data class EventDetailRoute(val eventId: Long) : RootNavigationRoute

    @Serializable
    data class ExpenseDetailRoute(val expenseId: Long) : RootNavigationRoute

    @Serializable
    data class InsertVenueRoute(val venueId: Long? = null) : RootNavigationRoute

    @Serializable
    data class InsertEventRoute(
        val existingEventId: Long? = null,
        val venueId: Long? = null,
        val startDate: LocalDate? = null,
    ) : RootNavigationRoute

    @Serializable
    data class InsertExpenseRoute(
        val existingExpenseId: Long? = null,
        val eventId: Long? = null,
        val venueId: Long? = null,
    ) : RootNavigationRoute

    @Serializable
    data object ViewVenuesRoute : RootNavigationRoute

    @Serializable
    data object ViewEventsRoute : RootNavigationRoute

    @Serializable
    data object ViewExpensesRoute : RootNavigationRoute
}
