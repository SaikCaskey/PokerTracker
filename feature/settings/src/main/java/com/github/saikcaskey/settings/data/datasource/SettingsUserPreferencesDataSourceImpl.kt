package com.github.saikcaskey.settings.data.datasource

import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.settings.domain.datasource.datasource.UserSettingsDataSource
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SettingsUserPreferencesDataSourceImpl(
    private val dataStore: SettingsDataStore,
    dispatchers: CoroutineDispatchers,
) : UserSettingsDataSource {

    private val scope = CoroutineScope(dispatchers.io)

    override fun setUserId(userId: String?) {
        dataStore.setUserId(userId)
    }

    override val state: StateFlow<SettingsData> = dataStore.data
        .stateIn(scope, Eagerly, SettingsData())
}
