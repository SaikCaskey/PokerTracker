package com.github.saikcaskey.pokertracker.libs.domain.repository

import com.github.saikcaskey.pokertracker.libs.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    fun getAll(): Flow<List<User>>
    fun getById(userId: Long): Flow<User?>
    suspend fun insert(name: String): Long
    suspend fun update(userId: Long, name: String)
    suspend fun deleteById(userId: Long)
    suspend fun deleteAll()
}
