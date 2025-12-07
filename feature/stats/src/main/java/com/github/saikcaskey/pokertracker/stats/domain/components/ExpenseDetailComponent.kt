package com.github.saikcaskey.pokertracker.stats.domain.components

import com.github.saikcaskey.pokertracker.libs.domain.models.Event
import com.github.saikcaskey.pokertracker.libs.domain.models.Expense
import com.github.saikcaskey.pokertracker.libs.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface ExpenseDetailComponent {
    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onShowVenueDetailClicked(venueId: Long)
    fun onShowEventDetailClicked(eventId: Long)
    fun onShowEditExpenseClicked()
    fun onDeleteExpenseClicked()

    data class UiState(
        val expenseId: Long? = null,
        val expense: Expense? = null,
        val event: Event? = null,
        val venue: Venue? = null,
    )
}
