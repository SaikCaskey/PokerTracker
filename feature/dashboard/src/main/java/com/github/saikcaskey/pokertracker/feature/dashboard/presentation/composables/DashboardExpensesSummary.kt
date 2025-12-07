package com.github.saikcaskey.pokertracker.feature.dashboard.presentation.composables

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.libs.ui_compose.common.section.SectionContainer
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ExpenseList
import com.github.saikcaskey.pokertracker.ui_compose.extensions.AsIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChartLine

@Composable
fun DashboardExpensesSummary(
    state: DashboardFeatureComponent.UiState,
    onShowAllExpensesClicked: () -> Unit,
    onShowExpenseDetailClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Expenses",
        action = {
            IconButton(onClick = onShowAllExpensesClicked) {
                FontAwesomeIcons.Solid.ChartLine.AsIcon(24.dp, "Show expenses",)
            }
        },
        onClick = onShowAllExpensesClicked,
    ) {
        ExpenseList(
            items = state.recentExpenses,
            onExpenseClicked = onShowExpenseDetailClicked,
        )
    }
}
