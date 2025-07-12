package com.github.saikcaskey.pokertracker.ui_compose.components.venue

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.extensions.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionListContainer

@Composable
fun VenueList(items: List<Venue>, onVenueClicked: (Long) -> Unit) {
    SectionListContainer(
        items = items,
        onItemClicked = { onVenueClicked(it.id) },
    ) { venue ->
        Text(
            text = venue.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = venue.description?.take(50).orEmpty(),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Created:  ${venue.createdAt?.toUiDateTimeOrNull()}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
