package com.github.saikcaskey.pokertracker.dashboard

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.domain.components.DashboardFeatureComponent.UiState
import com.github.saikcaskey.pokertracker.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class DashboardFeatureComponentImpl(
    componentContext: ComponentContext,
    eventRepository: EventRepository,
    expenseRepository: ExpenseRepository,
    venueRepository: VenueRepository,
    dispatchers: CoroutineDispatchers,
    private val onShowEventDetail: (Long) -> Unit,
    private val onShowExpenseDetail: (Long) -> Unit,
    private val onShowInsertExpense: () -> Unit,
    private val onShowInsertVenue: () -> Unit,
    private val onShowInsertEvent: (LocalDate?) -> Unit,
    private val onShowVenueDetail: (Long) -> Unit,
    private val onShowAllVenues: () -> Unit,
    private val onShowAllEvents: () -> Unit,
    private val onShowAllExpenses: () -> Unit,
) : DashboardFeatureComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val dashboardEventsData = combine(
        eventRepository.getRecent(),
        eventRepository.getToday(),
        eventRepository.getUpcoming(),
        ::DashboardEventsData
    ).stateIn(coroutineScope, Eagerly, DashboardEventsData())

    private val dashboardProfitSummary = combine(
        expenseRepository.getBalanceAllTime(),
        expenseRepository.getBalanceForMonth(),
        expenseRepository.getBalanceForYear(),
        expenseRepository.getUpcomingCosts(),
        ::DashboardProfitSummaryData
    ).stateIn(coroutineScope, Eagerly, DashboardProfitSummaryData())

    override val uiState = combine(
        dashboardEventsData,
        dashboardProfitSummary,
        venueRepository.getRecent(),
        expenseRepository.getRecent(),
        ::UiState
    ).stateIn(coroutineScope, Eagerly, UiState())

    override fun onShowEventDetailClicked(id: Long) = onShowEventDetail(id)
    override fun onShowExpenseDetailClicked(id: Long) = onShowExpenseDetail(id)
    override fun onShowInsertEventClicked() = onShowInsertEvent(null)
    override fun onShowInsertVenueClicked() = onShowInsertVenue()
    override fun onShowInsertExpenseClicked() = onShowInsertExpense()
    override fun onShowVenueDetailClicked(id: Long) = onShowVenueDetail(id)
    override fun onShowAllExpensesClicked() = onShowAllExpenses()
    override fun onShowAllEventsClicked() = onShowAllEvents()
    override fun onShowAllVenuesClicked() = onShowAllVenues()
}
