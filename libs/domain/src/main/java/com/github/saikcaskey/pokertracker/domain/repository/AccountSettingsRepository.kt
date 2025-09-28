package com.github.saikcaskey.pokertracker.domain.repository

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface AccountSettingsRepository {

    val state: StateFlow<AccountSettingsData>

    fun <R> setUserPreference(preference: UserPreference<R>, value: R?)
}
