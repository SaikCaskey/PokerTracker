package com.github.saikcaskey.pokertracker.stats.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import com.github.saikcaskey.libs.ui_charts.domain.model.ChartType
import com.github.saikcaskey.libs.ui_charts.presentation.ChartyWidget
import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.extensions.formatAsCurrency
import com.github.saikcaskey.pokertracker.domain.extensions.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.models.EventSummary
import com.github.saikcaskey.pokertracker.domain.models.Expense
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
                    onShowExpenseDetail = component::onShowExpenseDetailClicked
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
        VenueBalanceChart(calculateEventBalanceChartData(state.expenseSummary.all))
    }
}

@Composable
fun VenueExpenseSummary(
    state: VenueDetailComponent.UiState,
    modifier: Modifier = Modifier,
    onShowExpenseDetail: (Long) -> Unit = {},
) {
    Column {
        SectionContainer(
            title = "Cashes",
            modifier = modifier
                .fillMaxSize()
        ) {
            if (state.expenseSummary.cashes.isEmpty()) return@SectionContainer
            VenueCashesChart(calculateEventBalanceChartData(state.expenseSummary.cashes))
            ExpenseList(
                items = state.expenseSummary.cashes,
                onExpenseClicked = onShowExpenseDetail,
            )
        }
        SectionContainer(
            title = "Costs",
            modifier = modifier.fillMaxSize()
        ) {
            if (state.expenseSummary.costs.isEmpty()) return@SectionContainer

            calculateExpenseCountChartData(state.expenseSummary.costs)
                .also { expenseCountData ->
                    ChartyWidget(
                        type = ChartType.Bar,
                        dataPoints = expenseCountData
                    )
                }

            calculateExpenseTypeChartData(state.expenseSummary.costs)
                .also { expenseTypeData ->
                    ChartyWidget(
                        type = ChartType.Pie,
                        dataPoints = expenseTypeData.map { expense ->
                            expense.copy(y = abs(expense.y.toDouble()))
                        },
                    )

                    ExpenseList(
                        items = state.expenseSummary.costs,
                        onExpenseClicked = onShowExpenseDetail,
                    )
                }

        }
    }
}

@Composable
fun VenueExpenseTypeChart(expenseTypeData: List<ChartDataItem>) {
    if (expenseTypeData.isNotEmpty()) {
        HorizontalDivider()
        Text("Breakdown", style = MaterialTheme.typography.labelSmall)
        HorizontalDivider()

        HorizontalDivider()
    }
}


@Composable
fun VenueExpenseCountChart(expenseCountData: List<ChartDataItem>) {
    if (expenseCountData.isNotEmpty()) {

    }
}

@Composable
fun VenueBalanceChart(chartData: List<ChartDataItem>) {
    if (chartData.isNotEmpty()) {
        Column {
            HorizontalDivider()
            Text("Balance Delta", style = MaterialTheme.typography.labelSmall)
            HorizontalDivider()
            ChartyWidget(dataPoints = chartData, type = ChartType.Line)
        }
    }
}

@Composable
fun VenueCashesChart(chartData: List<ChartDataItem>) {
    if (chartData.isNotEmpty()) {
        Column {
            HorizontalDivider()
            Text("Venue Cashes", style = MaterialTheme.typography.labelSmall)
            HorizontalDivider()
            ChartyWidget(dataPoints = chartData, type = ChartType.Point)
        }
    }
}

private fun calculateExpenseTypeChartData(venueExpenses: List<Expense>): List<ChartDataItem> {
    return venueExpenses.associateBy { it.type }
        .map {
            ChartDataItem(
                label = it.key.name,
                y = abs(it.value.amount),
            )
        }.sortedByDescending { it.y.toDouble() }
}

private fun calculateExpenseCountChartData(venueExpenses: List<Expense>): List<ChartDataItem> {
    return venueExpenses
        .groupBy { it.type }
        .map {
            ChartDataItem(
                y = it.value.size,
                label = it.key.name
            )
        }
}

fun calculateEventBalanceChartData(expenses: List<Expense>): List<ChartDataItem> {
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
