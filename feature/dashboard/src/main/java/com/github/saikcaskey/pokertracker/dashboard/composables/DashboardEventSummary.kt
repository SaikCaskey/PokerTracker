package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.DashboardEventsData
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventsList

@Composable
fun DashboardEventSummary(
    data: DashboardEventsData,
    onShowAllEventsClicked: () -> Unit,
    onShowInsertEventClicked: () -> Unit,
    onShowEventDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
        onAddClick = onShowInsertEventClicked,
        onShowAllClick = onShowAllEventsClicked,
    ) {
        if (data.upcomingEvents.isNotEmpty()) {
            Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
            EventsList(
                items = data.upcomingEvents,
                emptyMessage = "No Upcoming events",
                onEventClicked = onShowEventDetailClicked,
            )
        }
        if (data.todayEvents.isNotEmpty()) {
            Text(text = "Today", style = MaterialTheme.typography.labelLarge)
            EventsList(
                items = data.todayEvents,
                emptyMessage = "No more events today",
                onEventClicked = onShowEventDetailClicked,
            )
        }
        if (data.recentEvents.isNotEmpty()) {
            Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
            EventsList(
                items = data.recentEvents,
                emptyMessage = "No Recent events",
                onEventClicked = onShowEventDetailClicked,
            )
        }
    }
}
