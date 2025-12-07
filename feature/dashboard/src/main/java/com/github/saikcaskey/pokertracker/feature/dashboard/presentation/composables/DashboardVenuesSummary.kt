package com.github.saikcaskey.pokertracker.feature.dashboard.presentation.composables

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.libs.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.VenueList

@Composable
fun DashboardVenuesSummary(
    state: DashboardFeatureComponent.UiState,
    onShowAllVenuesClicked: () -> Unit,
    onShowVenueDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Venues",
        onClick = onShowAllVenuesClicked,
    ) {
        VenueList(
            items = state.recentVenues,
            onVenueClicked = onShowVenueDetailClicked,
        )
    }
}
