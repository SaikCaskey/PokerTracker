package com.github.saikcaskey.pokertracker.ui_compose.common.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SectionListItem(
    onItemClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(enabled = onItemClick != null, onClick = { onItemClick?.invoke() }),
        content = content
    )
}
