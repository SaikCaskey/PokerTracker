package com.github.saikcaskey.pokertracker.ui_compose.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType

@Composable
fun Expense.toExpenseColor(): Color {
    return when {
        type == ExpenseType.CASH_OUT || type == ExpenseType.DEAL -> MaterialTheme.colorScheme.primary
        amount == 0.0 -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.error
    }
}
