package com.github.saikcaskey.pokertracker.feature.stats.data.datasource

import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.dao.UserDao
import com.github.saikcaskey.pokertracker.libs.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.libs.domain.datastore.AccountSettingsDataStore
import com.github.saikcaskey.pokertracker.libs.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn

class UserDataSourceImpl(
    userDao: UserDao,
    private val dataStore: AccountSettingsDataStore,
    dispatchers: CoroutineDispatchers,
) : UserDataSource {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val storedUser: StateFlow<User?> =
        selectedUserId.flatMapLatest { userId ->
            (userId?.let(userDao::getById) ?: emptyFlow()).onEmpty { emit(null) }
        }.stateIn(coroutineScope, Eagerly, null)

    private val selectedUserId: Flow<Long?>
        get() = dataStore.data.map { it.userId }.distinctUntilChanged()
}
