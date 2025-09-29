package com.github.saikcaskey.pokertracker.domain.datastore

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import kotlinx.coroutines.flow.Flow

interface AccountSettingsDataStore {

    fun setShowAdvancedSettings(value: Boolean)
    fun setDefaultBuyIn(value: Int?)
    fun setUserId(userId: String? = null)

    val data: Flow<AccountSettingsData>
}
