package com.github.saikcaskey.pokertracker.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.shared.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.components.MainPagerPageComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class MainPagerPageDashboardComponent(
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
) : MainPagerPageComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    val recentEvents = eventRepository.getRecent()
        .stateIn(coroutineScope, Eagerly, emptyList())
    val upcomingEvents = eventRepository.getUpcoming()
        .stateIn(coroutineScope, Eagerly, emptyList())
    val recentExpenses = expenseRepository.getRecent()
        .stateIn(coroutineScope, Eagerly, emptyList())
    val recentVenues = venueRepository.getRecent()
        .stateIn(coroutineScope, Eagerly, emptyList())
    val balance = expenseRepository.getBalanceAllTime()
        .stateIn(coroutineScope, Eagerly, 0.0)
    val upcomingCosts = expenseRepository.getUpcomingCosts()
        .stateIn(coroutineScope, Eagerly, 0.0)
    val balanceForYear = expenseRepository.getBalanceForYear()
        .stateIn(coroutineScope, Eagerly, 0.0)
    val balanceForMonth = expenseRepository.getBalanceForMonth()
        .stateIn(coroutineScope, Eagerly, 0.0)

    fun onShowEventDetailClicked(id: Long) = onShowEventDetail(id)
    fun onShowExpenseDetailClicked(id: Long) = onShowExpenseDetail(id)
    fun onShowInsertEventClicked() = onShowInsertEvent(null)
    fun onShowInsertVenueClicked() = onShowInsertVenue()
    fun onShowInsertExpenseClicked() = onShowInsertExpense()
    fun onShowVenueDetailClicked(id: Long) = onShowVenueDetail(id)
    fun onShowAllExpensesClicked() = onShowAllExpenses()
    fun onShowAllEventsClicked() = onShowAllEvents()
    fun onShowAllVenuesClicked() = onShowAllVenues()
}
