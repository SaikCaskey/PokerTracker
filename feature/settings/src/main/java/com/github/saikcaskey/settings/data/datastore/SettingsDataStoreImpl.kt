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
    private val userIdPreferencesKey = stringPreferencesKey(UserPreference.UserId.key)
    private val isDebugPreferencesKey = booleanPreferencesKey(UserPreference.IsDebug.key)
    private val lastSelectedTabPreferencesKey =
        intPreferencesKey(UserPreference.LastSelectedTab.key)

    override val data: Flow<SettingsData>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map { preferences ->
                SettingsData(
                    userId = preferences[userIdPreferencesKey],
                    lastSelectedTab = preferences[lastSelectedTabPreferencesKey],
                    isDebug = preferences[isDebugPreferencesKey] == true,
                )
            }

    override fun setIsDebug(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[isDebugPreferencesKey] = value
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
