package com.github.saikcaskey.pokertracker.presentation.navigation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigationRoute
import kotlinx.datetime.LocalDate

class RootNavigatorImpl : RootNavigator {

    private val navigator = StackNavigation<RootNavigationRoute>()

    override val navigationSource: NavigationSource<StackNavigation.Event<RootNavigationRoute>> get() = navigator

    override fun <R : RootNavigationRoute> push(route: R, onComplete: () -> Unit) {
        navigator.push(route, onComplete)
    }

    override fun pop(onComplete: (Boolean) -> Unit) {
        navigator.pop(onComplete)
    }

    override fun popTo(index: Int, onComplete: (Boolean) -> Unit) {
        navigator.popTo(index)
    }

    override fun onShowInsertEvent(existingEventId: Long?, venueId: Long?, startDate: LocalDate?) {
        navigator.push(
            RootNavigationRoute.InsertEventRoute(
                existingEventId = existingEventId,
                venueId = venueId,
                startDate = startDate
            )
        )
    }

    override fun onShowInsertExpense(existingExpenseId: Long?, eventId: Long?, venueId: Long?) {
        navigator.push(
            RootNavigationRoute.InsertExpenseRoute(
                existingExpenseId = existingExpenseId,
                eventId = eventId,
                venueId = venueId
            )
        )
    }

    override fun onShowInsertVenue(existingVenueId: Long?) {
        navigator.push(RootNavigationRoute.InsertVenueRoute(venueId = existingVenueId))
    }

    override fun onShowEventDetail(eventId: Long) {
        navigator.push(RootNavigationRoute.EventDetailRoute(eventId = eventId))
    }

    override fun onShowExpenseDetail(expenseId: Long) {
        navigator.push(RootNavigationRoute.ExpenseDetailRoute(expenseId = expenseId))
    }

    override fun onShowVenueDetail(venueId: Long) {
        navigator.push(RootNavigationRoute.VenueDetailRoute(venueId = venueId))
    }

    override fun onShowAllEvents() {
        navigator.push(RootNavigationRoute.ViewEventsRoute)
    }

    override fun onShowAllExpenses() {
        navigator.push(RootNavigationRoute.ViewExpensesRoute)
    }

    override fun onShowAllVenues() {
        navigator.push(RootNavigationRoute.ViewVenuesRoute)
    }
}
