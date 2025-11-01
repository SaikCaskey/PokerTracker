package com.github.saikcaskey.libs.ui_charts.presentation.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.materialkolor.palettes.TonalPalette

@Composable
fun ExpenseType.toAdaptiveColor(): Color {
    val uniqueId = name.hashCode()
    val hue = (uniqueId % 360 + 360) % 360.0
    val targetChroma = 48.0
    val palette = TonalPalette.fromHueAndChroma(hue, targetChroma)
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return if (isDarkTheme) Color(palette.tone(70)) else Color(palette.tone(40))
}
