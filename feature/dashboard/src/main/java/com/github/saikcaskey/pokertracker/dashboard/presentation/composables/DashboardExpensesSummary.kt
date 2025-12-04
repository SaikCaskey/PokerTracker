package com.github.saikcaskey.pokertracker.dashboard.presentation.composables

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ExpenseList

@Composable
fun DashboardExpensesSummary(
    state: DashboardFeatureComponent.UiState,
    onShowAllExpensesClicked: () -> Unit,
    onShowInsertExpenseClicked: () -> Unit,
    onShowExpenseDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Expenses",
        onAction1Click = onShowInsertExpenseClicked,
        action1Label = "Add",
        onAction2Click = onShowAllExpensesClicked,
        action2Label = "Show All",
    ) {
        ExpenseList(
            items = state.recentExpenses,
            onExpenseClicked = onShowExpenseDetailClicked,
        )
    }
}
