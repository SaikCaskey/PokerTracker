package com.github.saikcaskey.pokertracker.ui_compose.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.ktx.harmonize

@Composable
fun Double.toProfitColor(): Color {
    return when {
        this > 0.0 -> Color.Green.harmonize(MaterialTheme.colorScheme.primary)
        this < 0.0 -> Color.Red.harmonize(MaterialTheme.colorScheme.primary)
        else -> Color.Blue.harmonize(MaterialTheme.colorScheme.primary)
    }
}
