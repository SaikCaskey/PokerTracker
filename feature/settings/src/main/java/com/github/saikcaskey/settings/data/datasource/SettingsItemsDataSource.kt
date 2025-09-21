package com.github.saikcaskey.settings.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.SettingsItem
import com.github.saikcaskey.pokertracker.domain.models.SettingsItemsData
import com.github.saikcaskey.settings.domain.datasource.datasource.SettingsItemsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * DataSource that converts preferences from [androidx.datastore.dataStore] into
 * a list of [SettingsItem]s,
 * which should contain the data to be shown, as well as the sections to be laid out.
 */
class SettingsItemsDataSourceImpl(
    dataStore: DataStore<Preferences>,
    dispatchers: CoroutineDispatchers,
) : SettingsItemsDataSource {

    private val scope = CoroutineScope(dispatchers.io)

    override val state: StateFlow<SettingsItemsData> = dataStore.data
        .map(Preferences::toSettingsItemsData)
        .stateIn(scope, Eagerly, SettingsItemsData())
}

private fun Preferences.toSettingsItemsData(): SettingsItemsData {
    return SettingsItemsData(
        listOf(
            SettingsItem.Header(),
            SettingsItem.Subheader(),
            SettingsItem.Text("userId: ${get(stringPreferencesKey("user_id"))}"),
            SettingsItem.Check(),
        )
    )
}
