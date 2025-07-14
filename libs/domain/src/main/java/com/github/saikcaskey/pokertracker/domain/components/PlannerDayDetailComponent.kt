package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Event
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface PlannerDayDetailComponent {
    val uiState: StateFlow<UiState>

    fun onShowEventDetailClicked(eventId: Long)
    fun onShowInsertEventClicked()
    fun onBackClicked()

    data class UiState(
        val date: LocalDate,
        val events: List<Event> = emptyList(),
    )
}