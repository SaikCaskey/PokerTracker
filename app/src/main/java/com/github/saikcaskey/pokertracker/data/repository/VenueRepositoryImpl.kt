package com.github.saikcaskey.pokertracker.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneNotNull
import com.github.saikcaskey.pokertracker.data.mappers.toDomain
import com.github.saikcaskey.pokertracker.domain.util.nowAsInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import com.github.saikcaskey.pokertracker.database.Venue as DatabaseVenue

class VenueRepositoryImpl(
    private val database: PokerTrackerDatabase,
    private val dispatchers: CoroutineDispatchers,
) : VenueRepository {

    override fun getAll(): Flow<List<Venue>> = database.venueQueries.getAll()
        .asFlow()
        .mapToList(dispatchers.io)
        .map { list -> list.map(DatabaseVenue::toDomain) }

    override fun getRecent(): Flow<List<Venue>> {
        val now = nowAsLocalDateTime()
        val today = now.date.toString()

        return database.venueQueries.getRecent(
            beforeDate = today,
            limit = 5
        )
            .asFlow()
            .mapToList(dispatchers.io)
            .map { list -> list.map(DatabaseVenue::toDomain) }
    }

    override fun getById(venueId: Long): Flow<Venue> = database.venueQueries.getById(venueId)
        .asFlow()
        .mapToOneNotNull(dispatchers.io)
        .map(DatabaseVenue::toDomain)

    override suspend fun insert(name: String, address: String, description: String) {
        database.venueQueries.insert(
            name = name,
            address = address,
            description = description,
            created_at = Clock.System.now().toString()
        )
    }

    override suspend fun update(
        venueId: Long,
        name: String,
        address: String,
        description: String,
    ) {
        database.venueQueries.update(
            id = venueId,
            name = name,
            address = address,
            description = description,
            updated_at = nowAsInstant().toString(),
        )
    }

    override suspend fun deleteById(venueId: Long) {
        database.venueQueries.deleteById(venueId)
    }

    override suspend fun deleteAll() {
        database.venueQueries.deleteAll()
    }
}
