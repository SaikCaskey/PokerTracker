package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.util.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionListContainer

@Composable
fun DashboardEventsList(
    items: List<Event>,
    limit: Int? = 4,
    emptyMessage: String,
    onEventClicked: (Long) -> Unit,
) {
    SectionListContainer(
        items = items,
        adjustHeight = true,
        limit = limit,
        onItemClicked = { onEventClicked(it.id) },
        emptyMessage = emptyMessage
    ) { event ->
        Text(
            text = event.name?.take(50).orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = event.description?.take(50).orEmpty(),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "At: ${event.date?.toUiDateTimeOrNull().orEmpty()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
