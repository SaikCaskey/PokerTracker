package com.github.saikcaskey.pokertracker.libs.domain.dao

import com.github.saikcaskey.pokertracker.libs.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserDao {

    fun getAll(): Flow<List<User>>

    fun getById(userId: Long): Flow<User?>

    suspend fun insert(name: String)

    suspend fun update(userId: Long, name: String)

    suspend fun deleteById(userId: Long)

    suspend fun deleteAll()

}
