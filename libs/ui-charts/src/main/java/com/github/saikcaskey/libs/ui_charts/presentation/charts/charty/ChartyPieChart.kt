package com.github.saikcaskey.libs.ui_charts.presentation.charts.charty

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.himanshoe.charty.common.asSolidChartColor
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.model.PieChartData
import kotlin.collections.get

@Composable
fun ChartyPieChart(
    data: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    isDonut: Boolean = false,
    segmentColors: Map<String, Color>,
    labelColor: Color = MaterialTheme.colorScheme.onPrimary,
    onPieSliceClick: (PieChartData) -> Unit = {},
) {
    Box(modifier = modifier) {
        PieChart(
            data = {
                data.map {
                    val itemColor = (segmentColors[it.label] ?: labelColor).asSolidChartColor()
                    PieChartData(
                        value = it.y.toFloat(),
                        color = itemColor,
                        labelColor = labelColor.asSolidChartColor(),
                        label = it.label.orEmpty()
                    )
                }
            },
            isDonutChart = isDonut,
            onPieChartSliceClick = onPieSliceClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(200.dp),
        )
    }
}
