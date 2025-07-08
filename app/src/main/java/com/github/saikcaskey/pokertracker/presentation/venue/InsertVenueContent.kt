package com.github.saikcaskey.pokertracker.presentation.venue

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.saikcaskey.pokertracker.presentation.common.inputform.InputFormScaffold
import com.github.saikcaskey.pokertracker.domain.components.InsertVenueComponent

@Composable
fun InsertVenueContent(component: InsertVenueComponent) {
    val state by component.uiState.collectAsState()

    InputFormScaffold(
        title = if (state.existingVenueId == null) "Insert Venue" else "Edit Venue",
        onBackClicked = component::onBackClicked,
        onSubmit = component::onSubmitClicked,
        isSubmitEnabled = state.isSubmitEnabled
    ) {
        OutlinedTextField(
            value = state.inputData.name,
            onValueChange = component::onNameChanged,
            label = { Text("name*") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.inputData.address,
            onValueChange = component::onAddressChanged,
            label = { Text("address*") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.inputData.description,
            onValueChange = component::onDescriptionChanged,
            label = { Text("description*") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}