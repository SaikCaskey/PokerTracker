package com.github.saikcaskey.account.domain.datasource

import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import kotlinx.coroutines.flow.StateFlow

interface AccountSettingsDataSource {
    val state: StateFlow<SettingsData>

    fun setUserId(userId: String? = null)
    fun setShowAdvancedSettings(value: Boolean)
    fun setDefaultBuyIn(value: Int? = null)
}
