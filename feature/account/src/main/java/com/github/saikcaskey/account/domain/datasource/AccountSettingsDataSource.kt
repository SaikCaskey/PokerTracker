package com.github.saikcaskey.account.domain.datasource

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import kotlinx.coroutines.flow.StateFlow

interface AccountSettingsDataSource {
    val state: StateFlow<AccountSettingsData>

    fun setUserId(userId: String? = null)
    fun setShowAdvancedSettings(value: Boolean)
    fun setDefaultBuyIn(value: Int? = null)
}
