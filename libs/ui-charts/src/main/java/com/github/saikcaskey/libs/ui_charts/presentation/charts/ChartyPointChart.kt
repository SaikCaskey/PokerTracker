package com.github.saikcaskey.libs.ui_charts.presentation.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointChartColorConfig
import com.himanshoe.charty.point.model.PointChartConfig
import com.himanshoe.charty.point.model.PointData
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem

@Composable
fun ChartyPointChart(
    data: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    showClickedBar: Boolean = true,
    animatePoints: Boolean = true,
    gridLinePathEffect: PathEffect = PathEffect.cornerPathEffect(0.3f),
    axisColor: Color = MaterialTheme.colorScheme.onSurface,
    gridLineColor: Color = MaterialTheme.colorScheme.onSurface,
    circleColor: Color = MaterialTheme.colorScheme.onSurface,
    strokeColor: Color = MaterialTheme.colorScheme.onSurface,
    selectionBarColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Box(modifier = modifier) {
        PointChart(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(200.dp),
            data = { data.map { PointData(xValue = it.x?.toInt() ?: 0, yValue = it.y.toFloat()) } },
            colorConfig = PointChartColorConfig(
                axisColor = ChartColor.Solid(axisColor),
                gridLineColor = ChartColor.Solid(gridLineColor),
                circleColor = ChartColor.Solid(circleColor),
                strokeColor = ChartColor.Solid(strokeColor),
                selectionBarColor = ChartColor.Solid(selectionBarColor),
            ),
            chartConfig = PointChartConfig(
                showClickedBar = showClickedBar,
                animatePoints = animatePoints,
                gridLinePathEffect = gridLinePathEffect
            )
        )
    }
}

