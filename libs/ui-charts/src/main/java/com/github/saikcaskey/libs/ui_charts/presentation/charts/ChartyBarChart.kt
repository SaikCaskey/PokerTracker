package com.github.saikcaskey.libs.ui_charts.presentation.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import kotlin.collections.map

@Composable
fun ChartyBarChart(
    chartData: List<ChartDataItem>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        BarChart(
            data = {
                chartData.map {
                    BarData(
                        yValue = it.y.toFloat(),
                        xValue = it.x?.toInt() ?: 0
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

