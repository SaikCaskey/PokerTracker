package com.github.saikcaskey.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.dao.UserDao
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNull
import com.github.saikcaskey.pokertracker.domain.models.User
import com.github.saikcaskey.pokertracker.domain.util.nowAsInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import com.github.saikcaskey.pokertracker.database.User as DatabaseUser

class UserDaoImpl(
    private val database: PokerTrackerDatabase,
    private val dispatchers: CoroutineDispatchers,
) : UserDao {

    override fun getAll(): Flow<List<User>> {
        return database.userQueries.getAll()
            .asFlow()
            .mapToList(dispatchers.io)
            .mapNotNull { users -> users.map(DatabaseUser::toDomain) }
    }

    override fun getById(userId: Long): Flow<User?> {
        return database.userQueries.getById(userId)
            .asFlow()
            .mapToOneOrNull(dispatchers.io)
            .mapNotNull { it?.toDomain() }
    }

    override suspend fun insert(name: String) {
        database.userQueries.insert(name, nowAsInstant().toString())
    }

    override suspend fun update(userId: Long, name: String) {
        database.userQueries.update(
            name = name,
            updated_at = nowAsInstant().toString(),
            id = userId
        )
    }

    override suspend fun deleteById(userId: Long) {
        database.userQueries.deleteById(userId)
    }

    override suspend fun deleteAll() {
        database.userQueries.deleteAll()
    }
}

private fun DatabaseUser.toDomain(): User {
    return User(
        id = id,
        name = name,
        createdAt = created_at.asInstantOrNull(),
        updatedAt = updated_at.asInstantOrNull(),
    )
}

