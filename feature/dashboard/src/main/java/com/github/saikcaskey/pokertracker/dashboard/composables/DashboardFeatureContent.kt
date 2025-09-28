package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.domain.components.DashboardFeatureComponent

@Composable
fun DashboardFeatureContent(component: DashboardFeatureComponent) {

    val uiState = component.uiState.collectAsStateWithLifecycle(
        initialValue = DashboardFeatureComponent.UiState(),
        minActiveState = Lifecycle.State.RESUMED
    )

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {

        item {
            DashboardProfitSummary(uiState.value.profitSummaryData)
        }

        item {
            DashboardEventSummary(
                uiState.value.eventsData,
                onShowAllEventsClicked = component::onShowAllEventsClicked,
                onShowInsertEventClicked = component::onShowInsertEventClicked,
                onShowEventDetailClicked = component::onShowEventDetailClicked,
            )
        }

        item {
            DashboardExpensesSummary(
                uiState.value.recentExpenses,
                onShowAllExpensesClicked = component::onShowAllExpensesClicked,
                onShowInsertExpenseClicked = component::onShowInsertExpenseClicked,
                onShowExpenseDetailClicked = component::onShowExpenseDetailClicked,
            )
        }
        item {
            DashboardVenuesSummary(
                uiState.value.recentVenues,
                onShowAllVenuesClicked = component::onShowAllVenuesClicked,
                onShowInsertVenueClicked = component::onShowInsertVenueClicked,
                onShowVenueDetailClicked = component::onShowVenueDetailClicked,
            )
        }
    }
}
