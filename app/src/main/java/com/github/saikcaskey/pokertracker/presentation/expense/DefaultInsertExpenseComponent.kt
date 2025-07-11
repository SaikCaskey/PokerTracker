package com.github.saikcaskey.pokertracker.presentation.expense

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.util.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.util.atTimeInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.InsertExpenseComponent
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class DefaultInsertExpenseComponent(
    private val componentContext: ComponentContext,
    private val existingExpenseId: Long? = null,
    private val eventId: Long?,
    private val venueId: Long?,
    private val expenseRepository: ExpenseRepository,
    private val eventRepository: EventRepository,
    private val venueRepository: VenueRepository,
    private val onShowInsertVenue: () -> Unit,
    private val onShowInsertEvent: (Long?) -> Unit,
    private val onFinished: () -> Unit,
    private val dispatchers: CoroutineDispatchers,
) : InsertExpenseComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val _inputData = MutableStateFlow(InsertExpenseComponent.InputData())
    private val _events = eventRepository.getAll()
    private val _venues = venueRepository.getAll()
    private val _selectedVenueId = MutableStateFlow(venueId)
    private val _selectedEventId = MutableStateFlow(eventId)

    private val _selectedVenue = _selectedVenueId.flatMapLatest { id ->
        (id?.let(venueRepository::getById) ?: emptyFlow())
    }.stateIn(coroutineScope, Eagerly, null)

    private val _selectedEvent = _selectedEventId.flatMapLatest { id ->
        (id?.let(eventRepository::getById) ?: emptyFlow())
    }.stateIn(coroutineScope, Eagerly, null)

    override val uiState: StateFlow<InsertExpenseComponent.UiState> = combine(
        _inputData,
        _selectedVenue,
        _selectedEvent,
        _venues,
        _events
    ) { inputData, venue, event, venues, events ->
        InsertExpenseComponent.UiState(
            existingExpenseId = existingExpenseId,
            inputData = inputData,
            venue = venue,
            event = event,
            venues = venues,
            events = events,
            isSubmitEnabled = inputData.amount != null && inputData.time != null && inputData.date != null
        )
    }.stateIn(
        coroutineScope, Eagerly, InsertExpenseComponent.UiState(
            existingExpenseId = existingExpenseId,
            venue = _selectedVenue.value,
            event = _selectedEvent.value,
        )
    )

    init {
        coroutineScope.launch {
            existingExpenseId?.let {
                expenseRepository.getById(existingExpenseId).collect { expense ->
                    expense.venueId?.let { _selectedVenueId.value = it }
                    expense.eventId?.let { _selectedEventId.value = it }
                    _inputData.update {
                        InsertExpenseComponent.InputData(
                            amount = expense.amount,
                            date = expense.date?.asLocalDateTime()?.date,
                            time = expense.date?.asLocalDateTime()?.time,
                            type = expense.type,
                            description = expense.description.orEmpty(),
                        )
                    }
                }
            }
        }
    }

    override fun onDescriptionChanged(newValue: String) {
        _inputData.update { it.copy(description = newValue) }
    }

    override fun onTypeChanged(newValue: ExpenseType) {
        _inputData.update { it.copy(type = newValue) }
    }

    override fun onAmountChanged(newValue: String?) {
        val newAmount = newValue?.toDoubleOrNull()
        _inputData.update { it.copy(amount = newAmount) }
    }

    override fun onEventChanged(newValue: Event) {
        _selectedEventId.value = newValue.id
    }

    override fun onTimeChanged(time: LocalTime?) {
        _inputData.update {
            it.copy(time = time)
        }
    }

    override fun onDateChanged(date: LocalDate?) {
        _inputData.update {
            it.copy(date = date)
        }
    }

    override fun onVenueChanged(newValue: Venue) {
        _selectedVenueId.value = newValue.id
    }

    override fun onShowInsertEventClicked(venueId: Long?) =
        onShowInsertEvent(uiState.value.venue?.id)

    override fun onShowInsertVenueClicked() = onShowInsertVenue()

    override fun onSubmitClicked() {
        val uiState = uiState.value
        val amount = uiState.inputData.amount ?: return
        coroutineScope.launch {
            runCatching {
                val existingExpenseId = uiState.existingExpenseId
                val expenseTime = uiState.inputData.time ?: nowAsLocalDateTime().time
                val expenseDate = uiState.inputData.date?.atTimeInstant(expenseTime)

                if (existingExpenseId != null) {
                    expenseRepository.update(
                        expenseId = existingExpenseId,
                        eventId = uiState.event?.id,
                        venueId = uiState.venue?.id,
                        amount = amount,
                        type = uiState.inputData.type.name,
                        date = expenseDate?.toString(),
                        description = uiState.inputData.description.trim()
                            .takeIf(String::isNotBlank),
                    )
                } else {
                    expenseRepository.insert(
                        eventId = uiState.event?.id,
                        venueId = uiState.venue?.id,
                        amount = amount,
                        type = uiState.inputData.type.name,
                        date = expenseDate.toString(),
                        description = uiState.inputData.description.trim()
                            .takeIf(String::isNotBlank),
                    )
                }
            }
                .onSuccess { withContext(dispatchers.main) { onFinished() } }
                .onFailure(Throwable::printStackTrace)
        }
    }

    override fun onBackClicked() = onFinished()
}
