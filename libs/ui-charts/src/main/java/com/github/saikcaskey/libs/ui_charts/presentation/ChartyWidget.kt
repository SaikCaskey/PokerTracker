package com.github.saikcaskey.libs.ui_charts.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyBarChart
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyLineChart
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyPieChart
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyPointChart
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartType

@Composable
fun ChartyWidget(
    dataPoints: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    type: ChartType = ChartType.Line,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (type) {
            ChartType.Point -> ChartyPointChart(dataPoints)
            ChartType.Pie -> ChartyPieChart(dataPoints)
            ChartType.Line -> ChartyLineChart(dataPoints)
            ChartType.Bar -> ChartyBarChart(dataPoints)
        }
    }
}
