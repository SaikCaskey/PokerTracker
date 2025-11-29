package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent

@Composable
fun DashboardFeatureContent(component: DashboardFeatureComponent) {
    val uiState = component.uiState.collectAsStateWithLifecycle(
        initialValue = DashboardFeatureComponent.UiState(),
        minActiveState = Lifecycle.State.RESUMED
    )
    Scaffold { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 12.dp)
        ) {

            item {
                DashboardProfitSummary(uiState.value.profitSummaryData)
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
                DashboardEventSummary(
                    uiState.value.eventsData,
                    onShowAllEventsClicked = component::onShowAllEventsClicked,
                    onShowInsertEventClicked = component::onShowInsertEventClicked,
                    onShowEventDetailClicked = component::onShowEventDetailClicked,
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
}
