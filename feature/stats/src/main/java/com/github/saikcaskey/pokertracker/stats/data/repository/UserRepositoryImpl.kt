package com.github.saikcaskey.pokertracker.stats.data.repository

import com.github.saikcaskey.pokertracker.domain.dao.UserDao
import com.github.saikcaskey.pokertracker.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.domain.models.User
import com.github.saikcaskey.pokertracker.domain.repository.UserRepository
import com.github.saikcaskey.stats.extensions.flatMapWithUserId
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

    override suspend fun insert(name: String) {
        userDao.insert(name = name)
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
