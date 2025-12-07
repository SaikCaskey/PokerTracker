package com.github.saikcaskey.pokertracker.ui_compose.extensions

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
fun ImageVector.asIcon(
    height: Dp,
    contentDescription: String,
) {
    Icon(
        modifier = Modifier.height(height),
        imageVector = this,
        contentDescription = contentDescription
    )
}