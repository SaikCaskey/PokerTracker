package com.github.saikcaskey.settings.data.datasource

import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.settings.domain.datasource.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SettingsDataSourceImpl(
    private val dataStore: SettingsDataStore,
    dispatchers: CoroutineDispatchers,
) : SettingsDataSource {

    private val scope = CoroutineScope(dispatchers.io)

    override val state: StateFlow<SettingsData> = dataStore.data
        .stateIn(scope, Eagerly, SettingsData())

    override fun setUserId(userId: String?) {
        dataStore.setUserId(userId)
    }

    override fun setShowDebugSettings(value: Boolean) {
        dataStore.setShowDebugSettings(value)
    }

    override fun setDefaultBuyIn(value: Int?) {
        dataStore.setDefaultBuyIn(value)
    }
}
