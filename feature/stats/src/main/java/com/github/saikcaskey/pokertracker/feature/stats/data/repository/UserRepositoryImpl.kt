package com.github.saikcaskey.pokertracker.feature.stats.data.repository

import com.github.saikcaskey.pokertracker.feature.stats.data.extensions.flatMapWithUserId
import com.github.saikcaskey.pokertracker.libs.domain.dao.UserDao
import com.github.saikcaskey.pokertracker.libs.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.libs.domain.models.User
import com.github.saikcaskey.pokertracker.libs.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userDataSource: UserDataSource,
) : UserRepository {

    override fun getCurrentUser(): Flow<User?> {
        return userDataSource.storedUser.flatMapWithUserId(userDao::getById)
    }

    override fun getAll(): Flow<List<User>> {
        return userDao.getAll()
    }

    override fun getById(userId: Long): Flow<User?> {
        return userDao.getById(userId = userId)
    }

    override suspend fun insert(name: String): Long {
        userDao.insert(name = name)
        return userDao.lastInsertRowId()
    }

    override suspend fun update(userId: Long, name: String) {
        userDao.update(userId = userId, name = name)
    }

    override suspend fun deleteById(userId: Long) {
        userDao.deleteById(userId = userId)
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }
}
