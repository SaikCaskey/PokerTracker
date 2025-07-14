package com.github.saikcaskey.pokertracker.presentation.event

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.ViewEventsComponent
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.components.ViewEventsComponent.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

class DefaultViewEventsComponent(
    componentContext: ComponentContext,
    dispatchers: CoroutineDispatchers,
    private val onShowInsertEvent: () -> Unit,
    private val onShowEventDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    private val eventRepository: EventRepository,
) : ViewEventsComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val _searchQuery = MutableStateFlow<String?>(null)

    private val _eventSortOption = MutableStateFlow(EventSortOption.DATE_DESC)

    private val _searchOptions = combine(_searchQuery, _eventSortOption, ::EventSearchFilter)
        .stateIn(coroutineScope, Eagerly, EventSearchFilter())

    override val uiState: StateFlow<UiState> =
        combine(eventRepository.getAll(), _searchOptions) { events, searchFilter ->
            val filtered = events
                .filter { event ->
                    val query = searchFilter.query.orEmpty()
                    query.isBlank()
                            || event.name?.contains(query, ignoreCase = true) == true
                            || event.gameType.name.contains(query, ignoreCase = true)
                            || event.description?.contains(query, ignoreCase = true) == true
                            || event.id == query.toLongOrNull()
                            || event.venueId == query.toLongOrNull()
                }
                .sortedWith(
                    when (searchFilter.sort) {
                        EventSortOption.DATE_ASC -> compareBy(Event::date)
                        EventSortOption.DATE_DESC -> compareByDescending(Event::date)
                        EventSortOption.NAME_ASC -> compareBy(Event::name)
                        EventSortOption.NAME_DESC -> compareByDescending(Event::name)
                        EventSortOption.ID_ASC -> compareBy(Event::id)
                        EventSortOption.ID_DESC -> compareByDescending(Event::id)
                        EventSortOption.CREATED_AT_ASC -> compareBy(Event::createdAt)
                        EventSortOption.CREATED_AT_DESC -> compareByDescending(Event::createdAt)
                        EventSortOption.UPDATED_AT_ASC -> compareBy(Event::updatedAt)
                        EventSortOption.UPDATED_AT_DESC -> compareByDescending(Event::updatedAt)
                    }
                )
            UiState(events, filtered, searchFilter)
        }.stateIn(coroutineScope, Eagerly, UiState())

    override fun onBackClicked() = onFinished()

    override fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = query
    }

    override fun onFilterOptionChanged(sortOption: EventSortOption) {
        _eventSortOption.value = sortOption
    }

    override fun onShowEventDetailClicked(eventId: Long) = onShowEventDetail(eventId)

    override fun onShowInsertEventClicked() = onShowInsertEvent()

    override fun onDeleteEventClicked(id: Long) {
        coroutineScope.launch {
            runCatching { eventRepository.deleteById(id) }
                .onFailure(Throwable::printStackTrace)
        }
    }

    override fun onDeleteAllEventsClicked() {
        coroutineScope.launch {
            runCatching { eventRepository.deleteAll() }
                .onFailure(Throwable::printStackTrace)
        }
    }
}