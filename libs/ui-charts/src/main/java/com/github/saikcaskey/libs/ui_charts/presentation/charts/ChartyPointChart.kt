package com.github.saikcaskey.libs.ui_charts.presentation.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.github.saikcaskey.libs.ui_charts.presentation.ChartContainer
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.TargetConfig
import com.himanshoe.charty.common.asSolidChartColor
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointChartColorConfig
import com.himanshoe.charty.point.model.PointChartConfig
import com.himanshoe.charty.point.model.PointData

@Composable
fun ChartyPointChart(
    data: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    height: Int = 200,
    target: Float? = null,
    showClickedBar: Boolean = true,
    animatePoints: Boolean = true,
    gridLinePathEffect: PathEffect = PathEffect.cornerPathEffect(0.3f),
    axisColor: Color = MaterialTheme.colorScheme.onSurface,
    gridLineColor: Color = MaterialTheme.colorScheme.onSurface,
    circleColor: Color = MaterialTheme.colorScheme.onSurface,
    strokeColor: Color = MaterialTheme.colorScheme.onSurface,
    labelColor: Color = MaterialTheme.colorScheme.onSurface,
    selectionBarColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onPointClick: (Int, PointData) -> Unit = { _, _ -> },
) {

    val gradient = ChartColor.Gradient(
        listOf(
            MaterialTheme.colorScheme.primary.copy(0.5f),
            MaterialTheme.colorScheme.secondary.copy(0.3f),
            MaterialTheme.colorScheme.tertiary.copy(0.2f),
        )
    )
    val chartConfig = PointChartConfig(
        showClickedBar = showClickedBar,
        animatePoints = animatePoints,
        gridLinePathEffect = gridLinePathEffect
    )
    val colorConfig = PointChartColorConfig(
        axisColor = ChartColor.Solid(axisColor),
        gridLineColor = ChartColor.Solid(gridLineColor),
        circleColor = ChartColor.Solid(circleColor),
        strokeColor = ChartColor.Solid(strokeColor),
        selectionBarColor = ChartColor.Solid(selectionBarColor),
    )
    val labelConfig = LabelConfig(
        textColor = labelColor.asSolidChartColor(),
        showXLabel = true,
        showYLabel = true,
        xAxisCharCount = 5,
        labelTextStyle = MaterialTheme.typography.labelSmall
    )
    val targetConfig = TargetConfig(
        targetLineBarColors = gradient,
        targetStrokeWidth = 1.2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    ChartContainer(modifier = modifier) {
        PointChart(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(height.dp),
            data = {
                data.map {
                    PointData(xValue = it.x?.toInt() ?: 0, yValue = it.y.toFloat())
                }
            },
            colorConfig = colorConfig,
            chartConfig = chartConfig,
            labelConfig = labelConfig,
            target = target,
            targetConfig = targetConfig,
            onPointClick = onPointClick,
        )
    }
}
