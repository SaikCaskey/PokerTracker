@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.saikcaskey.pokertracker.presentation.venue

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.ProfitSummary
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.saikcaskey.github.pokertracker.shared.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.saikcaskey.github.pokertracker.shared.presentation.venue.VenueDetailComponent
import com.saikcaskey.github.pokertracker.shared.presentation.venue.VenueDetailComponent.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultVenueDetailComponent(
    private val componentContext: ComponentContext,
    private val venueId: Long,
    private val venueRepository: VenueRepository,
    eventRepository: EventRepository,
    expenseRepository: ExpenseRepository,
    private val onShowInsertEvent: () -> Unit,
    private val onShowEventDetail: (Long) -> Unit,
    private val onShowEditVenue: () -> Unit,
    private val onFinished: () -> Unit,
    private val dispatchers: CoroutineDispatchers,
) : VenueDetailComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val venue = venueRepository.getById(venueId)
        .stateIn(coroutineScope, Eagerly, null)

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

    override val uiState: StateFlow<UiState> = combine(
        venue,
        eventRepository.getUpcomingByVenue(venueId),
        eventRepository.getByVenue(venueId),
        profitSummary
    ) { venue, upcomingEvents, pastEvents, profitSummary ->
        UiState(
            id = venueId,
            venue = venue,
            upcomingEvents = upcomingEvents,
            pastEvents = pastEvents,
            profitSummary = profitSummary
        )
    }.stateIn(coroutineScope, Eagerly, UiState(id = venueId, profitSummary = profitSummary.value))

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
