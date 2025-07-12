package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface MainPagerPageDashboardComponent : MainPagerPageComponent {
    val recentEvents: StateFlow<List<Event>>
    val todayEvents: StateFlow<List<Event>>
    val upcomingEvents: StateFlow<List<Event>>
    val recentExpenses: StateFlow<List<Expense>>
    val recentVenues: StateFlow<List<Venue>>
    val balance: StateFlow<Double>
    val upcomingCosts: StateFlow<Double>
    val balanceForYear: StateFlow<Double>
    val balanceForMonth: StateFlow<Double>

    fun onShowEventDetailClicked(id: Long)
    fun onShowExpenseDetailClicked(id: Long)
    fun onShowInsertEventClicked()
    fun onShowInsertVenueClicked()
    fun onShowInsertExpenseClicked()
    fun onShowVenueDetailClicked(id: Long)
    fun onShowAllExpensesClicked()
    fun onShowAllEventsClicked()
    fun onShowAllVenuesClicked()
}
