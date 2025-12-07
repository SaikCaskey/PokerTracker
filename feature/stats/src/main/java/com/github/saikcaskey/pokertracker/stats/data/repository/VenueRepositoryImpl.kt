package com.github.saikcaskey.pokertracker.stats.data.repository

import com.github.saikcaskey.pokertracker.libs.domain.dao.VenueDao
import com.github.saikcaskey.pokertracker.libs.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.libs.domain.models.Venue
import com.github.saikcaskey.pokertracker.libs.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.stats.data.extensions.flatMapWithUserId
import kotlinx.coroutines.flow.Flow

class VenueRepositoryImpl(
    private val venueDao: VenueDao,
    private val userDataSource: UserDataSource,
) : VenueRepository {

    override fun getAll(): Flow<List<Venue>> {
        return userDataSource.storedUser.flatMapWithUserId(venueDao::getAll)
    }

    override fun getRecent(): Flow<List<Venue>> {
        return userDataSource.storedUser.flatMapWithUserId(venueDao::getRecent)
    }

    override fun getById(venueId: Long): Flow<Venue> {
        return userDataSource.storedUser.flatMapWithUserId { userId ->
            venueDao.getById(userId = userId, venueId = venueId)
        }
    }

    override suspend fun insert(
        name: String,
        address: String,
        description: String,
    ) {
        venueDao.insert(
            userId = userDataSource.storedUser.value?.id ?: return,
            name = name,
            address = address,
            description = description,
        )
    }

    override suspend fun update(
        venueId: Long,
        name: String,
        address: String,
        description: String,
    ) {
        venueDao.update(
            userId = userDataSource.storedUser.value?.id ?: return,
            venueId = venueId,
            name = name,
            address = address,
            description = description,
        )
    }

    override suspend fun deleteById(venueId: Long) {
        venueDao.deleteById(userId = userDataSource.storedUser.value?.id ?: return, venueId)
    }

    override suspend fun deleteAll() {
        venueDao.deleteAll(userId = userDataSource.storedUser.value?.id ?: return)
    }
}
