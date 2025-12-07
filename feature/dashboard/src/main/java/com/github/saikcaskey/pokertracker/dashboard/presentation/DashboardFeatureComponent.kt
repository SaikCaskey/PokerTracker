package com.github.saikcaskey.pokertracker.dashboard.presentation

import com.github.saikcaskey.pokertracker.libs.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.libs.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.libs.domain.models.Expense
import com.github.saikcaskey.pokertracker.libs.domain.models.Venue
import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.FeatureComponent
import kotlinx.coroutines.flow.Flow

interface DashboardFeatureComponent : FeatureComponent {

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
    fun onShowAccountClicked()
    fun onShowStatsClicked()
    fun onShowPlannerClicked()

    data class UiState(
        val eventsData: DashboardEventsData = DashboardEventsData(),
        val profitSummaryData: DashboardProfitSummaryData = DashboardProfitSummaryData(),
        val recentVenues: List<Venue> = emptyList(),
        val recentExpenses: List<Expense> = emptyList(),
    )
}
