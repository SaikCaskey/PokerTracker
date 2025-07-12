package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ExpenseList

@Composable
fun DashboardExpensesSummary(
    recentExpenses: List<Expense>,
    onShowAllExpensesClicked: () -> Unit,
    onShowInsertExpenseClicked: () -> Unit,
    onShowExpenseDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Expenses",
        onAddClick = onShowInsertExpenseClicked,
        onShowAllClick = onShowAllExpensesClicked,
    ) {
        ExpenseList(
            items = recentExpenses,
            onExpenseClicked = onShowExpenseDetailClicked,
        )
    }
}
