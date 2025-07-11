package com.github.saikcaskey.pokertracker.ui_compose.common.section

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*

@Composable
fun SectionContainer(
    modifier: Modifier = Modifier,
    title: String? = null,
    onAddClick: (() -> Unit)? = null,
    onShowAllClick: (() -> Unit)? = null,
    onDeleteAllClick: (() -> Unit)? = null,
    horizontalPadding: Dp = 12.dp,
    verticalPadding: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .padding(vertical = verticalPadding)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            if (!title.isNullOrBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 26.sp)
                )
            }
            Spacer(Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)) {
                onShowAllClick?.let {
                    TextButton(onClick = onShowAllClick) { Text("Show All") }
                }
                onAddClick?.let {
                    TextButton(onClick = onAddClick) { Text("Add") }
                }
                onDeleteAllClick?.let {
                    TextButton(onClick = onDeleteAllClick) { Text("Clear") }
                }
            }
        }
        Card(
            shape = CardDefaults.elevatedShape,
            modifier = modifier.fillMaxWidth()
                .padding(vertical = 4.dp)
                .padding(horizontal = 2.dp)
        ) {
            Row {
                Column(modifier = Modifier.padding(all = 8.dp), content = content)
            }
        }
    }
}
