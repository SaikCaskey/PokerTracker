package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.github.saikcaskey.libs.ui_charts.presentation.charts.charty.ChartyLineChart
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.libs.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.libs.domain.extensions.formatAsCurrency
import com.github.saikcaskey.pokertracker.libs.domain.models.Expense
import com.github.saikcaskey.pokertracker.libs.domain.util.nowAsUiDateOrNull
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

@Composable
fun DashboardBalanceSummary(
    state: DashboardFeatureComponent.UiState,
    onShowViewStatsClicked: () -> Unit,
) {
    val refreshedAtTime = nowAsUiDateOrNull()
    val data = state.profitSummaryData
    SectionContainer(
        title = "Balance",
        content = {
            if (state.profitSummaryData.expensesBeforeNow.isEmpty()) {
                Text(
                    text = "Add some expenses to see data here ",
                    style = MaterialTheme.typography.labelLarge
                )
            } else {
                AnimatedProfitText(data.nowBalance)
                ChartyLineChart(data = calculateDashboardBalanceChartData(data.expensesBeforeNow))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Last updated at: $refreshedAtTime",
                        style = MaterialTheme.typography.labelSmallEmphasized,
                    )
                }
            }
        },
        action1Label = "Viewer",
        onAction1Click = onShowViewStatsClicked,
    )
}

private fun calculateDashboardBalanceChartData(
    expenses: List<Expense>,
    startingValue: Double = 0.0,
): List<ChartDataItem> {
    return buildList {
        // Count up the total balance each time we add an event to the chart data
        // This won't include Expenses for a Venue if they aren't associated with an Event
        var eventsBalance = startingValue

        // Group expenses by their eventIds, figure out the balance for each event, and then add a
        // data point with this date and balance
        expenses
            // If the expense had no date we can't include it here, but we enforce it
            .filter { it.date != null }
            // Sort by date ascending
            .sortedBy { it.date }
            // Group by eventId (or use default)
            .groupBy { it.eventId ?: it.date }
            // Map each group of expenses
            .map { (_, expenses) ->
                // Get the minimum date of this group of expenses TODO use event date
                val eventDate = requireNotNull(expenses.minBy { requireNotNull(it.date) }.date)
                // Get the adjusted balance for this list of expense
                val balance = expenses.sumOf { expense -> expense.adjustedAmount }
                // keep track of the balance from this event for the total
                // TODO use some sort of running fold
                eventsBalance += balance

                add(
                    ChartDataItem(
                        // Use the full balance at the date of this event
                        y = eventsBalance.formatAsCurrency().toDouble(),
                        // Uses date but could just use index
                        x = eventDate.asLocalDateTime().dayOfYear,
                        label = eventDate.toString()
                    )
                )
            }
    }
}
