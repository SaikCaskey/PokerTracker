package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface InsertExpenseComponent {

    val uiState: StateFlow<UiState>

    fun onAmountChanged(newValue: String?)
    fun onTypeChanged(newValue: ExpenseType)
    fun onVenueChanged(newValue: Venue)
    fun onEventChanged(newValue: Event)
    fun onTimeChanged(time: LocalTime?)
    fun onDateChanged(date: LocalDate?)
    fun onDescriptionChanged(newValue: String)
    fun onSubmitClicked()
    fun onShowInsertVenueClicked()
    fun onShowInsertEventClicked(venueId: Long? = null)
    fun onBackClicked()

    data class InputData(
        val amount: Double? = null,
        val type: ExpenseType = ExpenseType.OTHER,
        val date: LocalDate? = null,
        val time: LocalTime? = null,
        val description: String = "",
    )

    data class UiState(
        val existingExpenseId: Long? = null,
        val inputData: InputData = InputData(),
        val venue: Venue? = null,
        val event: Event? = null,
        val venues: List<Venue> = emptyList(),
        val events: List<Event> = emptyList(),
        val isSubmitEnabled: Boolean = true,
    )
}

