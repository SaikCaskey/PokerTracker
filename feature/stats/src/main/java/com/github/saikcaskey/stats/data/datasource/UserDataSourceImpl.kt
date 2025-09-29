package com.github.saikcaskey.stats.data.datasource

import co.touchlab.kermit.Logger
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.dao.UserDao
import com.github.saikcaskey.pokertracker.domain.datasource.UserDataSource
import com.github.saikcaskey.pokertracker.domain.datastore.AccountSettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class UserDataSourceImpl(
    userDao: UserDao,
    private val dataStore: AccountSettingsDataStore,
    dispatchers: CoroutineDispatchers,
) : UserDataSource {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val selectedUserId: StateFlow<Long?>
        get() = dataStore.data
            .distinctUntilChangedBy { it.userId }
            .onEach { Logger.i("asd dataStore.data ${it.userId}") }
            .map { it.userId?.toLongOrNull() }
            .onEach { Logger.i("asd longOrNull $it") }
            .stateIn(coroutineScope, Eagerly, null)

    override val storedUser: StateFlow<User?> =
        selectedUserId.flatMapLatest { userId ->
            Logger.i("asd finding User $userId ...")
            (userId?.let(userDao::getById) ?: emptyFlow()).onEach {
                Logger.i("asd found: ...$it")
            }
        }.stateIn(coroutineScope, Eagerly, null)
}
