package com.github.saikcaskey.pokertracker.domain.presentation.navigation

import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.stack.StackNavigation
import kotlinx.datetime.LocalDate

interface RootNavigator {

    val navigationSource: NavigationSource<StackNavigation.Event<RootNavigationRoute>>

    fun <R : NavigationRoute> push(route: R, onComplete: () -> Unit = {})
    fun pop(onComplete: (Boolean) -> Unit = {})
    fun popTo(index: Int, onComplete: (Boolean) -> Unit = {})

    fun onShowCalendarDayDetail(date: LocalDate, hasEvent: Boolean)
    fun onShowEventDetail(eventId: Long)
    fun onShowExpenseDetail(expenseId: Long)
    fun onShowVenueDetail(venueId: Long)

    fun onShowAllEvents()
    fun onShowAllExpenses()
    fun onShowAllVenues()

    fun onShowInsertEvent(
        existingEventId: Long? = null,
        venueId: Long? = null,
        startDate: LocalDate? = null,
    )

    fun onShowInsertExpense(
        existingExpenseId: Long? = null,
        eventId: Long? = null,
        venueId: Long? = null,
    )

    fun onShowInsertVenue(existingVenueId: Long? = null)
}
