package com.github.saikcaskey.pokertracker.presentation.common.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
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
            TextButton(onClick = { onDeleteClicked?.invoke() }) { Text("Delete") }
            TextButton(onClick = { onEditClicked?.invoke() }) { Text("Edit") }
        },
        navigationIcon = { TopBarBackButton(onBackClicked = { onBackClicked?.invoke() }) },
    )
}
