package com.github.saikcaskey.pokertracker.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.SettingsItemsData
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    val state: StateFlow<SettingsItemsData>

    fun setUserId(userId: String? = null)
}
