package com.github.saikcaskey.account.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.datastore.AccountSettingsDataStore
import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.domain.models.AppInfo
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

class AccountSettingsDataStoreImpl(
    private val appInfo: AppInfo,
    private val dataStore: DataStore<Preferences>,
    dispatchers: CoroutineDispatchers,
) : AccountSettingsDataStore {

    private val scope = CoroutineScope(dispatchers.io)
    private val userIdPreferencesKey = longPreferencesKey(UserId.key)
    private val showAdvancedSettingsPreferencesKey = booleanPreferencesKey(ShowAdvancedSettings.key)

    override val data: Flow<AccountSettingsData>
        get() = dataStore.data
            .catch { err -> if (err is IOException) emit(emptyPreferences()) else throw err }
            .map { it.toSettingsData(appInfo) }

    override fun setUserPreference(preference: UserPreference<*>, value: Any?) {
        when (preference) {
            DefaultBuyIn -> setDefaultBuyIn(value as? Int)
            ShowAdvancedSettings -> setShowAdvancedSettings(value as Boolean)
            UserId -> setUserId(value as? Long)
        }
    }

    override fun clearUserPreference(preference: UserPreference<*>) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences.remove(preference.preferenceKey)
            }
        }
    }

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
                preferences[DefaultBuyIn.preferenceKey] = value ?: 0
            }
        }
    }

    override fun setUserId(userId: Long?) {
        scope.launch {
            dataStore.edit { preferences ->
                if (userId == preferences[userIdPreferencesKey]) return@edit
                if (userId != null) {
                    preferences[userIdPreferencesKey] = userId
                }
            }
        }
    }
}

private fun Preferences.toSettingsData(appInfo: AppInfo): AccountSettingsData {
    return AccountSettingsData(
        userId = runCatching { get(UserId.preferenceKey) as Long}.getOrNull(),
        showAdvancedSettings = get(ShowAdvancedSettings.preferenceKey) == true,
        defaultBuyIn = get(DefaultBuyIn.preferenceKey),
        applicationId = appInfo.applicationId,
        isProd = appInfo.isProd,
        buildType = appInfo.buildType,
        versionCode = appInfo.versionCode,
        versionName = appInfo.versionName,
        gitCommitHash = appInfo.gitCommitHash,
    )
}

@Suppress("UNCHECKED_CAST")
private val <R : Any> UserPreference<R>.preferenceKey: Preferences.Key<R>
    get() {
        val preference = this
        return when (preference.type) {
            String::class -> stringPreferencesKey(preference.key)
            Boolean::class -> booleanPreferencesKey(preference.key)
            Long::class -> longPreferencesKey(preference.key)
            Int::class -> intPreferencesKey(preference.key)
            else -> byteArrayPreferencesKey(preference.key)
        } as Preferences.Key<R>
    }

