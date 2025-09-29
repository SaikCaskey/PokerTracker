package com.github.saikcaskey.pokertracker.domain.dao

import com.github.saikcaskey.pokertracker.domain.models.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventDao {
    fun getAll(userId: Long): Flow<List<Event>>
    fun getDaysWithEvents(userId: Long): Flow<List<LocalDate>>
    fun getByDate(userId: Long, date: LocalDate): Flow<List<Event>>
    fun getRecent(userId: Long): Flow<List<Event>>
    fun getUpcoming(userId: Long): Flow<List<Event>>
    fun getById(userId: Long, eventId: Long): Flow<Event?>
    fun getToday(userId: Long): Flow<List<Event>>
    fun getTodayByVenue(userId: Long, venueId: Long?): Flow<List<Event>>
    fun getByVenue(userId: Long, venueId: Long): Flow<List<Event>>
    fun getUpcomingByVenue(userId: Long, venueId: Long?): Flow<List<Event>>

    fun getRecentByVenue(userId: Long, venueId: Long?): Flow<List<Event>>

    suspend fun insert(
        name: String,
        userId: Long,
        gameType: String,
        venueId: Long? = null,
        date: String? = null,
        time: String? = null,
        description: String? = null,
    )

    suspend fun update(
        id: Long,
        userId: Long,
        name: String,
        gameType: String,
        venueId: Long? = null,
        date: String? = null,
        time: String? = null,
        description: String? = null,
    )

    suspend fun deleteById(userId: Long, eventId: Long)

    suspend fun deleteAll(userId: Long)
}
