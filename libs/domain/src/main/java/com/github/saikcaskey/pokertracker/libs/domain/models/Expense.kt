package com.github.saikcaskey.pokertracker.libs.domain.models

import com.github.saikcaskey.pokertracker.libs.domain.extensions.adjustedForType
import kotlin.time.Instant

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
) {
    val prettyName: String
        get() = type.name
            .replace("_", " ")
            .lowercase()
            .replaceFirstChar(Char::uppercase)

    val adjustedAmount get() = amount.adjustedForType(type)
}
