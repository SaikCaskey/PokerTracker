package com.github.saikcaskey.stats.domain

import com.github.saikcaskey.pokertracker.domain.components.MainPagerPageComponent
import com.github.saikcaskey.pokertracker.domain.models.StatsData
import kotlinx.coroutines.flow.StateFlow

interface StatsFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    data class UiState(
        val accountStatsData: StatsData = StatsData(),
    )
}
