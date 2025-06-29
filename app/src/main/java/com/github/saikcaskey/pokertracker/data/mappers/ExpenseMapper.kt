package com.github.saikcaskey.pokertracker.data.mappers

import co.touchlab.kermit.Logger
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.data.utils.asInstantOrNow
import com.github.saikcaskey.pokertracker.data.utils.asInstantOrNull
import com.saikcaskey.github.pokertracker.shared.database.Expense
import com.github.saikcaskey.pokertracker.domain.models.Expense as DomainExpense

fun Expense.toDomain(): DomainExpense {
    Logger.i("asd expenseToDomain $date")
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
