package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.ProfitSummary
import com.github.saikcaskey.pokertracker.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface VenueDetailComponent {

    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onShowInsertEventClicked()
    fun onShowEditVenueClicked()
    fun onDeleteVenueClicked()
    fun onShowEventDetailClicked(eventId: Long)

    data class UiState(
        val id: Long? = null,
        val venue: Venue? = null,
        val pastEvents: List<Event> = emptyList(),
        val upcomingEvents: List<Event> = emptyList(),
        val todayEvents: List<Event> = emptyList(),
        val profitSummary: ProfitSummary = ProfitSummary(),
    )
}
