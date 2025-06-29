package com.saikcaskey.github.pokertracker.shared.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAll(): Flow<List<Expense>>
    fun getRecent(): Flow<List<Expense>>
    fun getById(eventId: Long): Flow<Expense>
    fun getUpcomingCosts(): Flow<Double>
    fun getBalanceAllTime(): Flow<Double>
    fun getBalanceForYear(): Flow<Double>
    fun getBalanceForMonth(): Flow<Double>
    fun getByEvent(eventId: Long): Flow<List<Expense>>
    fun getEventBalance(eventId: Long): Flow<Double>
    fun getEventCostSubtotal(eventId: Long): Flow<Double>
    fun getEventCashesSubtotal(eventId: Long): Flow<Double>
    fun getVenueBalance(venueId: Long): Flow<Double>
    fun getVenueCostSubtotal(venueId: Long): Flow<Double>
    fun getVenueCashesSubtotal(venueId: Long): Flow<Double>
    suspend fun insert(
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String? = null,
        description: String? = null,
    )

    suspend fun update(
        expenseId: Long,
        eventId: Long?,
        venueId: Long?,
        amount: Double,
        type: String,
        date: String? = null,
        description: String? = null,
    )

    suspend fun deleteById(expenseId: Long)
    suspend fun deleteAll()
}
