package com.github.saikcaskey.pokertracker.domain.dao

import com.github.saikcaskey.pokertracker.domain.models.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseDao {
    fun getAll(userId: Long): Flow<List<Expense>>
    fun getRecent(userId: Long): Flow<List<Expense>>
    fun getById(userId: Long, eventId: Long): Flow<Expense>
    fun getUpcomingCosts(userId: Long): Flow<Double>
    fun getBalanceNow(userId: Long): Flow<Double>
    fun getBalanceForYear(userId: Long): Flow<Double>
    fun getBalanceForMonth(userId: Long): Flow<Double>
    fun getByEvent(userId: Long, eventId: Long): Flow<List<Expense>>
    fun getEventBalance(userId: Long, eventId: Long): Flow<Double>
    fun getEventCostSubtotal(userId: Long, eventId: Long): Flow<Double>
    fun getEventCashesSubtotal(userId: Long, eventId: Long): Flow<Double>
    fun getVenueBalance(userId: Long, venueId: Long): Flow<Double>
    fun getVenueCostSubtotal(userId: Long, venueId: Long): Flow<Double>
    fun getVenueCashesSubtotal(userId: Long, venueId: Long): Flow<Double>

    suspend fun insert(
        eventId: Long?,
        userId: Long,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String? = null,
        description: String? = null,
    ): Long

    suspend fun update(
        expenseId: Long,
        userId: Long,
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String? = null,
        description: String? = null,
    ): Long

    suspend fun deleteById(userId: Long, expenseId: Long)
    suspend fun deleteAll(userId: Long)
}
