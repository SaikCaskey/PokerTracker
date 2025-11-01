package com.github.saikcaskey.libs.ui_charts.presentation.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.model.LineData
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem

@Composable
fun ChartyLineChart(
    dataPoints: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.onSurface,
    axisColor: Color = MaterialTheme.colorScheme.onSurface,
    gridLineColor: Color = MaterialTheme.colorScheme.onSurface,
    lineFillColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    selectionBarColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(modifier = modifier) {
        LineChart(
            data = { dataPoints.map { LineData(xValue = it.x ?: 0f, yValue = it.y.toFloat()) } },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(200.dp),
            colorConfig = LineChartColorConfig(
                lineColor = ChartColor.Solid(lineColor),
                axisColor = ChartColor.Solid(axisColor),
                gridLineColor = ChartColor.Solid(gridLineColor),
                lineFillColor = ChartColor.Solid(lineFillColor),
                selectionBarColor = ChartColor.Solid(selectionBarColor),
            ),
        )
    }
}
