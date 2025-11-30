package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventsList

@Composable
fun DashboardEventSummary(
    state: State<DashboardFeatureComponent.UiState>,
    onShowAllEventsClicked: () -> Unit,
    onShowPlannerClicked: () -> Unit,
    onShowInsertEventClicked: () -> Unit,
    onShowEventDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
        action1Label = "Add",
        action2Label = "Show All",
        action3Label = "Planner",
        onAction1Click = onShowInsertEventClicked,
        onAction2Click = onShowAllEventsClicked,
        onAction3Click = onShowPlannerClicked,
    ) {
        val eventsData = state.value.eventsData
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
