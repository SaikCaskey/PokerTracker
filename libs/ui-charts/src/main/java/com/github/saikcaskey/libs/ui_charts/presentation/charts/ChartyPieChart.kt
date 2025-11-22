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
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.github.saikcaskey.libs.ui_charts.presentation.extensions.toPieChartSegmentColor
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.himanshoe.charty.common.asSolidChartColor
import com.himanshoe.charty.pie.PieChart
import com.himanshoe.charty.pie.model.PieChartData

@Composable
fun ChartyPieChart(
    data: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    labelColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    val segmentColors: Map<String, Color> = data.mapIndexed { index, item ->
        item.label.orEmpty() to ExpenseType.fromString(item.label.orEmpty()).toPieChartSegmentColor()
    }.toMap()

    Box(modifier = modifier) {
        PieChart(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(200.dp),
            data = {
                data.map {
                    val itemColor = (segmentColors[it.label] ?: Color.Gray).asSolidChartColor()
                    PieChartData(
                        value = it.y.toFloat(),
                        color = itemColor,
                        labelColor = labelColor.asSolidChartColor(),
                        label = it.label.orEmpty()
                    )
                }
            }
        )
    }
}
