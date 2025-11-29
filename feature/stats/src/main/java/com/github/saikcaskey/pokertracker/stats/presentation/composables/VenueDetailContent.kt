package com.github.saikcaskey.pokertracker.stats.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartDataItem
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyBarChart
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyLineChart
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyPieChart
import com.github.saikcaskey.libs.ui_charts.presentation.charts.ChartyPointChart
import com.github.saikcaskey.libs.ui_charts.presentation.extensions.toPieChartSegmentColor
import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.extensions.formatAsCurrency
import com.github.saikcaskey.pokertracker.domain.extensions.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.models.EventSummary
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.stats.domain.components.VenueDetailComponent
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopBarItemDetail
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventsList
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ExpenseList
import com.github.saikcaskey.pokertracker.ui_compose.extensions.toProfitColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle
import kotlin.math.abs

@Composable
fun VenueDetailContent(component: VenueDetailComponent) {
    val state by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarItemDetail(
                title = state.venue?.name.orEmpty(),
                onDeleteClicked = component::onDeleteVenueClicked,
                onBackClicked = component::onBackClicked,
                onEditClicked = component::onShowEditVenueClicked
            )
        },
        floatingActionButton = {
            FloatingActionButton(component::onShowInsertEventClicked) {
                Icon(
                    modifier = Modifier.height(24.dp),
                    imageVector = FontAwesomeIcons.Solid.PlusCircle,
                    contentDescription = "Add event"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
        ) {
            item { VenueDetailSummary(state) }
            item { VenueProfitSummary(state) }
            item {
                VenueEventsSummary(
                    eventSummary = state.eventSummary,
                    onEventClicked = component::onShowEventDetailClicked,
                    onShowInsertEventClicked = component::onShowInsertEventClicked,
                    onShowAllEventsClicked = component::onShowAllEventsClicked,
                )
            }
            item {
                VenueExpenseSummary(
                    state = state,
                    onShowExpenseDetail = component::onShowExpenseDetailClicked,
                    onShowAllExpenses = component::onShowAllExpensesClicked,
                )
            }
        }
    }
}

@Composable
fun VenueEventsSummary(
    eventSummary: EventSummary,
    onEventClicked: (Long) -> Unit,
    onShowInsertEventClicked: () -> Unit,
    onShowAllEventsClicked: () -> Unit,
) {
    SectionContainer(
        title = "Events",
        onAddClick = onShowInsertEventClicked,
        onShowAllClick = onShowAllEventsClicked,
    ) {
        if (eventSummary.today.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Today", style = MaterialTheme.typography.labelLarge)
            EventsList(
                items = eventSummary.today,
                emptyMessage = "",
                onEventClicked = onEventClicked,
            )
        }

        if (eventSummary.upcoming.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
            EventsList(
                items = eventSummary.upcoming,
                emptyMessage = "",
                onEventClicked = onEventClicked,
            )
        }

        if (eventSummary.recent.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
            EventsList(
                items = eventSummary.recent,
                emptyMessage = "",
                onEventClicked = onEventClicked,
            )
        }
    }
}

@Composable
fun VenueDetailSummary(state: VenueDetailComponent.UiState) {
    SectionContainer(modifier = Modifier.fillMaxHeight()) {
        val venue = state.venue
        Text(venue?.name.orEmpty(), style = MaterialTheme.typography.displaySmall)
        Text("Id: ${venue?.id}")
        Text("Created: ${venue?.createdAt?.toUiDateTimeOrNull()}")
        Text("Updated At: ${venue?.updatedAt?.toUiDateTimeOrNull() ?: "Never"}")
        if (venue?.description != null) {
            Spacer(Modifier.height(4.dp))
            Text(venue.description.orEmpty())
        }
        if (venue?.address != null) {
            Spacer(Modifier.height(4.dp))
            Text(venue.address.orEmpty())
        }
    }
}

@Composable
fun VenueProfitSummary(
    state: VenueDetailComponent.UiState,
    modifier: Modifier = Modifier,
    onVenueClicked: ((Long) -> Unit)? = null,
) {
    val venue = state.venue
    SectionContainer(
        title = "Profit Summary",
        modifier = modifier
            .fillMaxSize()
            .clickable(venue?.id != null) {
                venue?.id?.let { onVenueClicked?.invoke(it) }
            }
    ) {
        Text("Total Expenses")
        AnimatedProfitText(
            state.profitSummary.costsSubtotal,
            forcedColor = (state.profitSummary.costsSubtotal).toProfitColor(),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp)
        )
        Text("Total Cashes")
        AnimatedProfitText(
            state.profitSummary.cashesSubtotal,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp)
        )
        Text("Total Profit", style = MaterialTheme.typography.titleMedium)
        AnimatedProfitText(state.profitSummary.balance)
        ChartyLineChart(data = calculateEventBalanceChartData(state.expenseSummary.all))
    }
}

