package com.github.saikcaskey.pokertracker.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.presentation.common.profitsummary.AnimatedExpenseText
import com.github.saikcaskey.pokertracker.presentation.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.presentation.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.presentation.common.section.SectionListContainer
import com.github.saikcaskey.pokertracker.data.utils.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.Venue

@Composable
fun MainPagerDashboardContent(component: MainPagerPageDashboardComponent) {
    val upcomingEvents = component.upcomingEvents.collectAsState()
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
            SectionContainer(
                title = "Events",
                onAddClick = component::onShowInsertEventClicked,
                onShowAllClick = component::onShowAllEventsClicked,
            ) {
                Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
                DashboardEventsList(
                    items = upcomingEvents.value,
                    emptyMessage = "No Upcoming events",
                    onEventClicked = component::onShowEventDetailClicked,
                )
                Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
                DashboardEventsList(
                    items = recentEvents.value,
                    emptyMessage = "No Recent events",
                    onEventClicked = component::onShowEventDetailClicked,
                )
            }
        }
        item {
            SectionContainer(
                title = "Expenses",
                onAddClick = component::onShowInsertExpenseClicked,
                onShowAllClick = component::onShowAllExpensesClicked,
            ) {
                DashboardExpensesList(
                    items = recentExpenses.value,
                    onExpenseClicked = component::onShowExpenseDetailClicked,
                )
            }
        }
        item {
            SectionContainer(
                title = "Venues",
                onAddClick = component::onShowInsertVenueClicked,
                onShowAllClick = component::onShowAllVenuesClicked,
            ) {
                DashboardVenueList(
                    items = venues.value,
                    onVenueClicked = component::onShowVenueDetailClicked,
                )
            }
        }
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
