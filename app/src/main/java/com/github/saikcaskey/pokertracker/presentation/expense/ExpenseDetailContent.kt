package com.github.saikcaskey.pokertracker.presentation.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.prettyName
import com.github.saikcaskey.pokertracker.domain.util.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.domain.components.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopBarItemDetail
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedExpenseText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

@Composable
internal fun ExpenseDetailContent(
    component: ExpenseDetailComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarItemDetail(
                title = "Expense Detail",
                onDeleteClicked = component::onDeleteExpenseClicked,
                onBackClicked = component::onBackClicked,
                onEditClicked = component::onShowEditExpenseClicked
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            // Expense Detail
            state.expense?.let { expense -> ExpenseDetailSummary(expense) }
            // Venue Detail
            ExpenseVenueSummary(
                state,
                onVenueClicked = { venueId -> component.onShowVenueDetailClicked(venueId) })
            // Event Detail
            ExpenseEventSummary(
                state,
                onEventClicked = { eventId -> component.onShowEventDetailClicked(eventId) })
        }
    }
}

@Composable
fun ExpenseDetailSummary(expense: Expense) {
    SectionContainer(title = "Info about this Expense") {
        val eventDescription = expense.description
        AnimatedExpenseText(expense, MaterialTheme.typography.bodyLarge)
        Text(expense.prettyName)
        if (!eventDescription.isNullOrBlank()) Text(eventDescription)
        Text("At: ${expense.date?.toUiDateTimeOrNull()}")
        Text("Created:  ${expense.createdAt?.toUiDateTimeOrNull()}")
        Text("Updated At: ${expense.updatedAt?.toUiDateTimeOrNull() ?: "Never"}")
    }
}

@Composable
fun ExpenseVenueSummary(state: ExpenseDetailComponent.UiState, onVenueClicked: (Long) -> Unit) {
    val eventVenue = state.venue
    if (eventVenue != null) {
        SectionContainer(
            title = "Info about the Venue",
            modifier = Modifier
                .wrapContentHeight()
                .clickable(true) { onVenueClicked(eventVenue.id) },
            content = {
                if (eventVenue.name.isNotBlank()) Text(eventVenue.name)
                Text("Address: ${eventVenue.address}")
                if (!eventVenue.description.isNullOrBlank()) Text(eventVenue.description.orEmpty())
                Text("Created:  ${eventVenue.createdAt?.toUiDateTimeOrNull()}")
                Text("Updated At: ${eventVenue.updatedAt?.toUiDateTimeOrNull()}")
            },
        )
    }
}

@Composable
fun ExpenseEventSummary(state: ExpenseDetailComponent.UiState, onEventClicked: (Long) -> Unit) {
    val event = state.event
    if (event != null) {
        SectionContainer(
            title = "Info about the Event",
            modifier = Modifier
                .wrapContentHeight()
                .clickable(true) { onEventClicked(event.id) },
            content = {
                val eventName = event.name
                val eventDescription = event.description
                Text(event.gameType.name)
                if (eventName.orEmpty().isNotBlank()) Text(eventName.orEmpty())
                if (eventDescription.isNullOrBlank()) Text(eventDescription.orEmpty())
            },
        )
    }
}
