package com.github.saikcaskey.pokertracker.presentation;

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable;

@Serializable
sealed interface NavigationRoute {
    @Serializable
    data object MainRoute : NavigationRoute

    @Serializable
    data class DayDetailRoute(val date: LocalDate) : NavigationRoute

    @Serializable
    data class VenueDetailRoute(val venueId: Long) : NavigationRoute

    @Serializable
    data class EventDetailRoute(val eventId: Long) : NavigationRoute

    @Serializable
    data class ExpenseDetailRoute(val expenseId: Long) : NavigationRoute

    @Serializable
    data class InsertVenueRoute(val venueId: Long? = null) : NavigationRoute

    @Serializable
    data class InsertEventRoute(
        val existingEventId: Long? = null,
        val venueId: Long? = null,
        val startDate: LocalDate? = null,
    ) : NavigationRoute

    @Serializable
    data class InsertExpenseRoute(
        val existingExpenseId: Long? = null,
        val eventId: Long? = null,
        val venueId: Long? = null,
    ) : NavigationRoute

    @Serializable
    data object ViewVenuesRoute : NavigationRoute

    @Serializable
    data object ViewEventsRoute : NavigationRoute

    @Serializable
    data object ViewExpensesRoute : NavigationRoute
}
