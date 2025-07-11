package com.github.saikcaskey.pokertracker.ui_compose.components.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.domain.components.ViewExpensesComponent
import com.github.saikcaskey.pokertracker.ui_compose.common.appbar.TopAppBarItemViewer
import com.github.saikcaskey.pokertracker.ui_compose.common.inputform.InputDropdownSimple
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedExpenseText

@Composable
fun ViewExpensesContent(component: ViewExpensesComponent) {
    val uiState by component.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarItemViewer(
                "All Expenses",
                onBackClicked = component::onBackClicked,
                onShowInsertItemClicked = component::onShowInsertExpenseClicked,
                onDeleteAllItemsClicked = component::onDeleteAllExpensesClicked,
            )
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchFilter.query.orEmpty(),
                onValueChange = component::onSearchQueryChanged,
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputDropdownSimple(
                selected = uiState.searchFilter.sort,
                entries = ViewExpensesComponent.ExpenseSortOption.entries,
                onSelected = component::onFilterOptionChanged
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(uiState.filtered) { expense ->
                    ListItem(
                        headlineContent = { AnimatedExpenseText(expense) },
                        supportingContent = { Text(expense.createdAt.toString()) },
                        modifier = Modifier.clickable { component.onShowExpenseDetailClicked(expense.id) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
