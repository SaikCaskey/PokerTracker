package com.github.saikcaskey.pokertracker.ui_compose.common.appbar

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChevronLeft

@Composable
fun TopBarBackButton(onBackClicked: () -> Unit) {
    IconButton(onClick = onBackClicked) {
        FontAwesomeIcons.Solid.ChevronLeft.AsIcon(
            height = 24.dp,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}
