package com.github.saikcaskey.pokertracker.ui_compose.common.appbar

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle

@Composable
fun TopAppBarItemViewer(
    title: String,
    onBackClicked: () -> Unit,
    onShowInsertItemClicked: () -> Unit,
    onDeleteAllItemsClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = { TopBarBackButton(onBackClicked = onBackClicked) },
        actions = {
            IconButton(onClick = onShowInsertItemClicked) {
                FontAwesomeIcons.Solid.PlusCircle.AsIcon(24.dp, "Add Item")
            }
            IconButton(onClick = onDeleteAllItemsClicked) {
                FontAwesomeIcons.Solid.PlusCircle.AsIcon(24.dp, "Clear All Items")
            }
        },
    )
}
