package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface DashboardFeatureComponentImpl : DashboardFeatureComponent {
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

    data class UiState(
        val dashboardEventsData: DashboardEventsData,
        val dashboardProfitSummaryData: DashboardProfitSummaryData,
        val recentVenues: List<Venue>,
        val recentExpenses: List<Expense>
    )
}
