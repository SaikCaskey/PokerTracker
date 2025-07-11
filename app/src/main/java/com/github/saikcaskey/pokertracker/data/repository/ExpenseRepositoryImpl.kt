package com.github.saikcaskey.pokertracker.data.repository

import app.cash.sqldelight.coroutines.*
import com.github.saikcaskey.pokertracker.data.mappers.toDomain
import com.github.saikcaskey.pokertracker.data.utils.atStartOfDayInstant
import com.github.saikcaskey.pokertracker.data.utils.nowAsInstant
import com.github.saikcaskey.pokertracker.data.utils.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class ExpenseRepositoryImpl(
    private val database: PokerTrackerDatabase,
    private val dispatchers: CoroutineDispatchers,
) : ExpenseRepository {

    override fun getAll(): Flow<List<Expense>> = database.expenseQueries.getAll()
        .asFlow()
        .mapToList(dispatchers.io)
        .map { expenses -> expenses.map { expense -> expense.toDomain() } }

    override fun getUpcomingCosts(): Flow<Double> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()
        return database.expenseQueries.getUpcomingCosts(tomorrow)
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getRecent(): Flow<List<Expense>> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()

        return database.expenseQueries.getRecent(tomorrow, 5)
            .asFlow()
            .mapToList(dispatchers.io)
            .map { expenses -> expenses.map { expense -> expense.toDomain() } }
    }

    override fun getById(eventId: Long): Flow<Expense> = database.expenseQueries.getById(eventId)
        .asFlow()
        .mapToOneOrNull(dispatchers.io)
        .mapNotNull { expense -> expense?.toDomain() }


    override fun getBalanceAllTime(): Flow<Double> {
        val now = nowAsLocalDateTime()
        val then =
            nowAsLocalDateTime().date.minus(DatePeriod(years = 30))
        return database.expenseQueries.getBalance(
            startDate = then.toString(),
            endDate = now.toString()
        )
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getBalanceForYear(): Flow<Double> {
        val now = nowAsLocalDateTime()
        val oneYearAgo = now.date.minus(DatePeriod(years = 1)).atStartOfDayInstant().toString()
        return database.expenseQueries.getBalance(
            startDate = oneYearAgo,
            endDate = now.toString(),
        )
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getBalanceForMonth(): Flow<Double> {
        val now = nowAsLocalDateTime()
        val oneMonthAgo = now.date.minus(DatePeriod(months = 1)).atStartOfDayInstant().toString()
        return database.expenseQueries.getBalance(
            startDate = oneMonthAgo,
            endDate = now.toString(),
        )
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .map { it?.balance ?: 0.0 }
    }

    override fun getEventBalance(eventId: Long): Flow<Double> =
        database.expenseQueries.getEventBalance(eventId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getEventCostSubtotal(eventId: Long): Flow<Double> =
        database.expenseQueries.getEventCostsSubtotal(eventId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getEventCashesSubtotal(eventId: Long): Flow<Double> =
        database.expenseQueries.getEventCashesSubtotal(eventId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getVenueBalance(venueId: Long): Flow<Double> =
        database.expenseQueries.getVenueBalance(venueId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }


    override fun getVenueCostSubtotal(venueId: Long): Flow<Double> =
        database.expenseQueries.getVenueCostsSubtotal(venueId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override fun getVenueCashesSubtotal(venueId: Long): Flow<Double> =
        database.expenseQueries.getVenueCashesSubtotal(venueId)
            .asFlow()
            .mapToOne(dispatchers.io)
            .map { it.balance ?: 0.0 }

    override suspend fun insert(
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String?,
        description: String?,
    ) {
        database.expenseQueries.insert(
            event_id = eventId,
            venue_id = venueId,
            type = type,
            amount = amount,
            description = description,
            date = date,
            created_at = Clock.System.now().toString()
        )
    }

    override suspend fun update(
        expenseId: Long,
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String?,
        description: String?,
    ) {
        database.expenseQueries.update(
            id = expenseId,
            event_id = eventId,
            venue_id = venueId,
            type = type,
            amount = amount,
            description = description,
            date = date,
            updated_at = nowAsInstant().toString()
        )
    }

    override fun getByEvent(eventId: Long): Flow<List<Expense>> {
        return database.expenseQueries.getByEvent(eventId)
            .asFlow()
            .mapToList(dispatchers.io)
            .map { expenses -> expenses.map { expense -> expense.toDomain() } }
    }


    override suspend fun deleteById(expenseId: Long) {
        database.expenseQueries.deleteById(expenseId)
    }

    override suspend fun deleteAll() {
        database.expenseQueries.deleteAll()
    }
}
