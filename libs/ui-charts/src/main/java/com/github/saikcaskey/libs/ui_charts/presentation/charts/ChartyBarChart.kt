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
    height: Int = 200,
    target: Float? = null,
) {
    val labelConfig = LabelConfig(
        textColor = MaterialTheme.colorScheme.onSurface.asSolidChartColor(),
        showXLabel = true,
        showYLabel = true,
        xAxisCharCount = 5,
        labelTextStyle = MaterialTheme.typography.labelSmall
    )
    val barChartConfig = BarChartConfig(
        showAxisLines = true,
        showGridLines = true,
        drawNegativeValueChart = true,
        showCurvedBar = true,
        minimumBarCount = 1,
        cornerRadius = CornerRadius(12f, 12f)
    )
    val targetConfig = TargetConfig(
        targetLineBarColors = MaterialTheme.colorScheme.onSurfaceVariant
            .asSolidChartColor(),
        targetStrokeWidth = 0.9f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val chartConfig = BarChartColorConfig(
        fillBarColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            .asSolidChartColor(),
        negativeBarColors = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
            .asSolidChartColor()
    )

    Box(modifier = modifier) {
        BarChart(
            data = {
                chartData.map {
                    BarData(
                        yValue = it.y.toFloat(),
                        xValue = it.label.orEmpty()
                    )
                }
            },
            target = target,
            targetConfig = targetConfig,
            labelConfig = labelConfig,
            barChartConfig = barChartConfig,
            barChartColorConfig = chartConfig,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(height.dp),
        )
    }
}

