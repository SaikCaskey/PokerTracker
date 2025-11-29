package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.github.saikcaskey.libs.ui_charts.presentation.charts.charty.ChartyLineChart
import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.extensions.formatAsCurrency
import com.github.saikcaskey.pokertracker.domain.models.DashboardProfitSummaryData
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.util.nowAsUiDateOrNull
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

@Composable
fun DashboardProfitSummary(data: DashboardProfitSummaryData) {
    val refreshedAtTime = nowAsUiDateOrNull()
    SectionContainer(
        title = "Balance",
        content = {
            if (data.expensesBeforeNow.isEmpty()) {
                Text(
                    text = "Add some expenses to see data here ",
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                AnimatedProfitText(data.nowBalance)
                Text(
                    text = "Refreshed at: $refreshedAtTime",
                    style = MaterialTheme.typography.bodySmall
                )
                ChartyLineChart(data = calculateDashboardBalanceChartData(data.expensesBeforeNow))
            }
        },
    )
}

/**
 * TODO -  This chart currently shows the balance change in the last X Events by figuring out the deltas,
 *         just like I use for the venue profit summary / history.
 *         I want this chart to essentially show the path to the current balance.
 *         That will include things like deposits/gifts, so I'll need to add them here as separate items
 *         when they are supported. These won't always relate to events necessarily so they'll need to be
 *         handled separately.
 *         Calculating the deltas will only give correct result if we do this with all expenses, which would be expensive
 *         Rather I could get the balance at the start of the period and then add the deltas until then.
 *         _Feature Idea_ - I can actually plot all expenses for a given time and have controls to change the date
 *         so that future expenses would be accounted for if you moved the date forward for instance.
 *         So thinking about it after typing this, I want to define some logical 'chartable event'
 *         that is displayed here as data points, these chartable events are 'bankroll adjustment's
 *         and what this graph will show is the adjustments between the start and end period.
 *         I'm not sure how much data to show here yet, but the period itself is based on the length
 *         of the input, but it would be cooler to add controls to adjust this. I'm doing that because the
 *         base query is all expenses before today, and then basically I don't want to display every
 *         expense but I want to show enough - so maybe some logic around the amount like "significant changes"
 *         but I think starting with event balance deltas + 'cashes' or deposits should be enough
 *         For the graph I can ignore the single expenses for now, but yea show them maybe with controls
 *         or with some logic about amount.
 */
private fun calculateDashboardBalanceChartData(
    expenses: List<Expense>,
    startingValue: Double = 0.0,
): List<ChartDataItem> {
    return buildList {
        // Count up the total balance each time we add an event to the chart data
        // This won't include Expenses for a Venue if they aren't associated with an event
        var eventsBalance = startingValue

        // Group expenses by their eventIds, figure out the balance for each event, and then add a
        // data point with this date and balance
        expenses
            .filter { it.eventId != null && it.date != null }
            .groupBy { requireNotNull(it.eventId) }
            .map { (_, expenses) ->
                val eventDate = requireNotNull(expenses.maxBy { requireNotNull(it.date) }.date)
                val balance = expenses.sumOf { expense -> expense.adjustedAmount }

                // keep track of the balance from this event for the total TODO use some sort of running fold
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
