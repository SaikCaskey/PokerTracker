package com.github.saikcaskey.pokertracker.ui_compose.components.expense

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.util.toUiDateTimeOrNull
import com.github.saikcaskey.pokertracker.ui_compose.common.profitsummary.AnimatedExpenseText
import com.github.saikcaskey.pokertracker.ui_compose.common.section.SectionListContainer

@Composable
fun ExpenseList(items: List<Expense>, onExpenseClicked: (Long) -> Unit) {
    SectionListContainer(
        items = items,
        onItemClicked = { onExpenseClicked(it.id) },
    ) { expense ->
        AnimatedExpenseText(expense, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = expense.description?.take(50).orEmpty(),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = "Created:  ${expense.createdAt?.toUiDateTimeOrNull().orEmpty()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
