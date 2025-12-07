package com.github.saikcaskey.pokertracker.libs.domain.datasource

import com.github.saikcaskey.pokertracker.libs.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface AccountSettingsDataSource {
    val state: StateFlow<AccountSettingsData>

    fun setUserPreference(preference: UserPreference<*>, value: Any?)
}
