package com.github.saikcaskey.pokertracker.feature.stats.components

import com.github.saikcaskey.pokertracker.libs.domain.models.Event
import com.github.saikcaskey.pokertracker.libs.domain.models.Expense
import com.github.saikcaskey.pokertracker.libs.domain.models.ProfitSummary
import com.github.saikcaskey.pokertracker.libs.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface EventDetailComponent {
    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onDeleteEventClicked()
    fun onShowEditEventClicked()
    fun onShowInsertExpenseClicked()
    fun onShowExpenseDetailClicked(expenseId: Long)
    fun onShowVenueDetailClicked(venueId: Long)

    data class UiState(
        val id: Long? = null,
        val event: Event? = null,
        val venue: Venue? = null,
        val expenses: List<Expense> = emptyList(),
        val profitSummary: ProfitSummary = ProfitSummary(),
    )
}
