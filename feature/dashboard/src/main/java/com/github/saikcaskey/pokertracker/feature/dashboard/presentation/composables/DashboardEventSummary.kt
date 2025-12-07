package com.github.saikcaskey.pokertracker.feature.dashboard.presentation.composables

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.libs.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventsList
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarAlt

@Composable
fun DashboardEventSummary(
    state: DashboardFeatureComponent.UiState,
    onShowAllEventsClicked: () -> Unit,
    onShowPlannerClicked: () -> Unit,
    onShowEventDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
        onClick = onShowAllEventsClicked,
        action = {
            IconButton(onClick = onShowPlannerClicked) {
                FontAwesomeIcons.Solid.CalendarAlt.AsIcon(24.dp, "Show All Events",)
            }
        },
    ) {
        val eventsData = state.eventsData
        if (eventsData.isEmpty) {
            Text(
                text = "Add some Events to see data here.",
                style = MaterialTheme.typography.labelLarge
            )
        } else {
            if (eventsData.upcomingEvents.isNotEmpty()) {
                Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
                EventsList(
                    items = eventsData.upcomingEvents,
                    emptyMessage = "No Upcoming events",
                    onEventClicked = onShowEventDetailClicked,
                )
            }
            if (eventsData.todayEvents.isNotEmpty()) {
                Text(text = "Today", style = MaterialTheme.typography.labelLarge)
                EventsList(
                    items = eventsData.todayEvents,
                    emptyMessage = "No more events today",
                    onEventClicked = onShowEventDetailClicked,
                )
            }
            if (eventsData.recentEvents.isNotEmpty()) {
                Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
                EventsList(
                    items = eventsData.recentEvents,
                    emptyMessage = "No Recent events",
                    onEventClicked = onShowEventDetailClicked,
                )
            }
        }
    }
}
