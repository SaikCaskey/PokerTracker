package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

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
        DashboardVenueList(
            items = venues,
            onVenueClicked = onShowVenueDetailClicked,
        )
    }
}
