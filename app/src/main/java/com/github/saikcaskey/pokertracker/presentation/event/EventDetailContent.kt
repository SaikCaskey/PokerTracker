@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.saikcaskey.pokertracker.presentation.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.models.prettyName
import com.github.saikcaskey.pokertracker.domain.models.toProfitColor
import com.github.saikcaskey.pokertracker.presentation.common.appbar.TopBarItemDetail
import com.github.saikcaskey.pokertracker.presentation.common.profitsummary.AnimatedProfitText
import com.github.saikcaskey.pokertracker.presentation.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.presentation.common.section.SectionListContainer
import com.github.saikcaskey.pokertracker.data.utils.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.presentation.common.profitsummary.AnimatedExpenseText
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle

@Composable
internal fun EventDetailContent(modifier: Modifier = Modifier, component: EventDetailComponent) {
    val state by component.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarItemDetail(
                title = "Event Detail",
                onDeleteClicked = component::onDeleteEventClicked,
                onBackClicked = component::onBackClicked,
                onEditClicked = component::onShowEditEventClicked
            )
        },
        floatingActionButton = {
            FloatingActionButton(component::onShowInsertExpenseClicked) {
                Icon(
                    modifier = Modifier.height(32.dp),
                    imageVector = FontAwesomeIcons.Solid.PlusCircle,
                    contentDescription = "Add expense"
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
            item { EventDetailSummary(state = state) }
            item { EventProfitSummary(state = state) }
            state.venue?.let {
                item {
                    EventVenueSummary(
                        it,
                        onVenueClicked = component::onShowVenueDetailClicked
                    )
                }
            }
            item {
                EventExpensesList(
                    expenses = state.expenses,
                    onExpenseClicked = component::onShowExpenseDetailClicked
                )
            }
        }
    }
}

@Composable
fun EventExpensesList(expenses: List<Expense>, onExpenseClicked: (Long) -> Unit) {
    SectionContainer(
        title = "Expenses at this Event",
        content = {
            SectionListContainer(
                items = expenses.reversed(),
                onItemClicked = { onExpenseClicked(it.id) },
                maxHeight = 350.dp,
                emptyMessage = "No expenses logged yet",
                adjustHeight = false,
                limit = null,
                listItemContent = { expense -> ExpenseListItem(expense) }
            )
        }
    )
}

@Composable
fun ExpenseListItem(expense: Expense) {
    val expenseDescription = expense.description
    AnimatedExpenseText(expense, MaterialTheme.typography.bodyLarge)
    Text(expense.prettyName)
    if (!expenseDescription.isNullOrBlank()) Text(expenseDescription)
    Text("At: ${expense.date?.toUiDateTimeOrNull()}")
    Text("Created:  ${expense.createdAt?.toUiDateTimeOrNull()}")
    Text("Updated At: ${expense.updatedAt?.toUiDateTimeOrNull() ?: "Never"}")
}

@Composable
fun EventDetailSummary(state: EventDetailComponent.UiState) {
    SectionContainer(
        title = "",
        modifier = Modifier.fillMaxHeight(),
        content = {
            Text(state.event?.name.orEmpty(), style = MaterialTheme.typography.displaySmall)
            Text("Type: ${state.event?.gameType}")
            Text("At: ${state.event?.date?.toUiDateTimeOrNull()}")
            if (!state.event?.description.isNullOrBlank()) {
                Text(state.event.description)
            }
            Text("Created:  ${state.event?.createdAt?.toUiDateTimeOrNull()}")
            Text("Updated At: ${state.event?.updatedAt?.toUiDateTimeOrNull() ?: "Never"}")
        },
    )
}

@Composable
fun EventVenueSummary(venue: Venue, onVenueClicked: (Long) -> Unit) {
    SectionContainer(
        modifier = Modifier
            .fillMaxSize()
            .clickable(true) { onVenueClicked(venue.id) },
        title = "Info about the Venue",
        content = {
            Text(venue.name)
            Text("Address: ${venue.address}")
            if (!venue.description.isNullOrBlank()) Text("Description: ${venue.description}")
            Text("Created:  ${venue.createdAt?.toUiDateTimeOrNull()}")
            Text("Updated At: ${venue.updatedAt?.toUiDateTimeOrNull() ?: "Never"}")
        },
    )
}

@Composable
fun EventProfitSummary(
    modifier: Modifier = Modifier,
    state: EventDetailComponent.UiState,
    onVenueClicked: ((Long) -> Unit)? = null,
) {
    SectionContainer(
        title = "Event Cashflow",
        modifier = modifier
            .fillMaxSize()
            .clickable(state.event?.venueId != null) {
                state.event?.venueId?.let { onVenueClicked?.invoke(it) }
            },
        content = {
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
        },
    )
}
