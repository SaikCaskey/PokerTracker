package com.github.saikcaskey.stats.presentation

import com.github.saikcaskey.pokertracker.domain.models.EventSummary
import com.github.saikcaskey.pokertracker.domain.models.ExpenseSummary
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
        val eventSummary: EventSummary = EventSummary(),
        val expenseSummary: ExpenseSummary = ExpenseSummary(),
        val profitSummary: ProfitSummary = ProfitSummary(),
    )
}
