package com.github.saikcaskey.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneNotNull
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.dao.VenueDao
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNow
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNull
import com.github.saikcaskey.pokertracker.domain.models.Venue
import com.github.saikcaskey.pokertracker.domain.util.nowAsInstant
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.github.saikcaskey.pokertracker.database.Venue as DatabaseVenue

class VenueDaoImpl(
    private val database: PokerTrackerDatabase,
    private val dispatchers: CoroutineDispatchers,
) : VenueDao {

    override fun getAll(userId: Long): Flow<List<Venue>> = database.venueQueries.getAll(userId)
        .asFlow()
        .mapToList(dispatchers.io)
        .map { list -> list.map(DatabaseVenue::toDomain) }

    override fun getRecent(userId: Long): Flow<List<Venue>> {
        val now = nowAsLocalDateTime()
        val today = now.date.toString()

        return database.venueQueries.getRecent(
            userId = userId,
            beforeDate = today,
            limit = 5
        )
            .asFlow()
            .mapToList(dispatchers.io)
            .map { list -> list.map(DatabaseVenue::toDomain) }
    }

    override fun getById(userId: Long, venueId: Long): Flow<Venue> {
        return database.venueQueries.getById(userId = userId, venueId)
            .asFlow()
            .mapToOneNotNull(dispatchers.io)
            .map(DatabaseVenue::toDomain)
    }

    override suspend fun insert(
        userId: Long,
        name: String,
        address: String,
        description: String,
    ) {
        database.venueQueries.insert(
            user_id = userId,
            name = name,
            address = address,
            description = description,
            created_at = nowAsInstant().toString(),
        )
    }

    override suspend fun update(
        venueId: Long,
        userId: Long,
        name: String,
        address: String,
        description: String,
    ) {
        database.venueQueries.update(
            user_id = userId,
            id = venueId,
            name = name,
            address = address,
            description = description,
            updated_at = nowAsInstant().toString(),
        )
    }

    override suspend fun deleteById(userId: Long, venueId: Long) {
        database.venueQueries.deleteById(userId = userId, venueId)
    }

    override suspend fun deleteAll(userId: Long) {
        database.venueQueries.deleteAllByUserId(userId = userId)
    }
}

private fun DatabaseVenue.toDomain() = Venue(
    id = id,
    name = name,
    address = address,
    description = description,
    createdAt = created_at.asInstantOrNull(),
    updatedAt = updated_at.asInstantOrNull()
)
