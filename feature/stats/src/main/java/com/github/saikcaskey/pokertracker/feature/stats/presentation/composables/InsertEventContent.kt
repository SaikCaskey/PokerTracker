package com.github.saikcaskey.pokertracker.feature.stats.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertEventComponent
import com.github.saikcaskey.pokertracker.libs.domain.models.GameType
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDateField
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownField
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownVenue
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputFormScaffold
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputTimeField

@Composable
fun InsertEventContent(component: InsertEventComponent) {

    val state by component.uiState.collectAsState()

    val localNameState = remember(state.inputData.name) {
        mutableStateOf(state.inputData.name)
    }
    val localDescriptionState = remember(state.inputData.description) {
        mutableStateOf(state.inputData.description)
    }

    InputFormScaffold(
        title = if (state.existingEventId == null) "Insert Event" else "Edit Event",
        onBackClicked = component::onBackClicked,
        onSubmit = component::onSubmitClicked,
        isSubmitEnabled = state.isSubmitEnabled
    ) {
        InputDropdownField(
            value = state.inputData.type,
            label = "Event Type",
            modifier = Modifier.fillMaxWidth(),
            options = GameType.entries.toList(),
            onSelected = component::onGameTypeChanged,
        )

        OutlinedTextField(
            value = localNameState.value,
            onValueChange = { newValue ->
                localNameState.value = newValue
                component.onNameChanged(newValue)
            },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = localDescriptionState.value,
            onValueChange = { newValue ->
                localDescriptionState.value = newValue
                component.onDescriptionChanged(newValue)
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        InputDropdownVenue(
            venues = state.venues,
            selectedVenue = state.venue,
            onVenueSelected = component::onVenueChanged,
            onAddVenueClicked = component::onShowInsertVenueClicked
        )

        InputDateField(
            value = state.inputData.date,
            onValueChange = component::onDateChanged,
            label = "Date",
            modifier = Modifier.fillMaxWidth(),
        )

        InputTimeField(
            value = state.inputData.time,
            onValueChange = component::onTimeChanged,
            label = "Time",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
