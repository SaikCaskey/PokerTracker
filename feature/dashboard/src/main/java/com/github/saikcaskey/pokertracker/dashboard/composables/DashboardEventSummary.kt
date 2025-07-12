package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

@Composable
fun DashboardEventSummary(
    recentEvents: List<Event>,
    todayEvents: List<Event>,
    upcomingEvents: List<Event>,
    onShowAllEventsClicked: () -> Unit,
    onShowInsertEventClicked: () -> Unit,
    onShowEventDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
        onAddClick = onShowInsertEventClicked,
        onShowAllClick = onShowAllEventsClicked,
    ) {
        if (upcomingEvents.isNotEmpty()) {
            Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
            DashboardEventsList(
                items = upcomingEvents,
                emptyMessage = "No Upcoming events",
                onEventClicked = onShowEventDetailClicked,
            )
        }
        if (todayEvents.isNotEmpty()) {
            Text(text = "Today", style = MaterialTheme.typography.labelLarge)
            DashboardEventsList(
                items = todayEvents,
                emptyMessage = "No more events today",
                onEventClicked = onShowEventDetailClicked,
            )
        }
        if (recentEvents.isNotEmpty()) {
            Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
            DashboardEventsList(
                items = recentEvents,
                emptyMessage = "No Recent events",
                onEventClicked = onShowEventDetailClicked,
            )
        }
    }
}
