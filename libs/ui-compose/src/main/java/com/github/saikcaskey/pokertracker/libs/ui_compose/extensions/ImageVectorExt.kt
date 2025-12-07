package com.github.saikcaskey.pokertracker.ui_compose.extensions

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
fun ImageVector.AsIcon(
    height: Dp,
    contentDescription: String,
    tint: Color = MaterialTheme.colorScheme.onSurface,
) {
    Icon(
        modifier = Modifier.height(height),
        imageVector = this,
        tint = tint,
        contentDescription = contentDescription
    )
}
