package com.github.saikcaskey.pokertracker.dashboard.presentation

import com.github.saikcaskey.pokertracker.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.presentation.MainPagerPageComponent
import kotlinx.coroutines.flow.Flow

interface DashboardFeatureComponent : MainPagerPageComponent {

    val uiState: Flow<UiState>

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
        val eventsData: DashboardEventsData = DashboardEventsData(),
        val profitSummaryData: DashboardProfitSummaryData = DashboardProfitSummaryData(),
        val recentVenues: List<Venue> = emptyList(),
        val recentExpenses: List<Expense> = emptyList(),
    )
}
