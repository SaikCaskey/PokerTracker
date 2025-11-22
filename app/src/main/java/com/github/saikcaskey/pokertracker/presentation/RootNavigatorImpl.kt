package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.github.saikcaskey.pokertracker.domain.presentation.RootNavigator
import com.github.saikcaskey.pokertracker.domain.presentation.NavigationRoute
import kotlinx.datetime.LocalDate

class RootNavigatorImpl : RootNavigator {

    private val navigator = StackNavigation<NavigationRoute>()

    override val navigationSource: NavigationSource<StackNavigation.Event<NavigationRoute>> get() = navigator

    override fun <R : NavigationRoute> push(route: R, onComplete: () -> Unit) {
        navigator.push(route, onComplete)
    }

    override fun pop(onComplete: (Boolean) -> Unit) {
        navigator.pop(onComplete)
    }

    override fun popTo(index: Int, onComplete: (Boolean) -> Unit) {
        navigator.popTo(index)
    }

    override fun onShowCalendarDayDetail(
        date: LocalDate,
        hasEvent: Boolean,
    ) {
        navigator.push(NavigationRoute.DayDetailRoute(date = date))
    }

    override fun onShowInsertEvent(existingEventId: Long?, venueId: Long?, startDate: LocalDate?) {
        navigator.push(
            NavigationRoute.InsertEventRoute(
                existingEventId = existingEventId,
                venueId = venueId,
            )
        )
    }

    override fun onShowInsertExpense(existingExpenseId: Long?, eventId: Long?, venueId: Long?) {
        navigator.push(
            NavigationRoute.InsertExpenseRoute(
                existingExpenseId = existingExpenseId,
                eventId = eventId,
                venueId = venueId
            )
        )
    }

    override fun onShowInsertVenue(existingVenueId: Long?) {
        navigator.push(NavigationRoute.InsertVenueRoute(venueId = existingVenueId))
    }

    override fun onShowEventDetail(eventId: Long) {
        navigator.push(NavigationRoute.EventDetailRoute(eventId = eventId))
    }

    override fun onShowExpenseDetail(expenseId: Long) {
        navigator.push(NavigationRoute.ExpenseDetailRoute(expenseId = expenseId))
    }

    override fun onShowVenueDetail(venueId: Long) {
        navigator.push(NavigationRoute.VenueDetailRoute(venueId = venueId))
    }

    override fun onShowAllEvents() {
        navigator.push(NavigationRoute.ViewEventsRoute)
    }

    override fun onShowAllExpenses() {
        navigator.push(NavigationRoute.ViewExpensesRoute)
    }

    override fun onShowAllVenues() {
        navigator.push(NavigationRoute.ViewVenuesRoute)
    }
}
