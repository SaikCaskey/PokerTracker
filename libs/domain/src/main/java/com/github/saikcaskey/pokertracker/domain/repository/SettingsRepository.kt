package com.github.saikcaskey.pokertracker.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.SettingsItemsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    val state: StateFlow<SettingsItemsData>

    fun <R> setUserPreference(preference: UserPreference<R>, value: R?)
}
