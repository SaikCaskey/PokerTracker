package com.github.saikcaskey.settings.domain.datasource

import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import kotlinx.coroutines.flow.StateFlow

interface SettingsDataSource {
    val state: StateFlow<SettingsData>

    fun setUserId(userId: String? = null)
    fun setShowAdvancedSettings(value: Boolean)
    fun setDefaultBuyIn(value: Int? = null)
}
