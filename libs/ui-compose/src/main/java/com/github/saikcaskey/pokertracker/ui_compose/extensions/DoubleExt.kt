package com.github.saikcaskey.pokertracker.ui_compose.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Double.toProfitColor(): Color {
    return when {
        this > 0.0 -> MaterialTheme.colorScheme.primary
        this < 0.0 -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }
}
