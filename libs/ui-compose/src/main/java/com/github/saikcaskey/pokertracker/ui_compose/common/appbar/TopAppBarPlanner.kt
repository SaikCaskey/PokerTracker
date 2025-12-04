package com.github.saikcaskey.pokertracker.ui_compose.common.appbar

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun TopAppBarPlanner(
    onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Planner") },
        navigationIcon = { TopBarBackButton(onBackClicked = onBackClicked) },
    )
}
