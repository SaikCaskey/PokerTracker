package com.github.saikcaskey.pokertracker.libs.domain.repository

import com.github.saikcaskey.pokertracker.libs.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface AccountSettingsRepository {

    val state: StateFlow<AccountSettingsData>

    fun setUserPreference(preference: UserPreference<*>, value: Any?)

    fun clearAll()
}
