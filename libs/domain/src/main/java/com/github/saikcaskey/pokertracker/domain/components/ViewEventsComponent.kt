package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Event
import kotlinx.coroutines.flow.StateFlow

interface ViewEventsComponent {
    val uiState: StateFlow<UiState>

    fun onShowEventDetailClicked(eventId: Long)
    fun onSearchQueryChanged(query: String?)
    fun onFilterOptionChanged(sortOption: EventSortOption)
    fun onBackClicked()
    fun onShowInsertEventClicked()
    fun onDeleteEventClicked(id: Long)
    fun onDeleteAllEventsClicked()

    data class UiState(
        val events: List<Event> = emptyList(),
        val filtered: List<Event> = emptyList(),
        val searchFilter: EventSearchFilter = EventSearchFilter(),
    )

    enum class EventSortOption {
        DATE_ASC, DATE_DESC,
        NAME_ASC, NAME_DESC,
        ID_ASC, ID_DESC,
        CREATED_AT_ASC, CREATED_AT_DESC,
        UPDATED_AT_ASC, UPDATED_AT_DESC,
    }

    data class EventSearchFilter(
        val query: String? = null,
        val sort: EventSortOption = EventSortOption.DATE_DESC,
    )
}
