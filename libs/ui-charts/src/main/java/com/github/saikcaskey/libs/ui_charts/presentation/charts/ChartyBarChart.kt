package com.github.saikcaskey.libs.ui_charts.presentation.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.config.BarChartColorConfig
import com.himanshoe.charty.bar.config.BarChartConfig
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.TargetConfig
import com.himanshoe.charty.common.asSolidChartColor

@Composable
fun ChartyBarChart(
    chartData: List<ChartDataItem>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        BarChart(
            targetConfig = TargetConfig(
                targetLineBarColors = MaterialTheme.colorScheme.onSurfaceVariant
                    .asSolidChartColor(),
                targetStrokeWidth = 0.9f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            ),
            labelConfig = LabelConfig(
                textColor = MaterialTheme.colorScheme.onSurface.asSolidChartColor(),
                showXLabel = true,
                showYLabel = true,
                xAxisCharCount = 5,
                labelTextStyle = MaterialTheme.typography.labelSmall
            ),
            barChartConfig = BarChartConfig(
                showAxisLines = true,
                showGridLines = true,
                drawNegativeValueChart = true,
                showCurvedBar = true,
                minimumBarCount = 1,
                cornerRadius = CornerRadius(12f, 12f)
            ),
            barChartColorConfig = BarChartColorConfig(
                fillBarColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    .asSolidChartColor(),
                negativeBarColors = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                    .asSolidChartColor()
            ),
            data = {
                chartData.map {
                    BarData(
                        yValue = it.y.toFloat(),
                        xValue = it.label.orEmpty()
                    )
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(200.dp),
        )
    }
}

