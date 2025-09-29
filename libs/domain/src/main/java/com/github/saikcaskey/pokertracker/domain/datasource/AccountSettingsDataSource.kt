package com.github.saikcaskey.pokertracker.domain.datasource

import com.github.saikcaskey.pokertracker.domain.models.AccountSettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface AccountSettingsDataSource {
    val state: StateFlow<AccountSettingsData>

    fun setUserPreference(preference: UserPreference<*>, value: Any?)
}
