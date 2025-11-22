package com.github.saikcaskey.pokertracker.stats.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputFormScaffold
import com.github.saikcaskey.pokertracker.stats.domain.components.InsertVenueComponent

@Composable
fun InsertVenueContent(component: InsertVenueComponent) {
    val state by component.uiState.collectAsState()

    val localNameState = remember(state.inputData.name) {
        mutableStateOf(state.inputData.name)
    }
    val localAddressState = remember(state.inputData.address) {
        mutableStateOf(state.inputData.address)
    }
    val localDescriptionState = remember(state.inputData.description) {
        mutableStateOf(state.inputData.description)
    }

    InputFormScaffold(
        title = if (state.existingVenueId == null) "Insert Venue" else "Edit Venue",
        onBackClicked = component::onBackClicked,
        onSubmit = component::onSubmitClicked,
        isSubmitEnabled = state.isSubmitEnabled
    ) {
        OutlinedTextField(
            value = localNameState.value,
            onValueChange = { newValue ->
                localNameState.value = newValue
                component.onNameChanged(newValue)
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = localAddressState.value,
            onValueChange = { newValue ->
                localAddressState.value = newValue
                component.onAddressChanged(newValue)
            },
            label = { Text("Address") },
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
    }
}
