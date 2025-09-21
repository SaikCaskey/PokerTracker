package com.github.saikcaskey.pokertracker.domain.components

import com.github.saikcaskey.pokertracker.domain.models.SettingsItemsData
import kotlinx.coroutines.flow.StateFlow

interface SettingsFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    fun setUserId(uuid: kotlin.uuid.Uuid)

    data class UiState(val settingsItemsData: SettingsItemsData = SettingsItemsData())
}
