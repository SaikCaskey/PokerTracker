package com.github.saikcaskey.pokertracker.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    val state: StateFlow<SettingsData>

    fun <R> setUserPreference(preference: UserPreference<R>, value: R?)
}
