package com.github.saikcaskey.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.dao.ExpenseDao
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNull
import com.github.saikcaskey.pokertracker.domain.extensions.atStartOfDayInstant
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.util.nowAsInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import com.github.saikcaskey.pokertracker.database.Expense as DatabaseExpense

class ExpenseDaoImpl(
    private val database: PokerTrackerDatabase,
    private val dispatchers: CoroutineDispatchers,
) : ExpenseDao {

    override fun getAll(userId: Long): Flow<List<Expense>> = database.expenseQueries.getAll(userId)
        .asFlow()
        .mapToList(dispatchers.io)
        .map { expenses -> expenses.map(DatabaseExpense::toDomain) }

    override fun getUpcomingCosts(userId: Long): Flow<Double> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()
        return database.expenseQueries.getUpcomingCosts(userId = userId, tomorrow)
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getRecent(userId: Long): Flow<List<Expense>> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()

        return database.expenseQueries.getRecent(userId = userId, tomorrow, 5)
            .asFlow()
            .mapToList(dispatchers.io)
            .map { expenses -> expenses.map(DatabaseExpense::toDomain) }
    }

    override fun getById(userId: Long, eventId: Long): Flow<Expense> =
        database.expenseQueries.getById(userId = userId, eventId)
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .mapNotNull { expense -> expense?.toDomain() }


    override fun getBalanceNow(userId: Long): Flow<Double> {
        val now = nowAsLocalDateTime()
        val then = nowAsLocalDateTime().date.minus(DatePeriod(years = 30))
        return database.expenseQueries.getBalance(
            userId = userId,
            startDate = then.toString(),
            endDate = now.toString()
        )
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getBalanceForYear(userId: Long): Flow<Double> {
        val now = nowAsLocalDateTime()
        val oneYearAgo = now.date.minus(DatePeriod(years = 1)).atStartOfDayInstant().toString()
        return database.expenseQueries.getBalance(
            userId = userId,
            startDate = oneYearAgo,
            endDate = now.toString(),
        )
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getBalanceForMonth(userId: Long): Flow<Double> {
        val now = nowAsLocalDateTime()
        val oneMonthAgo = now.date.minus(DatePeriod(months = 1)).atStartOfDayInstant().toString()
        return database.expenseQueries.getBalance(
            userId = userId,
            startDate = oneMonthAgo,
            endDate = now.toString(),
        )
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getEventBalance(userId: Long, eventId: Long): Flow<Double> =
        database.expenseQueries.getEventBalance(userId = userId, eventId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getEventCostSubtotal(userId: Long, eventId: Long): Flow<Double> =
        database.expenseQueries.getEventCostsSubtotal(userId = userId, eventId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getEventCashesSubtotal(userId: Long, eventId: Long): Flow<Double> =
        database.expenseQueries.getEventCashesSubtotal(userId = userId, eventId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getVenueBalance(userId: Long, venueId: Long): Flow<Double> =
        database.expenseQueries.getVenueBalance(userId = userId, venueId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }


    override fun getVenueCostSubtotal(userId: Long, venueId: Long): Flow<Double> =
        database.expenseQueries.getVenueCostsSubtotal(userId = userId, venueId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getVenueCashesSubtotal(userId: Long, venueId: Long): Flow<Double> =
        database.expenseQueries.getVenueCashesSubtotal(userId = userId, venueId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override suspend fun insert(
        eventId: Long?,
        userId: Long,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String?,
        description: String?,
    ): Long {
        val result = database.expenseQueries.insert(
            event_id = eventId,
            user_id = userId,
            venue_id = venueId,
            type = type,
            amount = amount,
            description = description,
            date = date,
            created_at = nowAsInstant().toString(),
        )
        return result.value
    }

    override suspend fun update(
        expenseId: Long,
        userId: Long,
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String?,
        description: String?,
    ): Long {
        val result = database.expenseQueries.update(
            id = expenseId,
            event_id = eventId,
            user_id = userId,
            venue_id = venueId,
            type = type,
            amount = amount,
            description = description,
            date = date,
            updated_at = nowAsInstant().toString()
        )
        return result.value
    }

    override fun getByEvent(userId: Long, eventId: Long): Flow<List<Expense>> {
        return database.expenseQueries.getByEvent(userId = userId, eventId)
            .asFlow()
            .mapToList(dispatchers.io)
            .map { expenses -> expenses.map { expense -> expense.toDomain() } }
    }

    override suspend fun deleteById(userId: Long, expenseId: Long) {
        database.expenseQueries.deleteById(userId = userId, expenseId)
    }

    override suspend fun deleteAll(userId: Long) {
        database.expenseQueries.deleteAllByUserId(userId = userId)
    }
}

private fun DatabaseExpense.toDomain(): Expense {
    return Expense(
        id = id,
        eventId = event_id,
        venueId = venue_id,
        type = ExpenseType.valueOf(type),
        amount = amount,
        description = description,
        date = date.asInstantOrNull(),
        createdAt = created_at.asInstantOrNull(),
        updatedAt = updated_at.asInstantOrNull(),
    )
}
