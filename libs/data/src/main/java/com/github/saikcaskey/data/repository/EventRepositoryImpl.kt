package com.github.saikcaskey.data.repository

import app.cash.sqldelight.coroutines.*
import com.github.saikcaskey.data.mappers.toDomain
import com.github.saikcaskey.pokertracker.domain.extensions.atStartOfDayInstant
import com.github.saikcaskey.pokertracker.domain.util.atTimeInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.DEFAULT_LIMIT
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import com.github.saikcaskey.pokertracker.database.Event as DatabaseEvent

class EventRepositoryImpl(
    private val database: PokerTrackerDatabase,
    private val coroutineDispatchers: CoroutineDispatchers,
) : EventRepository {

    override fun getAll(): Flow<List<Event>> =
        database.eventQueries.getAll()
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }

    override fun getDaysWithEvents(): Flow<List<LocalDate>> {
        return database.eventQueries.getEventDates()
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { LocalDate.parse(it) } }
    }

    override fun getByDate(date: LocalDate): Flow<List<Event>> {
        return database.eventQueries.getByDate(date.toString())
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map(DatabaseEvent::toDomain) }
    }

    override fun getRecent(): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val today = now.date.atStartOfDayInstant().toString()

        return database.eventQueries.getRecent(today, DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getRecentByVenue(venueId: Long?): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.atStartOfDayInstant().toString()

        return database.eventQueries.getRecentByVenue(tomorrow, venueId, DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getUpcoming(): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()

        return database.eventQueries.getUpcoming(tomorrow, DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getUpcomingByVenue(venueId: Long?): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val startOfTomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()

        return database.eventQueries.getUpcomingByVenue(startOfTomorrow, venueId, DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getToday(): Flow<List<Event>> {
        return database.eventQueries.getDay(nowAsLocalDateTime().toString(), DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getTodayByVenue(venueId: Long?): Flow<List<Event>> {
        return database.eventQueries.getDayByVenue(
            nowAsLocalDateTime().toString(),
            venueId,
            DEFAULT_LIMIT
        )
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getById(eventId: Long): Flow<Event?> {
        return database.eventQueries.getById(eventId)
            .asFlow()
            .mapToOneOrNull(coroutineDispatchers.io)
            .filterNotNull()
            .map(DatabaseEvent::toDomain)
    }

    override fun getByVenue(venueId: Long): Flow<List<Event>> {
        return database.eventQueries.getByVenue(venueId)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { venues -> venues.map(DatabaseEvent::toDomain) }
    }

    override suspend fun insert(
        name: String,
        gameType: String,
        venueId: Long?,
        date: String?,
        time: String?,
        description: String?,
    ) {
        database.eventQueries.transaction {
            val parsedLocalDate = LocalDate.parse(date.orEmpty())
            val parsedTime = LocalTime.parse(time.orEmpty())
            database.eventQueries.insert(
                venue_id = venueId,
                name = name,
                date = parsedLocalDate.atTimeInstant(parsedTime).toString(),
                game_type = gameType,
                description = description,
                created_at = Clock.System.now().toString(),
            )
        }
    }

    override suspend fun update(
        id: Long,
        name: String,
        gameType: String,
        venueId: Long?,
        date: String?,
        time: String?,
        description: String?,
    ) {
        database.eventQueries.transaction {
            val parsedLocalDate = LocalDate.parse(date.orEmpty())
            val parsedTime = LocalTime.parse(time.orEmpty())

            database.eventQueries.update(
                id = id,
                venue_id = venueId,
                name = name,
                date = parsedLocalDate.atTimeInstant(parsedTime).toString(),
                game_type = gameType,
                description = description,
                updated_at = nowAsInstant().toString()
            )
        }
    }

    override suspend fun deleteById(eventId: Long) {
        database.eventQueries.deleteById(eventId)
    }

    override suspend fun deleteAll() {
        database.eventQueries.deleteAll()
    }
}
