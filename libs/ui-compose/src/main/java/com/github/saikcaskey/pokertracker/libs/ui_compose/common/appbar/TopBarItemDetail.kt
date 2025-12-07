package com.github.saikcaskey.pokertracker.ui_compose.common.appbar

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit
import compose.icons.fontawesomeicons.solid.Trash

@Composable
fun TopBarItemDetail(
    title: String,
    onEditClicked: (() -> Unit)? = null,
    onDeleteClicked: (() -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { onDeleteClicked?.invoke() }) {
                FontAwesomeIcons.Solid.Trash.AsIcon(24.dp, "Delete this item")
            }
            IconButton(onClick = { onEditClicked?.invoke() }) {
                FontAwesomeIcons.Solid.Edit.AsIcon(24.dp, "Edit this item")
            }
        },
        navigationIcon = { TopBarBackButton(onBackClicked = { onBackClicked?.invoke() }) },
    )
}
