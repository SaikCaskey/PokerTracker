package com.github.saikcaskey.pokertracker.domain.datastore

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.Flow

interface AccountSettingsDataStore {

    fun setShowAdvancedSettings(value: Boolean)
    fun setDefaultBuyIn(value: Int?)
    fun setUserId(userId: Long? = null)

    fun setUserPreference(preference: UserPreference<*>, value: Any?)
    fun clearUserPreference(preference: UserPreference<*>)

    val data: Flow<AccountSettingsData>
}
