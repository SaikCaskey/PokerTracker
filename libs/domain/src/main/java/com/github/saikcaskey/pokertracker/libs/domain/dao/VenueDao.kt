package com.github.saikcaskey.pokertracker.libs.domain.dao

import com.github.saikcaskey.pokertracker.libs.domain.models.Venue
import kotlinx.coroutines.flow.Flow

interface VenueDao {
    fun getAll(userId: Long): Flow<List<Venue>>
    fun getRecent(userId: Long): Flow<List<Venue>>
    fun getById(userId: Long, venueId: Long): Flow<Venue>
    suspend fun insert(userId: Long, name: String, address: String, description: String)
    suspend fun update(
        venueId: Long,
        userId: Long,
        name: String,
        address: String,
        description: String,
    )

    suspend fun deleteById(userId: Long, venueId: Long)
    suspend fun deleteAll(userId: Long)
}
