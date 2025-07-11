package com.github.saikcaskey.pokertracker.ui_compose.components.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.github.saikcaskey.pokertracker.domain.util.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedExpenseText
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionListContainer

@Composable
fun MainPagerDashboardContent(component: MainPagerPageDashboardComponent) {
    // TODO
    val upcomingEvents = component.upcomingEvents.collectAsState()
    val todayEvents = component.todayEvents.collectAsState()
    val recentEvents = component.recentEvents.collectAsState()
    val recentExpenses = component.recentExpenses.collectAsState()
    val venues = component.recentVenues.collectAsState()
    val upcomingCosts = component.upcomingCosts.collectAsState()
    val balance = component.balance.collectAsState()
    val balanceForYear = component.balanceForYear.collectAsState()
    val balanceForMonth = component.balanceForMonth.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {

        item {
            DashboardProfitSummary(
                upcomingCosts = upcomingCosts.value,
                balance = balance.value,
                balanceForYear = balanceForYear.value,
                balanceForMonth = balanceForMonth.value
            )
        }

        item {
            DashboardEventSummary(
                recentEvents = recentEvents.value,
                todayEvents = todayEvents.value,
                upcomingEvents = upcomingEvents.value,
                onShowAllEventsClicked = component::onShowAllEventsClicked,
                onShowInsertEventClicked = component::onShowInsertEventClicked,
                onShowEventDetailClicked = component::onShowEventDetailClicked,
            )
        }

        item {
            DashboardExpensesSummary(
                recentExpenses = recentExpenses.value,
                onShowAllExpensesClicked = component::onShowAllExpensesClicked,
                onShowInsertExpenseClicked = component::onShowInsertExpenseClicked,
                onShowExpenseDetailClicked = component::onShowExpenseDetailClicked,
            )
        }

        item {
            DashboardVenuesSummary(
                venues = venues.value,
                onShowAllVenuesClicked = component::onShowAllExpensesClicked,
                onShowInsertVenueClicked = component::onShowInsertVenueClicked,
                onShowVenueDetailClicked = component::onShowVenueDetailClicked,
            )
        }
    }
}

@Composable
fun DashboardVenuesSummary(
    venues: List<Venue>,
    onShowAllVenuesClicked: () -> Unit,
    onShowInsertVenueClicked: () -> Unit,
    onShowVenueDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Venues",
        onAddClick = onShowInsertVenueClicked,
        onShowAllClick = onShowAllVenuesClicked,
    ) {
        DashboardVenueList(
            items = venues,
            onVenueClicked = onShowVenueDetailClicked,
        )
    }
}

@Composable
fun DashboardExpensesSummary(
    recentExpenses: List<Expense>,
    onShowAllExpensesClicked: () -> Unit,
    onShowInsertExpenseClicked: () -> Unit,
    onShowExpenseDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Expenses",
        onAddClick = onShowInsertExpenseClicked,
        onShowAllClick = onShowAllExpensesClicked,
    ) {
        DashboardExpensesList(
            items = recentExpenses,
            onExpenseClicked = onShowExpenseDetailClicked,
        )
    }
}

@Composable
fun DashboardEventSummary(
    recentEvents: List<Event>,
    todayEvents: List<Event>,
    upcomingEvents: List<Event>,
    onShowAllEventsClicked: () -> Unit,
    onShowInsertEventClicked: () -> Unit,
    onShowEventDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
        onAddClick = onShowInsertEventClicked,
        onShowAllClick = onShowAllEventsClicked,
    ) {
        Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
        DashboardEventsList(
            items = upcomingEvents,
            emptyMessage = "No Upcoming events",
            onEventClicked = onShowEventDetailClicked,
        )
        Text(text = "Today", style = MaterialTheme.typography.labelLarge)
        DashboardEventsList(
            items = todayEvents,
            emptyMessage = "",
            onEventClicked = onShowEventDetailClicked,
        )
        Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
        DashboardEventsList(
            items = recentEvents,
            emptyMessage = "No Recent events",
            onEventClicked = onShowEventDetailClicked,
        )
    }
}

@Composable
fun DashboardProfitSummary(
    balance: Double,
    balanceForYear: Double,
    balanceForMonth: Double,
    upcomingCosts: Double,
) {
    SectionContainer(
        title = "Cashflow",
        content = {
            if (upcomingCosts < 0) {
                Text(text = "Upcoming Expenses: ", style = MaterialTheme.typography.bodySmall)
                AnimatedProfitText(upcomingCosts)
            }
            Text(text = "This Month: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(balanceForMonth)
            Text(text = "This Year: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(balanceForYear)
            Text(text = "All Time: ", style = MaterialTheme.typography.bodySmall)
            AnimatedProfitText(balance)
        },
    )
}

@Composable
fun DashboardEventsList(
    items: List<Event>,
    limit: Int? = 4,
    emptyMessage: String,
    onEventClicked: (Long) -> Unit,
) {
    SectionListContainer(
        items = items,
        adjustHeight = true,
        limit = limit,
        onItemClicked = { onEventClicked(it.id) },
        emptyMessage = emptyMessage
    ) { event ->
        Text(
            text = event.name?.take(50).orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = event.description?.take(50).orEmpty(),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "At: ${event.date?.toUiDateTimeOrNull().orEmpty()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DashboardExpensesList(items: List<Expense>, onExpenseClicked: (Long) -> Unit) {
    SectionListContainer(
        items = items,
        onItemClicked = { onExpenseClicked(it.id) },
    ) { expense ->
        AnimatedExpenseText(expense, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = expense.description?.take(50).orEmpty(),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = "Created:  ${expense.createdAt?.toUiDateTimeOrNull().orEmpty()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DashboardVenueList(items: List<Venue>, onVenueClicked: (Long) -> Unit) {
    SectionListContainer(
        items = items,
        onItemClicked = { onVenueClicked(it.id) },
    ) { venue ->
        Text(
            text = venue.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = venue.description?.take(50).orEmpty(),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Created:  ${venue.createdAt?.toUiDateTimeOrNull()}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
