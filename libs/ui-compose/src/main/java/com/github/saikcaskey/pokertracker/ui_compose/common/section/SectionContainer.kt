package com.github.saikcaskey.pokertracker.ui_compose.common.section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChevronRight

@Composable
fun SectionContainer(
    modifier: Modifier = Modifier,
    title: String? = null,
    onClick: (() -> Unit)? = null,
    action: (@Composable RowScope.() -> Unit)? = null,
    horizontalPadding: Dp = 12.dp,
    verticalPadding: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = CardDefaults.outlinedShape,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                enabled = onClick != null,
                onClick = { onClick?.invoke() }
            )
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .padding(vertical = verticalPadding)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (action != null) action()
                if (onClick != null) {
                    IconButton(onClick = onClick) {
                        FontAwesomeIcons.Solid.ChevronRight.AsIcon(24.dp, "Open $title",)
                    }
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
