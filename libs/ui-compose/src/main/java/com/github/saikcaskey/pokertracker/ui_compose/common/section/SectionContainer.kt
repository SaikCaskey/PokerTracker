package com.github.saikcaskey.pokertracker.ui_compose.common.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Card(
        shape = CardDefaults.outlinedShape,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .padding(vertical = verticalPadding)

    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            if (!title.isNullOrBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(vertical = 4.dp)
                .padding(horizontal = 2.dp)
        ) {
            Row {
                Column(modifier = Modifier.padding(all = 8.dp), content = content)
            }
        }
    }
}
