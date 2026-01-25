package com.github.saikcaskey.pokertracker.libs.ui_compose.common.inputform

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.libs.domain.models.Event

@Composable
fun InputDropdownEvent(
    events: List<Event>,
    selectedEvent: Event?,
    onEventSelected: (Event) -> Unit,
    onAddEventClicked: () -> Unit,
) {
    InputSearchableDropdownField(
        label = "Event",
        items = events,
        selectedItem = selectedEvent,
        onItemSelected = onEventSelected,
        filterItems = false,
        itemToString = { it.name ?: it.id.toString() },
        onAddNewItemClicked = onAddEventClicked
    )
}
