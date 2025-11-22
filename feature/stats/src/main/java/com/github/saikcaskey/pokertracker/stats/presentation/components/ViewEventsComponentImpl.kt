package com.github.saikcaskey.pokertracker.stats.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.stats.domain.components.ViewEventsComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

class ViewEventsComponentImpl(
    componentContext: ComponentContext,
    dispatchers: CoroutineDispatchers,
    private val onShowInsertEvent: () -> Unit,
    private val onShowEventDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    private val eventRepository: EventRepository,
) : ViewEventsComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val _searchQuery = MutableStateFlow<String?>(null)

    private val _eventSortOption = MutableStateFlow(ViewEventsComponent.EventSortOption.DATE_DESC)

    private val _searchOptions = combine(_searchQuery, _eventSortOption,
        ViewEventsComponent::EventSearchFilter
    )
        .stateIn(coroutineScope, Eagerly, ViewEventsComponent.EventSearchFilter())

    override val uiState: StateFlow<ViewEventsComponent.UiState> =
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
                        ViewEventsComponent.EventSortOption.DATE_ASC -> compareBy(Event::date)
                        ViewEventsComponent.EventSortOption.DATE_DESC -> compareByDescending(Event::date)
                        ViewEventsComponent.EventSortOption.NAME_ASC -> compareBy(Event::name)
                        ViewEventsComponent.EventSortOption.NAME_DESC -> compareByDescending(Event::name)
                        ViewEventsComponent.EventSortOption.ID_ASC -> compareBy(Event::id)
                        ViewEventsComponent.EventSortOption.ID_DESC -> compareByDescending(Event::id)
                        ViewEventsComponent.EventSortOption.CREATED_AT_ASC -> compareBy(Event::createdAt)
                        ViewEventsComponent.EventSortOption.CREATED_AT_DESC -> compareByDescending(Event::createdAt)
                        ViewEventsComponent.EventSortOption.UPDATED_AT_ASC -> compareBy(Event::updatedAt)
                        ViewEventsComponent.EventSortOption.UPDATED_AT_DESC -> compareByDescending(Event::updatedAt)
                    }
                )
            ViewEventsComponent.UiState(events, filtered, searchFilter)
        }.stateIn(coroutineScope, Eagerly, ViewEventsComponent.UiState())

    override fun onBackClicked() = onFinished()

    override fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = query
    }

    override fun onFilterOptionChanged(sortOption: ViewEventsComponent.EventSortOption) {
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
