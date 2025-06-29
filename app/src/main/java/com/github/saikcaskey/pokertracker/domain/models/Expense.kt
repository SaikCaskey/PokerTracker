package com.github.saikcaskey.pokertracker.domain.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Instant

data class Expense(
    val id: Long,
    val venueId: Long?,
    val eventId: Long?,
    val type: ExpenseType,
    val amount: Double,
    val description: String?,
    val date: Instant? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)

val Expense.adjustedAmount: Double
    get() = when (type) {
        ExpenseType.CASH_OUT, ExpenseType.DEAL -> amount
        else -> -amount
    }

val Expense.prettyName: String
    get() = type.name
        .replace("_", " ")
        .lowercase()
        .replaceFirstChar(Char::uppercase)

@Composable
fun Expense.toExpenseColor(): Color {
    return when {
        type == ExpenseType.CASH_OUT || type == ExpenseType.DEAL -> MaterialTheme.colorScheme.primary
        amount == 0.0 -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.error
    }
}

@Composable
fun Double.toProfitColor(): Color {
    return when {
        this > 0.0 -> MaterialTheme.colorScheme.primary
        this < 0.0 -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }
}
