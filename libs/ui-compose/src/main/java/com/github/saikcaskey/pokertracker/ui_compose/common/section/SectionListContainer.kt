package com.github.saikcaskey.pokertracker.ui_compose.common.section

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> SectionListContainer(
    items: List<T>,
    onItemClicked: (T) -> Unit,
    maxHeight: Dp = 275.dp,
    emptyMessage: String = "Nothing here yet!",
    adjustHeight: Boolean = true,
    limit: Int? = 4,
    listItemContent: @Composable ColumnScope.(T) -> Unit,
) {
    val height = if (adjustHeight) {
        if (items.size > 2) {
            maxHeight
        } else if (items.size == 2) {
            maxHeight / 2
        } else maxHeight / 3
    } else {
        maxHeight
    }
    if (items.isNotEmpty()) {
        LazyColumn(Modifier.height(height)) {
            items(if (limit != null) items.take(limit) else items) { item ->
                SectionListItem(onItemClick = { onItemClicked(item) }) {
                    listItemContent(item)
                }
            }
        }
    } else {
        Text(text = emptyMessage, style = MaterialTheme.typography.labelLarge)
    }
}
