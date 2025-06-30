package com.github.saikcaskey.pokertracker.presentation.event

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.ProfitSummary
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.shared.domain.repository.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultEventDetailComponent(
    private val componentContext: ComponentContext,
    private val eventId: Long,
    private val dispatchers: CoroutineDispatchers,
    private val onShowExpenseDetail: (expenseId: Long) -> Unit,
    private val onShowInsertExpense: (eventId: Long, venueId: Long?) -> Unit,
    private val onShowEditEvent: (existingEventId: Long?) -> Unit,
    private val onShowVenueDetail: (venueId: Long) -> Unit,
    private val onFinished: () -> Unit,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val venueRepository: VenueRepository,
) : EventDetailComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val eventDetail = eventRepository.getById(eventId)
        .stateIn(coroutineScope, Eagerly, null)

    private val profitSummary = eventDetail.flatMapLatest {
        combine(
            expenseRepository.getEventCashesSubtotal(eventId),
            expenseRepository.getEventCostSubtotal(eventId),
            expenseRepository.getEventBalance(eventId),
            ::ProfitSummary
        )
    }.stateIn(coroutineScope, Eagerly, ProfitSummary())

    private val eventExpenses = eventDetail.flatMapLatest { event ->
        event?.id?.let(expenseRepository::getByEvent) ?: emptyFlow()
    }.stateIn(coroutineScope, Eagerly, emptyList())

    private val eventVenue = eventDetail.flatMapLatest { event ->
        event?.venueId?.let(venueRepository::getById) ?: emptyFlow()
    }.stateIn(coroutineScope, Eagerly, null)

    override val uiState: StateFlow<EventDetailComponent.UiState> = combine(
        eventDetail,
        eventVenue,
        eventExpenses,
        profitSummary,
    ) { event, venue, expenses, profitSummary ->
        EventDetailComponent.UiState(
            id = eventId,
            event = event,
            venue = venue,
            expenses = expenses,
            profitSummary = profitSummary
        )
    }.stateIn(coroutineScope, Eagerly, EventDetailComponent.UiState(id = eventId, profitSummary = profitSummary.value))

    override fun onShowExpenseDetailClicked(expenseId: Long) = onShowExpenseDetail(expenseId)
    override fun onShowVenueDetailClicked(venueId: Long) = onShowVenueDetail(venueId)
    override fun onShowInsertExpenseClicked() = onShowInsertExpense(eventId, uiState.value.venue?.id)
    override fun onBackClicked() = onFinished()
    override fun onShowEditEventClicked() = onShowEditEvent(eventId)
    override fun onDeleteEventClicked() {
        coroutineScope.launch {
            runCatching { eventRepository.deleteById(eventId) }
                .onSuccess { withContext(dispatchers.main) { onBackClicked() } }
                .onFailure(Throwable::printStackTrace)
        }
    }
}
