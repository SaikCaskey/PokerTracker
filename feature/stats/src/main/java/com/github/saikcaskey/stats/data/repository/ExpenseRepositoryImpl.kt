package com.github.saikcaskey.stats.data.repository

import com.github.saikcaskey.pokertracker.domain.dao.ExpenseDao
import com.github.saikcaskey.pokertracker.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.domain.models.Expense
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.stats.extensions.flatMapWithUserId
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao,
    private val userDataSource: UserDataSource,
) : ExpenseRepository {

    override fun getAll(): Flow<List<Expense>> {
        return userDataSource.storedUser.flatMapWithUserId(expenseDao::getAll)
    }

    override fun getUpcomingCosts(): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId(expenseDao::getUpcomingCosts)
    }

    override fun getRecent(): Flow<List<Expense>> {
        return userDataSource.storedUser.flatMapWithUserId(expenseDao::getRecent)
    }

    override fun getByEvent(eventId: Long): Flow<List<Expense>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getByEvent(userId = userId, eventId = eventId)
        }
    }

    override fun getById(eventId: Long): Flow<Expense> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getById(userId = userId, eventId = eventId)
        }
    }

    override fun getByVenue(venueId: Long): Flow<List<Expense>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getByVenue(userId = userId, venueId = venueId)
        }
    }

    override fun getBalanceNow(): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId(expenseDao::getBalanceNow)
    }

    override fun getBalanceForYear(): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId(expenseDao::getBalanceForYear)
    }

    override fun getBalanceForMonth(): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId(expenseDao::getBalanceForMonth)
    }

    override fun getEventBalance(eventId: Long): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getEventBalance(userId = userId, eventId = eventId)
        }
    }

    override fun getEventCostSubtotal(eventId: Long): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getEventCostSubtotal(userId = userId, eventId = eventId)
        }
    }

    override fun getEventCashesSubtotal(eventId: Long): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getEventCashesSubtotal(userId = userId, eventId = eventId)
        }
    }

    override fun getVenueBalance(venueId: Long): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getVenueBalance(userId = userId, venueId = venueId)
        }
    }

    override fun getVenueCostSubtotal(venueId: Long): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getVenueCostSubtotal(userId = userId, venueId = venueId)
        }
    }

    override fun getVenueCashesSubtotal(venueId: Long): Flow<Double> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            expenseDao.getVenueCashesSubtotal(userId = userId, venueId = venueId)
        }
    }

    override suspend fun insert(
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: ExpenseType,
        date: String?,
        description: String?,
    ) {
        expenseDao.insert(
            userId = userDataSource.storedUser.value?.id ?: return,
            eventId = eventId,
            venueId = venueId,
            type = type.name,
            amount = amount.adjustedForType(type),
            description = description,
            date = date,
        )
    }

    override suspend fun update(
        expenseId: Long,
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: ExpenseType,
        date: String?,
        description: String?,
    ) {
        expenseDao.update(
            userId = userDataSource.storedUser.value?.id ?: return,
            expenseId = expenseId,
            eventId = eventId,
            venueId = venueId,
            type = type.name,
            amount = amount.adjustedForType(type),
            description = description,
            date = date,
        )
    }

    override suspend fun deleteById(expenseId: Long) {
        expenseDao.deleteById(
            userId = userDataSource.storedUser.value?.id ?: return,
            expenseId = expenseId
        )
    }

    override suspend fun deleteAll() {
        expenseDao.deleteAll(userId = userDataSource.storedUser.value?.id ?: return)
    }
}

private fun Double.adjustedForType(expenseType: ExpenseType): Double {
    return if (expenseType == ExpenseType.CASH_OUT || expenseType == ExpenseType.DEAL) {
        this
    } else {
        abs(this) * -1
    }
}
