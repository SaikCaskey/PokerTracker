package com.github.saikcaskey.stats.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.EventSummary
import com.github.saikcaskey.pokertracker.domain.models.ExpenseSummary
import com.github.saikcaskey.pokertracker.domain.models.ProfitSummary
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.stats.domain.components.VenueDetailComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VenueDetailComponentImpl(
    private val componentContext: ComponentContext,
    private val venueId: Long,
    private val venueRepository: VenueRepository,
    eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val onShowInsertEvent: () -> Unit,
    private val onShowEventDetail: (Long) -> Unit,
    private val onShowEditVenue: () -> Unit,
    private val onFinished: () -> Unit,
    private val dispatchers: CoroutineDispatchers,
) : VenueDetailComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val venue = venueRepository.getById(venueId)
        .stateIn(coroutineScope, Eagerly, null)

    private val expenseSummary = venue.flatMapLatest { venue ->
        expenseRepository.getByVenue(venueId).map(::ExpenseSummary)
    }.stateIn(coroutineScope, Eagerly, ExpenseSummary())

    private val profitSummary = venue.flatMapLatest { venue ->
        combine(
            expenseRepository.getVenueCashesSubtotal(venueId),
            expenseRepository.getVenueCostSubtotal(venueId),
            expenseRepository.getVenueBalance(venueId),
        ) { cashes, expenses, balance ->
            ProfitSummary(
                cashesSubtotal = cashes,
                costsSubtotal = expenses,
                balance = balance,
            )
        }
    }.stateIn(coroutineScope, Eagerly, ProfitSummary())

    private val eventSummary = venue.flatMapLatest { venue ->
        combine(
            eventRepository.getByVenue(venueId),
            eventRepository.getUpcomingByVenue(venueId),
            eventRepository.getTodayByVenue(venueId),
            ::EventSummary,
        )
    }.stateIn(coroutineScope, Eagerly, EventSummary())

    override val uiState: StateFlow<VenueDetailComponent.UiState> = combine(
        venue,
        eventSummary,
        expenseSummary,
        profitSummary
    ) { venue, eventSummary, expenseSummary, profitSummary ->
        VenueDetailComponent.UiState(
            id = venueId,
            venue = venue,
            eventSummary = eventSummary,
            expenseSummary = expenseSummary,
            profitSummary = profitSummary
        )
    }.stateIn(
        coroutineScope,
        Eagerly,
        VenueDetailComponent.UiState(
            id = venueId,
            profitSummary = profitSummary.value,
            eventSummary = eventSummary.value,
            expenseSummary = expenseSummary.value,
        )
    )

    override fun onBackClicked() = onFinished()
    override fun onShowInsertEventClicked() = onShowInsertEvent()
    override fun onShowEditVenueClicked() = onShowEditVenue()
    override fun onShowEventDetailClicked(eventId: Long) = onShowEventDetail(eventId)
    override fun onDeleteVenueClicked() {
        coroutineScope.launch {
            runCatching { venueRepository.deleteById(venueId) }
                .onSuccess { withContext(dispatchers.main) { onBackClicked() } }
                .onFailure(Throwable::printStackTrace)
        }
    }
}
