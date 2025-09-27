package com.github.saikcaskey.settings.data.datasource

import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.settings.domain.datasource.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * DataSource that converts preferences from [androidx.datastore.dataStore] into
 * a list of [SettingsItem]s, which should contain the data to be shown, as well as the sections
 * to be laid out.
 */
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

    override fun setLastSelectedTab(value: Int?) {
        dataStore.setLastSelectedTab(value)
    }
}
