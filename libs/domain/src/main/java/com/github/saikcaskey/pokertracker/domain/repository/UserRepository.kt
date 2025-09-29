package com.github.saikcaskey.pokertracker.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    fun getAll(): Flow<List<User>>
    fun getById(userId: Long): Flow<User?>
    suspend fun insert(name: String)
    suspend fun update(userId: Long, name: String)
    suspend fun deleteById(userId: Long)
    suspend fun deleteAll()
}
