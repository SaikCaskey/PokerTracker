package com.github.saikcaskey.data.mappers

import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNow
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNull
import com.github.saikcaskey.pokertracker.database.Expense
import com.github.saikcaskey.pokertracker.domain.models.Expense as DomainExpense

fun Expense.toDomain(): DomainExpense {
    return DomainExpense(
        id = id,
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
