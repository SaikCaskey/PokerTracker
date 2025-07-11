package com.github.saikcaskey.pokertracker.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.MainPagerPageDashboardComponent
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class DefaultMainPagerPageDashboardComponent(
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
) : MainPagerPageDashboardComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val recentEvents = eventRepository.getRecent()
        .stateIn(coroutineScope, Eagerly, emptyList())
    override val todayEvents = eventRepository.getToday()
        .stateIn(coroutineScope, Eagerly, emptyList())
    override val upcomingEvents = eventRepository.getUpcoming()
        .stateIn(coroutineScope, Eagerly, emptyList())
    override val recentExpenses = expenseRepository.getRecent()
        .stateIn(coroutineScope, Eagerly, emptyList())
    override val recentVenues = venueRepository.getRecent()
        .stateIn(coroutineScope, Eagerly, emptyList())
    override val balance = expenseRepository.getBalanceAllTime()
        .stateIn(coroutineScope, Eagerly, 0.0)
    override val upcomingCosts = expenseRepository.getUpcomingCosts()
        .stateIn(coroutineScope, Eagerly, 0.0)
    override val balanceForYear = expenseRepository.getBalanceForYear()
        .stateIn(coroutineScope, Eagerly, 0.0)
    override val balanceForMonth = expenseRepository.getBalanceForMonth()
        .stateIn(coroutineScope, Eagerly, 0.0)

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
