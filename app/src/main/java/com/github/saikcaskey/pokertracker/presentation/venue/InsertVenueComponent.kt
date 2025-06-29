package com.saikcaskey.github.pokertracker.shared.presentation.venue

import kotlinx.coroutines.flow.StateFlow

interface InsertVenueComponent {

    val uiState: StateFlow<UiState>

    fun onSubmitClicked()

    fun onBackClicked()
    fun onNameChanged(name: String)
    fun onAddressChanged(address: String)
    fun onDescriptionChanged(description: String)

    data class UiState(
        val inputData: InputData = InputData(),
        val existingVenueId: Long? = null,
        val isSubmitEnabled: Boolean = false,
    )

    data class InputData(
        val name: String = "",
        val address: String = "",
        val description: String = "",
    )
}
