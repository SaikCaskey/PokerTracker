package com.github.saikcaskey.pokertracker.dashboard.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.dashboard.presentation.navigation.DashboardNavigator
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DashboardFeatureComponentImpl(
    componentContext: ComponentContext,
    eventRepository: EventRepository,
    expenseRepository: ExpenseRepository,
    venueRepository: VenueRepository,
    dispatchers: CoroutineDispatchers,
    private val navigator: DashboardNavigator,
) : DashboardFeatureComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val dashboardEventsData = combine(
        eventRepository.getRecent(),
        eventRepository.getToday(),
        eventRepository.getUpcoming(),
        ::DashboardEventsData
    ).stateIn(coroutineScope, SharingStarted.Companion.Eagerly, DashboardEventsData())

    private val dashboardProfitSummary = combine(
        expenseRepository.getBalanceNow(),
        expenseRepository.getBalanceForMonth(),
        expenseRepository.getBalanceForYear(),
        expenseRepository.getUpcomingCosts(),
        ::DashboardProfitSummaryData
    ).stateIn(coroutineScope, SharingStarted.Companion.Eagerly, DashboardProfitSummaryData())

    override val uiState = combine(
        dashboardEventsData,
        dashboardProfitSummary,
        venueRepository.getRecent(),
        expenseRepository.getRecent(),
        DashboardFeatureComponent::UiState
    ).stateIn(coroutineScope, SharingStarted.Companion.Eagerly, DashboardFeatureComponent.UiState())

    override fun onShowEventDetailClicked(id: Long) = navigator.onShowEventDetail(id)
    override fun onShowExpenseDetailClicked(id: Long) = navigator.onShowExpenseDetail(id)
    override fun onShowVenueDetailClicked(id: Long) = navigator.onShowVenueDetail(id)
    override fun onShowInsertEventClicked() = navigator.onShowInsertEvent()
    override fun onShowInsertExpenseClicked() = navigator.onShowInsertExpense()
    override fun onShowInsertVenueClicked() = navigator.onShowInsertVenue()
    override fun onShowAllExpensesClicked() = navigator.onShowAllExpenses()
    override fun onShowAllEventsClicked() = navigator.onShowAllEvents()
    override fun onShowAllVenuesClicked() = navigator.onShowAllVenues()
}

