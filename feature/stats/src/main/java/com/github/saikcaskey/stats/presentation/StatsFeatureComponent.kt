package com.github.saikcaskey.stats.presentation

import com.github.saikcaskey.pokertracker.domain.models.StatsData
import com.github.saikcaskey.pokertracker.domain.presentation.MainPagerPageComponent
import kotlinx.coroutines.flow.StateFlow

interface StatsFeatureComponent : MainPagerPageComponent {
    val uiState: StateFlow<UiState>

    data class UiState(
        val accountStatsData: StatsData = StatsData(),
    )
}
