package com.github.saikcaskey.stats.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.saikcaskey.stats.domain.StatsFeatureComponent

@Composable
fun StatsFeatureContent(
    component: StatsFeatureComponent,
) {
    val uiState = component.uiState.collectAsStateWithLifecycle()
    StatsScreenContent(uiState = uiState.value)
}

@Composable
fun StatsScreenContent(
    uiState: StatsFeatureComponent.UiState,
) {
    Text("StatsScreenContent $uiState")
}
