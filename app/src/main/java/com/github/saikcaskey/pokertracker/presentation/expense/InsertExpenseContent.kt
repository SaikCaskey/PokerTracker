package com.github.saikcaskey.pokertracker.presentation.expense

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.github.saikcaskey.pokertracker.domain.components.InsertExpenseComponent
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDateField
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownEvent
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownField
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownVenue
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputFormScaffold
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputTimeField

@Composable
fun InsertExpenseContent(component: InsertExpenseComponent) {
    val state by component.uiState.collectAsState()

    InputFormScaffold(
        title = if (state.existingExpenseId == null) "Insert Expense" else "Edit Expense",
        onBackClicked = component::onBackClicked,
        onSubmit = component::onSubmitClicked,
        isSubmitEnabled = state.isSubmitEnabled
    ) {
        InputDropdownVenue(
            venues = state.venues,
            selectedVenue = state.venue,
            onVenueSelected = component::onVenueChanged,
            onAddVenueClicked = component::onShowInsertVenueClicked
        )
        InputDropdownEvent(
            events = state.events,
            selectedEvent = state.event,
            onEventSelected = component::onEventChanged,
            onAddEventClicked = component::onShowInsertEventClicked
        )
        OutlinedTextField(
            value = state.inputData.amount?.toString().orEmpty(),
            label = { Text("Amount") },
            onValueChange = component::onAmountChanged,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        InputDropdownField(
            value = state.inputData.type,
            label = "Expense Type",
            options = ExpenseType.entries.toList(),
            onSelected = component::onTypeChanged,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.inputData.description,
            label = { Text("Description") },
            onValueChange = component::onDescriptionChanged,
            modifier = Modifier.fillMaxWidth()
        )
        InputDateField(
            value = state.inputData.date,
            label = "Date",
            onValueChange = component::onDateChanged,
            modifier = Modifier.fillMaxWidth(),
        )
        InputTimeField(
            value = state.inputData.time,
            label = "Time",
            onValueChange = component::onTimeChanged,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
