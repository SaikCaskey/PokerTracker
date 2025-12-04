package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.ui_compose.extensions.asIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.User

@Composable
fun DashboardFeatureContent(
    component: DashboardFeatureComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle(
        initialValue = DashboardFeatureComponent.UiState(),
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("PokerTracker") }, actions = {
                IconButton(onClick = component::onShowAccountClicked) {
                    FontAwesomeIcons.Solid.User.asIcon(24.dp, "Go To Account")
                }
            })
        }
    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                DashboardProfitSummary(
                    state = uiState,
                    onShowViewStatsClicked = { component.onShowStatsClicked() })
            }

            item {
                DashboardExpensesSummary(
                    state = uiState,
                    onShowAllExpensesClicked = component::onShowAllExpensesClicked,
                    onShowInsertExpenseClicked = component::onShowInsertExpenseClicked,
                    onShowExpenseDetailClicked = component::onShowExpenseDetailClicked,
                )
            }

            item {
                DashboardEventSummary(
                    state = uiState,
                    onShowAllEventsClicked = component::onShowAllEventsClicked,
                    onShowPlannerClicked = component::onShowPlannerClicked,
                    onShowInsertEventClicked = component::onShowInsertEventClicked,
                    onShowEventDetailClicked = component::onShowEventDetailClicked,
                )
            }

            item {
                DashboardVenuesSummary(
                    state = uiState,
                    onShowAllVenuesClicked = component::onShowAllVenuesClicked,
                    onShowInsertVenueClicked = component::onShowInsertVenueClicked,
                    onShowVenueDetailClicked = component::onShowVenueDetailClicked,
                )
            }
        }
    }
}
