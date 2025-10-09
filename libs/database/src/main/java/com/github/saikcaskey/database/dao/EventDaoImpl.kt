package com.github.saikcaskey.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.DEFAULT_LIMIT
import com.github.saikcaskey.pokertracker.domain.dao.EventDao
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNull
import com.github.saikcaskey.pokertracker.domain.extensions.atStartOfDayInstant
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.models.GameType
import com.github.saikcaskey.pokertracker.domain.util.nowAsInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import com.github.saikcaskey.pokertracker.database.Event as DatabaseEvent

class EventDaoImpl(
    private val database: PokerTrackerDatabase,
    private val coroutineDispatchers: CoroutineDispatchers,
) : EventDao {

    override fun getAll(userId: Long): Flow<List<Event>> =
        database.eventQueries.getAll(userId = userId)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }

    override fun getDaysWithEvents(userId: Long): Flow<List<LocalDate>> {
        return database.eventQueries.getEventDates(userId = userId)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { LocalDate.parse(it) } }
    }

    override fun getByDate(userId: Long, date: LocalDate): Flow<List<Event>> {
        return database.eventQueries.getByDate(userId = userId, date.toString())
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map(DatabaseEvent::toDomain) }
    }

    override fun getRecent(userId: Long): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val today = now.date.atStartOfDayInstant().toString()

        return database.eventQueries.getRecent(userId = userId, today, DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getRecentByVenue(userId: Long, venueId: Long?): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.atStartOfDayInstant().toString()

        return database.eventQueries.getRecentByVenue(
            userId = userId,
            tomorrow,
            venueId,
            DEFAULT_LIMIT
        )
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getUpcoming(userId: Long): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val tomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()

        return database.eventQueries.getUpcoming(userId = userId, tomorrow, DEFAULT_LIMIT)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getUpcomingByVenue(userId: Long, venueId: Long?): Flow<List<Event>> {
        val now = nowAsLocalDateTime()
        val startOfTomorrow = now.date.plus(DatePeriod(days = 1)).atStartOfDayInstant().toString()

        return database.eventQueries.getUpcomingByVenue(
            userId = userId,
            startOfTomorrow,
            venueId,
            DEFAULT_LIMIT
        )
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getToday(userId: Long): Flow<List<Event>> {
        return database.eventQueries.getDay(
            userId = userId,
            nowAsLocalDateTime().toString(),
            DEFAULT_LIMIT
        )
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getTodayByVenue(userId: Long, venueId: Long?): Flow<List<Event>> {
        return database.eventQueries.getDayByVenue(
            userId = userId,
            day = nowAsLocalDateTime().toString(),
            id = venueId,
            limit = DEFAULT_LIMIT
        )
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { events -> events.map { event -> event.toDomain() } }
    }

    override fun getById(userId: Long, eventId: Long): Flow<Event?> {
        return database.eventQueries.getById(userId = userId, eventId = eventId)
            .asFlow()
            .mapToOneOrNull(coroutineDispatchers.io)
            .filterNotNull()
            .map(DatabaseEvent::toDomain)
    }

    override fun getByVenue(userId: Long, venueId: Long): Flow<List<Event>> {
        return database.eventQueries.getByVenue(userId = userId, venueId = venueId)
            .asFlow()
            .mapToList(coroutineDispatchers.io)
            .map { venues -> venues.map(DatabaseEvent::toDomain) }
    }

    override suspend fun insert(
        name: String,
        userId: Long,
        gameType: String,
        venueId: Long?,
        date: String?,
        time: String?,
        description: String?,
    ) {
        database.eventQueries.transaction {
            database.eventQueries.insert(
                user_id = userId,
                venue_id = venueId,
                name = name,
                date = date,
                game_type = gameType,
                description = description,
                created_at = nowAsInstant().toString(),
            )
        }
    }

    override suspend fun update(
        id: Long,
        userId: Long,
        name: String,
        gameType: String,
        venueId: Long?,
        date: String?,
        time: String?,
        description: String?,
    ) {
        database.eventQueries.transaction {
            database.eventQueries.update(
                id = id,
                user_id = userId,
                venue_id = venueId,
                name = name,
                date = date,
                game_type = gameType,
                description = description,
                updated_at = nowAsInstant().toString()
            )
        }
    }

    override suspend fun deleteById(userId: Long, eventId: Long) {
        database.eventQueries.deleteById(userId = userId, eventId = eventId)
    }

    override suspend fun deleteAll(userId: Long) {
        database.eventQueries.deleteAllByUserId(userId = userId)
    }
}

private fun DatabaseEvent.toDomain(): Event {
    return Event(
        id = id,
        venueId = venue_id,
        name = name,
        date = date.asInstantOrNull(),
        gameType = GameType.valueOf(game_type),
        description = description,
        createdAt = created_at.asInstantOrNull(),
        updatedAt = updated_at.asInstantOrNull(),
    )
}

