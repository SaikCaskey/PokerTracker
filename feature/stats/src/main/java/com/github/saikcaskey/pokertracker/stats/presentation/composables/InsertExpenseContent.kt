package com.github.saikcaskey.pokertracker.stats.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.github.saikcaskey.pokertracker.libs.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDateField
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownEvent
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownField
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownVenue
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputFormScaffold
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputTimeField
import com.github.saikcaskey.pokertracker.stats.domain.components.InsertExpenseComponent

@Composable
fun InsertExpenseContent(component: InsertExpenseComponent) {
    val state by component.uiState.collectAsState()

    val localAmountString = state.inputData.amount?.toString().orEmpty()
    val localAmountState = remember(localAmountString) { mutableStateOf(localAmountString) }

    val localDescriptionState =
        remember(state.inputData.description) { mutableStateOf(state.inputData.description) }

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
            value = localAmountState.value,
            label = { Text("Amount") },
            onValueChange = { newValue ->
                localAmountState.value = newValue
                component.onAmountChanged(newValue)
            },
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
            value = localDescriptionState.value,
            label = { Text("Description") },
            onValueChange = { newValue ->
                localDescriptionState.value = newValue
                component.onDescriptionChanged(newValue)
            },
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
