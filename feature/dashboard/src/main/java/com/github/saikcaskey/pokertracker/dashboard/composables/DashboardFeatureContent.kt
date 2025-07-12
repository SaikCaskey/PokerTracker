package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.saikcaskey.pokertracker.domain.components.MainPagerPageDashboardComponent

@Composable
fun DashboardFeatureContent(component: MainPagerPageDashboardComponent) {
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

        if ((upcomingEvents.value + todayEvents.value + upcomingEvents.value).isNotEmpty()) {
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
        }

        if (recentExpenses.value.isNotEmpty()) {
            item {
                DashboardExpensesSummary(
                    recentExpenses = recentExpenses.value,
                    onShowAllExpensesClicked = component::onShowAllExpensesClicked,
                    onShowInsertExpenseClicked = component::onShowInsertExpenseClicked,
                    onShowExpenseDetailClicked = component::onShowExpenseDetailClicked,
                )
            }
        }
        if (venues.value.isNotEmpty()) {
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
}
