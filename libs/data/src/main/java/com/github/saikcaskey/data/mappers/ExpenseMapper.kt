package com.github.saikcaskey.data.mappers

import com.github.saikcaskey.pokertracker.domain.util.asInstantOrNow
import com.github.saikcaskey.pokertracker.domain.util.asInstantOrNull
import com.github.saikcaskey.pokertracker.database.Expense
import com.github.saikcaskey.pokertracker.domain.models.Expense as DomainExpense

fun Expense.toDomain(): DomainExpense {
    return DomainExpense(
        eventId = event_id,
        venueId = venue_id,
        type = ExpenseType.valueOf(type),
        amount = amount,
        description = description,
        date = date.asInstantOrNow(),
        createdAt = created_at.asInstantOrNow(),
        updatedAt = updated_at.asInstantOrNull()
    )
}
