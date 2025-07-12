package com.github.saikcaskey.pokertracker.ui_compose.components.venue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.github.saikcaskey.pokertracker.domain.util.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.components.VenueDetailComponent
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopBarItemDetail
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventsList
import com.github.saikcaskey.pokertracker.ui_compose.extensions.toProfitColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle

@Composable
fun VenueDetailContent(modifier: Modifier = Modifier, component: VenueDetailComponent) {
    val state by component.uiState.collectAsState()

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
            item {
                VenueEventsSummary(
                    upcomingEvents = state.upcomingEvents,
                    todayEvents = state.todayEvents,
                    pastEvents = state.pastEvents,
                    onEventClicked = component::onShowEventDetailClicked
                )
            }
        }
    }
}

@Composable
fun VenueEventsSummary(
    upcomingEvents: List<Event>,
    pastEvents: List<Event>,
    todayEvents: List<Event>,
    onEventClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
    ) {
        Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
        EventsList(
            items = upcomingEvents,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "Today", style = MaterialTheme.typography.labelLarge)
        EventsList(
            items = todayEvents,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
        EventsList(
            items = pastEvents,
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
        Text("Id: ${state.venue?.id}")
        Text("Created:  ${state.venue?.createdAt?.toUiDateTimeOrNull()}")
        if (state.venue?.description != null) {
            Spacer(Modifier.height(4.dp))
            Text(venue?.description.orEmpty())
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
    SectionContainer(
        title = "Venue Cashflow",
        modifier = modifier
            .fillMaxSize()
            .clickable(state.venue?.id != null) {
                state.venue?.id?.let { onVenueClicked?.invoke(it) }
            }
    ) {
        Text("Expenses:")
        AnimatedProfitText(
            state.profitSummary.costsSubtotal,
            forcedColor = (-state.profitSummary.costsSubtotal).toProfitColor(),
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
