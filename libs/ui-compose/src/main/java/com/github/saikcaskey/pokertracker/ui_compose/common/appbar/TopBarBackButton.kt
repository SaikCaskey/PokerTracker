package com.github.saikcaskey.pokertracker.ui_compose.common.appbar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.ArrowAltCircleLeft

@Composable
fun TopBarBackButton(onBackClicked: () -> Unit) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = FontAwesomeIcons.Regular.ArrowAltCircleLeft,
            contentDescription = "Back button",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
