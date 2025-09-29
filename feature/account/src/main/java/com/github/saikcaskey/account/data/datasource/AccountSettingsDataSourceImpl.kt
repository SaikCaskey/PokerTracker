package com.github.saikcaskey.account.data.datasource

import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.datasource.AccountSettingsDataSource
import com.github.saikcaskey.pokertracker.domain.datastore.AccountSettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AccountSettingsDataSourceImpl(
    private val dataStore: AccountSettingsDataStore,
    dispatchers: CoroutineDispatchers,
) : AccountSettingsDataSource {

    private val scope = CoroutineScope(dispatchers.io)

    override val state: StateFlow<AccountSettingsData> = dataStore.data
        .stateIn(scope, Eagerly, AccountSettingsData())

    override fun setUserPreference(
        preference: UserPreference<*>,
        value: Any?,
    ) {
        dataStore.setUserPreference(preference, value)
    }
}
