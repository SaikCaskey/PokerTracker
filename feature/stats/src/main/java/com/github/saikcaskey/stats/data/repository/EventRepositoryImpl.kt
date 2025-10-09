package com.github.saikcaskey.stats.data.repository

import com.github.saikcaskey.pokertracker.domain.dao.EventDao
import com.github.saikcaskey.pokertracker.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.domain.models.Event
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.stats.ext.flatMapWithUserId
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class EventRepositoryImpl(
    private val eventDao: EventDao,
    private val userDataSource: UserDataSource,
) : EventRepository {

    override fun getAll(): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId(eventDao::getAll)
    }

    override fun getDaysWithEvents(): Flow<List<LocalDate>> {
        return userDataSource.storedUser.flatMapWithUserId(eventDao::getDaysWithEvents)
    }

    override fun getByDate(date: LocalDate): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            eventDao.getByDate(userId = userId, date = date)
        }
    }

    override fun getRecent(): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId(eventDao::getRecent)
    }

    override fun getRecentByVenue(venueId: Long?): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            eventDao.getRecentByVenue(userId = userId, venueId = venueId)
        }
    }

    override fun getUpcoming(): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId(eventDao::getUpcoming)
    }

    override fun getUpcomingByVenue(venueId: Long?): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            eventDao.getUpcomingByVenue(userId = userId, venueId = venueId)
        }
    }

    override fun getToday(): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId(eventDao::getToday)
    }

    override fun getTodayByVenue(venueId: Long?): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            eventDao.getTodayByVenue(userId = userId, venueId = venueId)
        }
    }

    override fun getById(eventId: Long): Flow<Event?> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            eventDao.getById(userId = userId, eventId = eventId)
        }
    }

    override fun getByVenue(venueId: Long): Flow<List<Event>> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            eventDao.getByVenue(userId = userId, venueId = venueId)
        }
    }

    override suspend fun insert(
        name: String,
        gameType: String,
        venueId: Long?,
        date: String?,
        description: String?,
    ) {
        eventDao.insert(
            userId = userDataSource.storedUser.value?.id ?: return,
            venueId = venueId,
            name = name,
            date = date,
            gameType = gameType,
            description = description,
        )
    }

    override suspend fun update(
        id: Long,
        name: String,
        gameType: String,
        venueId: Long?,
        date: String?,
        description: String?,
    ) {
        eventDao.update(
            id = id,
            userId = userDataSource.storedUser.value?.id ?: return,
            venueId = venueId,
            name = name,
            date = date,
            gameType = gameType,
            description = description,
        )
    }

    override suspend fun deleteById(eventId: Long) {
        eventDao.deleteById(userDataSource.storedUser.value?.id ?: return, eventId)
    }

    override suspend fun deleteAll() {
        eventDao.deleteAll(userDataSource.storedUser.value?.id ?: return)
    }
}
