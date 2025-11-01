package com.github.saikcaskey.libs.ui_charts.presentation.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChartDebugDataWidget(
    chartData: List<Any>,
    separator: String = ", ",
    mapData: (List<Any>) -> String = { it.joinToString(separator) },
) {
    val backgroundColor = colorScheme.tertiaryContainer.copy(alpha = 0.4f)
    val debugData = mapData(chartData)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(backgroundColor)
    ) {
        Text(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            text = "Data:${debugData.ifBlank { "None" }}",
            color = colorScheme.onTertiaryContainer
        )
    }
}
