package com.github.saikcaskey.pokertracker.domain.datastore

import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsDataStore {

    fun setIsDebug(value: Boolean)
    fun setLastSelectedTab(value: Int?)
    fun setUserId(userId: String? = null)

    val data: Flow<SettingsData>
}
