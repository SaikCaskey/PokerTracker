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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.TargetConfig
import com.himanshoe.charty.common.asSolidChartColor
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.InteractionTooltipConfig
import com.himanshoe.charty.line.config.LineChartAxisConfig
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.config.LineChartGridConfig
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData

@Composable
fun ChartyLineChart(
    data: List<ChartDataItem>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    lineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    axisColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    gridLineColor: Color? = null,
    lineFillColor: Color? = null,
    selectionBarColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
) {
    val defaultGradient = ChartColor.Gradient(
        listOf(
            MaterialTheme.colorScheme.primary.copy(0.5f),
            MaterialTheme.colorScheme.secondary.copy(0.3f),
            MaterialTheme.colorScheme.tertiary.copy(0.2f),
        )
    )
    val labelTextColor = MaterialTheme.colorScheme.onSurface.asSolidChartColor()
    val labelFontStyle = MaterialTheme.typography.labelSmall

    Box(modifier = modifier) {
        LineChart(
            data = {
                data.map {
                    LineData(
                        xValue = it.label.orEmpty(),
                        yValue = it.y.toFloat()
                    )
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .requiredHeight(200.dp),
            target = target,
            targetConfig = TargetConfig(
                targetLineBarColors = defaultGradient,
                targetStrokeWidth = 0.9f,
                pathEffect = PathEffect.chainPathEffect(
                    PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    PathEffect.cornerPathEffect(33f),
                )
            ),
            colorConfig = LineChartColorConfig(
                lineColor = ChartColor.Solid(lineColor),
                axisColor = ChartColor.Solid(axisColor),
                gridLineColor = gridLineColor?.asSolidChartColor() ?: defaultGradient,
                lineFillColor = lineFillColor?.asSolidChartColor() ?: defaultGradient,
                selectionBarColor = ChartColor.Solid(selectionBarColor),
            ),
            smoothLineCurve = true,
            showFilledArea = true,
            showLineStroke = true,
            labelConfig = LabelConfig(
                textColor = labelTextColor,
                showXLabel = false,
                showYLabel = false,
                labelTextStyle = labelFontStyle,
                xAxisCharCount = 4
            ),
            chartConfig = LineChartConfig(
                axisConfig = LineChartAxisConfig(),
                gridConfig = LineChartGridConfig(),
                lineConfig = LineConfig(
                    lineCap = StrokeCap.Round,
                    drawPointerCircle = true,
                    showValueOnLine = true,
                    valueTextColor = labelTextColor
                ),
                interactionTooltipConfig = InteractionTooltipConfig(
                    textColor = labelTextColor
                ),
            )
        )
    }
}
