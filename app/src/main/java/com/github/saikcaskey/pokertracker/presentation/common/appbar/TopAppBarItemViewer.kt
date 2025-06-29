package com.github.saikcaskey.pokertracker.presentation.common.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
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
            TextButton(onClick = onShowInsertItemClicked) {
                Text("Add")
            }
            TextButton(onClick = onDeleteAllItemsClicked) {
                Text("Clear")
            }
        },
    )
}
