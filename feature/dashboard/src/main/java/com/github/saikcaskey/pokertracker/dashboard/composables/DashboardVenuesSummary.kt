package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.VenueList

@Composable
fun DashboardVenuesSummary(
    venues: List<Venue>,
    onShowAllVenuesClicked: () -> Unit,
    onShowInsertVenueClicked: () -> Unit,
    onShowVenueDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Venues",
        onAddClick = onShowInsertVenueClicked,
        onShowAllClick = onShowAllVenuesClicked,
    ) {
        VenueList(
            items = venues,
            onVenueClicked = onShowVenueDetailClicked,
        )
    }
}
