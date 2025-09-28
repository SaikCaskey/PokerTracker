package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.SettingsData
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import kotlinx.coroutines.flow.StateFlow

interface StatsFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun updatePreferenceValue(preference: UserPreference<*>, value: Any?)

    fun setRandomUserId()
    fun clearDefaultBuyIn()
    fun clearUserId()
    fun addDummyData()
    fun clearAllData()

    data class UiState(
        val settingsData: StatsData = SettingsData(),
    )
}
