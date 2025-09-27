package com.github.saikcaskey.settings.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.LastSelectedTab
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.ShowDebugSettings
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.UserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class SettingsDataStoreImpl(
    private val dataStore: DataStore<Preferences>,
    dispatchers: CoroutineDispatchers,
) : SettingsDataStore {

    private val scope = CoroutineScope(dispatchers.io)
    private val userIdPreferencesKey = stringPreferencesKey(UserId.key)
    private val showDebugSettingsPreferencesKey = booleanPreferencesKey(ShowDebugSettings.key)
    private val lastSelectedTabPreferencesKey = intPreferencesKey(LastSelectedTab.key)

    override val data: Flow<SettingsData>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map(Preferences::toSettingsData)

    override fun setShowDebugSettings(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[showDebugSettingsPreferencesKey] = value
            }
        }
    }

    override fun setLastSelectedTab(value: Int?) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[lastSelectedTabPreferencesKey] = value ?: 0
            }
        }
    }

    override fun setUserId(userId: String?) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[userIdPreferencesKey] = userId.orEmpty()
            }
        }
    }
}

private fun Preferences.toSettingsData(): SettingsData {
    return SettingsData(
        userId = get(UserId.getStringPreference()),
        showDebugSettings = get(ShowDebugSettings.getBooleanPreference()) == true,
        lastSelectedTab = get(LastSelectedTab.getIntPreference()),
    )
}

fun UserPreference<String>.getStringPreference() = stringPreferencesKey(key)
fun UserPreference<Boolean>.getBooleanPreference() = booleanPreferencesKey(key)
fun UserPreference<Int>.getIntPreference() = intPreferencesKey(key)
