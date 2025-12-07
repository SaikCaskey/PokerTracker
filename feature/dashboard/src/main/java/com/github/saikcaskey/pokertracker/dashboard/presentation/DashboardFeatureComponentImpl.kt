package com.github.saikcaskey.pokertracker.dashboard.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.libs.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.libs.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.VenueRepository
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
    private val rootNavigator: RootNavigator,
) : DashboardFeatureComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val dashboardEventsData = combine(
        eventRepository.getRecent(),
        eventRepository.getToday(),
        eventRepository.getUpcoming(),
        ::DashboardEventsData
    ).stateIn(coroutineScope, SharingStarted.Companion.Eagerly, DashboardEventsData())

    private val dashboardProfitSummary = combine(
        expenseRepository.getBeforeNow(),
        expenseRepository.getBalanceNow(),
        ::DashboardProfitSummaryData
    ).stateIn(coroutineScope, SharingStarted.Companion.Eagerly, DashboardProfitSummaryData())

    override val uiState = combine(
        dashboardEventsData,
        dashboardProfitSummary,
        venueRepository.getAll(),
        expenseRepository.getTomorrow(),
        DashboardFeatureComponent::UiState
    ).stateIn(coroutineScope, SharingStarted.Companion.Eagerly, DashboardFeatureComponent.UiState())

    override fun onShowEventDetailClicked(id: Long) = rootNavigator.onShowEventDetail(id)
    override fun onShowExpenseDetailClicked(id: Long) = rootNavigator.onShowExpenseDetail(id)
    override fun onShowInsertEventClicked() = rootNavigator.onShowInsertEvent()
    override fun onShowInsertVenueClicked() = rootNavigator.onShowInsertVenue()
    override fun onShowInsertExpenseClicked() = rootNavigator.onShowInsertExpense()
    override fun onShowVenueDetailClicked(id: Long) = rootNavigator.onShowVenueDetail(id)
    override fun onShowAllExpensesClicked() = rootNavigator.onShowAllExpenses()
    override fun onShowAllEventsClicked() = rootNavigator.onShowAllEvents()
    override fun onShowAllVenuesClicked() = rootNavigator.onShowAllVenues()
    override fun onShowAccountClicked() = rootNavigator.onShowAccount()
    override fun onShowStatsClicked() = rootNavigator.onShowStats()
    override fun onShowPlannerClicked() = rootNavigator.onShowPlanner()
}


