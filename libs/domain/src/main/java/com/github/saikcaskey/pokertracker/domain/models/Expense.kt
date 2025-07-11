package com.github.saikcaskey.pokertracker.domain.models

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
