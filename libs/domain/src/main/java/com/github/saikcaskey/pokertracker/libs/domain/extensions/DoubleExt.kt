package com.github.saikcaskey.pokertracker.libs.domain.extensions

import com.github.saikcaskey.pokertracker.libs.domain.models.ExpenseType
import kotlin.math.abs

fun Double.formatAsCurrency(symbol: String = ""): String {
    val rounded = (this * 100).toInt() / 100.0 // round to 2 decimal places
    val parts = rounded.toString().split(".")
    val whole = parts[0]
    val decimal = parts.getOrNull(1)?.padEnd(2, '0') ?: "00"
    return "${symbol}$whole.$decimal"
}

fun Double.adjustedForType(expenseType: ExpenseType): Double {
    val adjustedAmount = abs(this)
    return if (expenseType == ExpenseType.CASH_OUT || expenseType == ExpenseType.DEAL) {
        adjustedAmount
    } else {
        -(adjustedAmount)
    }
}
