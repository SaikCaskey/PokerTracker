package com.github.saikcaskey.pokertracker.ui_compose.common.inputform

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Venue

@Composable
fun InputDropdownVenue(
    venues: List<Venue>,
    selectedVenue: Venue?,
    onVenueSelected: (Venue) -> Unit,
    onAddVenueClicked: () -> Unit,
) {
    InputSearchableDropdownField(
        label = "Venue",
        items = venues,
        selectedItem = selectedVenue,
        onItemSelected = onVenueSelected,
        itemToString = Venue::name,
        filterItems = false,
        onAddNewItemClicked = onAddVenueClicked
    )
}