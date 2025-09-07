package com.github.saikcaskey.pokertracker.domain.components

import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    data class UiState(
        val userId: Long? = null,
    )
}
