package com.github.saikcaskey.stats.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.github.saikcaskey.pokertracker.domain.extensions.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.models.EventSummary
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopBarItemDetail
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventsList
import com.github.saikcaskey.pokertracker.ui_compose.extensions.toProfitColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle
import kotlin.math.abs

@Composable
fun VenueDetailContent(modifier: Modifier = Modifier, component: VenueDetailComponent) {
    val state by component.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarItemDetail(
                title = "Venue Detail",
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
            item { VenueExpenseSummary(state) }
            item {
                VenueEventsSummary(
                    eventSummary = state.eventSummary,
                    onEventClicked = component::onShowEventDetailClicked
                )
            }
        }
    }
}

@Composable
fun VenueEventsSummary(
    eventSummary: EventSummary,
    onEventClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
    ) {
        Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
        EventsList(
            items = eventSummary.upcoming,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "Today", style = MaterialTheme.typography.labelLarge)
        EventsList(
            items = eventSummary.today,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "All", style = MaterialTheme.typography.labelLarge)
        EventsList(
            items = eventSummary.all,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
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
        title = "Venue Cashflow",
        modifier = modifier
            .fillMaxSize()
            .clickable(venue?.id != null) {
                venue?.id?.let { onVenueClicked?.invoke(it) }
            }
    ) {
        Text("Expenses:")
        AnimatedProfitText(
            state.profitSummary.costsSubtotal,
            forcedColor = (state.profitSummary.costsSubtotal).toProfitColor(),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp)
        )
        Text("Cashout:")
        AnimatedProfitText(
            state.profitSummary.cashesSubtotal,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp)
        )
        Text("Total:", style = MaterialTheme.typography.titleMedium)
        AnimatedProfitText(state.profitSummary.balance)
    }
}

@Composable
fun VenueExpenseSummary(
    state: VenueDetailComponent.UiState,
    modifier: Modifier = Modifier,
    onVenueClicked: ((Long) -> Unit)? = null,
) {
    val venue = state.venue
    SectionContainer(
        title = "Venue Expenses",
        modifier = modifier
            .fillMaxSize()
            .clickable(venue?.id != null) {
                venue?.id?.let { onVenueClicked?.invoke(it) }
            }
    ) {
        val expenseCountData = state.expenseSummary.all.map {
            ChartDataItem(
                y = it.amount,
                x = it.date?.asLocalDateTime()?.dayOfYear ?: 0
            )
        }
        HorizontalDivider()
        Text("Recent", style = MaterialTheme.typography.labelSmall)
        HorizontalDivider()
        ChartyWidget(dataPoints = expenseCountData, type = ChartType.Line)
        HorizontalDivider()
        ChartyWidget(dataPoints = expenseCountData, type = ChartType.Point)
        HorizontalDivider()
        val expenseTypeData = state.expenseSummary.all.associateBy { it.type }
            .map {
                ChartDataItem(
                    label = it.key.name,
                    y = it.value.amount,
                )
            }
        Text("Breakdown", style = MaterialTheme.typography.labelSmall)
        HorizontalDivider()
        ChartyWidget(
            dataPoints = expenseTypeData.map { it.copy(y = abs(it.y.toDouble())) },
            type = ChartType.Pie
        )
        HorizontalDivider()
        ChartyWidget(dataPoints = expenseTypeData, type = ChartType.Bar)
    }
}