@Composable
fun VenueExpenseSummary(
    state: VenueDetailComponent.UiState,
    onShowExpenseDetail: (Long) -> Unit,
    onShowAllExpenses: () -> Unit,
) {
    Column {
        VenueCashesSection(state.expenseSummary.cashes)
        VenueCostsSection(state.expenseSummary.costs)
        VenueExpenseFeedSection(state, onShowExpenseDetail, onShowAllExpenses)
    }
}

@Composable
private fun VenueExpenseFeedSection(
    state: VenueDetailComponent.UiState,
    onShowExpenseDetail: (Long) -> Unit,
    onShowAllExpenses: () -> Unit,
) {

    SectionContainer(title = "Feed", onShowAllClick = onShowAllExpenses) {
        ExpenseList(
            items = state.expenseSummary.all.sortedByDescending(Expense::date),
            onExpenseClicked = onShowExpenseDetail,
        )
    }
}

@Composable
private fun VenueCostsSection(
    expenses: List<Expense>,
    modifier: Modifier = Modifier,
) {
    SectionContainer(
        title = "Expenses",
        modifier = modifier.fillMaxSize()
    ) {
        if (expenses.isEmpty()) return@SectionContainer
        Text("Breakdown", style = MaterialTheme.typography.titleMedium)
        VenueExpenseTopExpensesBreakdownChart(expenses)
        Text("Distribution", style = MaterialTheme.typography.titleMedium)
        VenueCostsDistributionChart(expenses)
    }
}

@Composable
fun VenueExpenseTopExpensesBreakdownChart(
    expenses: List<Expense>,
) {
    ChartyBarChart(
        chartData = expenses
            .groupBy(Expense::type)
            .map {
                ChartDataItem(
                    y = it.value.size,
                    label = it.key.name
                )
            }
    )
}

@Composable
fun VenueCostsDistributionChart(
    expenses: List<Expense>,
) {
    val data = expenses
        .associateBy { it.type }
        .map {
            ChartDataItem(
                label = it.key.name,
                y = abs(it.value.amount),
            )
        }
        .sortedByDescending { it.y.toDouble() }
    ChartyPieChart(
        data = data,
        segmentColors = data.mapIndexed { index, item ->
            // Map some colors from expense types of the incoming segments
            // Since this one relates to expense types, we'll use the label as
            // the type
            // TODO employ an 'official' set of colors and ensure it's fixed for given items
            item.label.orEmpty() to ExpenseType.fromString(item.label.orEmpty())
                .toPieChartSegmentColor()
        }.toMap()
    )
}

@Composable
private fun VenueCashesSection(
    expenses: List<Expense>,
    modifier: Modifier = Modifier,
) {
    SectionContainer(
        title = "Cashes",
        modifier = modifier.fillMaxWidth()
    ) {
        if (expenses.isEmpty()) return@SectionContainer
        VenueCashesChart(expenses)
    }
}

@Composable
private fun VenueCashesChart(
    expenses: List<Expense>,
) {
    val chartData = expenses
        .associateBy(Expense::eventId)
        .map { entry ->
            ChartDataItem(
                label = entry.key.toString(),
                x = entry.key,
                y = abs(entry.value.amount),
            )
        }

    if (chartData.isNotEmpty()) {
        Column {
            ChartyPointChart(data = chartData)
        }
    }
}

private fun calculateEventBalanceChartData(expenses: List<Expense>): List<ChartDataItem> {
    return buildList {
        // Count up the total balance each time we add an event to the chart data
        // This won't include Expenses for a Venue if they aren't associated with an event
        var eventsBalance = 0.0

        // Group expenses by their eventIds, figure out the balance for each event, and then add a
        // data point with this date and balance
        expenses
            .filter { it.eventId != null && it.date != null }
            .groupBy { requireNotNull(it.eventId) }
            .map { (_, expenses) ->
                val eventDate = requireNotNull(expenses.maxBy { requireNotNull(it.date) }.date)
                val balance = expenses.sumOf { expense -> expense.adjustedAmount }

                // keep track of the balance from this event for the total TODO
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
