package com.github.saikcaskey.stats.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.GameType
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.util.atTimeInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class InsertEventComponentImpl(
    private val componentContext: ComponentContext,
    existingEventId: Long? = null,
    venueId: Long? = null,
    startDate: LocalDate? = null,
    private val eventRepository: EventRepository,
    venueRepository: VenueRepository,
    private val dispatchers: CoroutineDispatchers,
    private val onShowInsertVenue: () -> Unit,
    private val onFinished: () -> Unit,
) : InsertEventComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val _selectedVenueId = MutableStateFlow(venueId)

    private val _inputData = MutableStateFlow(InsertEventComponent.InputData())

    private val _venues: StateFlow<List<Venue>> = venueRepository.getAll()
        .stateIn(coroutineScope, Eagerly, emptyList())

    private val _venue: StateFlow<Venue?> = _selectedVenueId
        .flatMapLatest { venueId ->
            if (venueId == null) {
                emptyFlow()
            } else {
                venueRepository.getById(venueId)
            }
        }
        .stateIn(coroutineScope, Eagerly, null)

    override val uiState: StateFlow<InsertEventComponent.UiState> = combine(
        _inputData,
        _venues,
        _venue,
    ) { inputData, venues, venue ->
        InsertEventComponent.UiState(
            existingEventId = existingEventId,
            inputData = inputData,
            venues = venues,
            venue = venue,
            isSubmitEnabled = inputData.name.isNotBlank(),
        )
    }.stateIn(
        scope = coroutineScope,
        started = Eagerly,
        initialValue = InsertEventComponent.UiState(
            existingEventId = existingEventId,
            inputData = _inputData.value,
            venue = _venue.value,
        )
    )

    init {
        coroutineScope.launch {
            existingEventId?.let {
                eventRepository.getById(existingEventId).collectLatest { event ->
                    _selectedVenueId.value = event?.venueId
                    _inputData.update {
                        val storedEventDateTime = event?.date?.asLocalDateTime()
                        InsertEventComponent.InputData(
                            name = event?.name.orEmpty(),
                            date = storedEventDateTime?.date ?: it.date,
                            time = storedEventDateTime?.time ?: it.time,
                            type = event?.gameType ?: GameType.CASH,
                            description = event?.description.orEmpty(),
                        )
                    }
                }
            }
        }
    }

    override fun onNameChanged(name: String) {
        _inputData.update { it.copy(name = name) }
    }

    override fun onDateChanged(date: LocalDate?) {
        _inputData.update { it.copy(date = date) }
    }

    override fun onTimeChanged(time: LocalTime?) {
        _inputData.update { it.copy(time = time) }
    }

    override fun onDescriptionChanged(description: String) {
        _inputData.update { it.copy(description = description) }
    }

    override fun onGameTypeChanged(type: GameType) {
        _inputData.update { it.copy(type = type) }
    }

    override fun onVenueChanged(venue: Venue) {
        _selectedVenueId.value = venue.id
    }

    override fun onSubmitClicked() {
        val uiState = uiState.value
        coroutineScope.launch {
            runCatching {
                val existingEventId = uiState.existingEventId
                val inputEventTime = uiState.inputData.time ?: nowAsLocalDateTime().time
                val inputEventDateTime = uiState.inputData.date?.atTimeInstant(inputEventTime)

                if (existingEventId != null) {
                    eventRepository.update(
                        id = existingEventId,
                        venueId = uiState.venue?.id,
                        name = uiState.inputData.name,
                        date = inputEventDateTime?.toString(),
                        gameType = uiState.inputData.type.name,
                        description = uiState.inputData.description,
                    )
                } else {
                    eventRepository.insert(
                        name = uiState.inputData.name,
                        gameType = uiState.inputData.type.name,
                        venueId = uiState.venue?.id,
                        date = inputEventDateTime?.toString(),
                        description = uiState.inputData.description,
                    )
                }
            }
                .onSuccess { withContext(dispatchers.main) { onFinished() } }
                .onFailure(Throwable::printStackTrace)
        }
    }

    override fun onShowInsertVenueClicked() {
        onShowInsertVenue()
    }

    override fun onBackClicked() {
        onFinished()
    }
}
