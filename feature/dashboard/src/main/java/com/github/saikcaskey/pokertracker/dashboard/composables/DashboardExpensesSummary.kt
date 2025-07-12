package com.github.saikcaskey.pokertracker.dashboard.composables

import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionContainer

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
        DashboardExpensesList(
            items = recentExpenses,
            onExpenseClicked = onShowExpenseDetailClicked,
        )
    }
}
