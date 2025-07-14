package com.github.saikcaskey.pokertracker.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventRepository {
    fun getAll(): Flow<List<Event>>
    fun getDaysWithEvents(): Flow<List<LocalDate>>
    fun getByDate(date: LocalDate): Flow<List<Event>>
    fun getRecent(): Flow<List<Event>>
    fun getUpcoming(): Flow<List<Event>>
    fun getById(eventId: Long): Flow<Event?>
    fun getToday(): Flow<List<Event>>
    fun getTodayByVenue(venueId: Long?): Flow<List<Event>>
    fun getByVenue(venueId: Long): Flow<List<Event>>
    fun getUpcomingByVenue(venueId: Long?): Flow<List<Event>>

    fun getRecentByVenue(venueId: Long?): Flow<List<Event>>

    suspend fun insert(
        name: String,
        gameType: String,
        venueId: Long? = null,
        date: String? = null,
        time: String? = null,
        description: String? = null,
    )

    suspend fun update(
        id: Long,
        name: String,
        gameType: String,
        venueId: Long? = null,
        date: String? = null,
        time: String? = null,
        description: String? = null,
    )

    suspend fun deleteById(eventId: Long)

    suspend fun deleteAll()
}
