package com.github.saikcaskey.settings.domain.datasource.datasource

import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import kotlinx.coroutines.flow.StateFlow

interface UserSettingsDataSource {
    fun setUserId(userId: String? = null)
    fun setIsDebug(value: Boolean)
    fun setLastSelectedTab(value: Int? = null)

    val state: StateFlow<SettingsData>
}

