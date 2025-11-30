package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.VenueList

@Composable
fun DashboardVenuesSummary(
    state: State<DashboardFeatureComponent.UiState>,
    onShowAllVenuesClicked: () -> Unit,
    onShowInsertVenueClicked: () -> Unit,
    onShowVenueDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Venues",
        action1Label = "Add",
        action2Label = "Show All",
        onAction1Click = onShowInsertVenueClicked,
        onAction2Click = onShowAllVenuesClicked,
    ) {
        VenueList(
            items = state.value.recentVenues,
            onVenueClicked = onShowVenueDetailClicked,
        )
    }
}
