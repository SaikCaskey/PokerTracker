package com.github.saikcaskey.account.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.datastore.SettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.AppInfo
import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.DefaultBuyIn
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.ShowAdvancedSettings
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.UserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class SettingsDataStoreImpl(
    private val appInfo: AppInfo,
    private val dataStore: DataStore<Preferences>,
    dispatchers: CoroutineDispatchers,
) : SettingsDataStore {
    private val scope = CoroutineScope(dispatchers.io)
    private val userIdPreferencesKey = stringPreferencesKey(UserId.key)
    private val showAdvancedSettingsPreferencesKey = booleanPreferencesKey(ShowAdvancedSettings.key)
    private val defaultBuyInPreferencesKey = intPreferencesKey(DefaultBuyIn.key)

    override val data: Flow<SettingsData>
        get() = dataStore.data
            .catch { err -> if (err is IOException) emit(emptyPreferences()) else throw err }
            .map { it.toSettingsData(appInfo) }

    override fun setShowAdvancedSettings(value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[showAdvancedSettingsPreferencesKey] = value
            }
        }
    }

    override fun setDefaultBuyIn(value: Int?) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[defaultBuyInPreferencesKey] = value ?: 0
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

private fun Preferences.toSettingsData(appInfo: AppInfo): SettingsData {
    return SettingsData(
        userId = get(UserId.getStringPreference()),
        showAdvancedSettings = get(ShowAdvancedSettings.getBooleanPreference()) == true,
        defaultBuyIn = get(DefaultBuyIn.getIntPreference()),
        applicationId = appInfo.applicationId,
        isProd = appInfo.isProd,
        buildType = appInfo.buildType,
        versionCode = appInfo.versionCode,
        versionName = appInfo.versionName,
        gitCommitHash = appInfo.gitCommitHash,
    )
}

fun UserPreference<String>.getStringPreference() = stringPreferencesKey(key)
fun UserPreference<Boolean>.getBooleanPreference() = booleanPreferencesKey(key)
fun UserPreference<Int>.getIntPreference() = intPreferencesKey(key)
