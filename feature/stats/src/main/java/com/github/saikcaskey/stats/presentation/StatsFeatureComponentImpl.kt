package com.github.saikcaskey.stats.presentation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StatsFeatureComponentImpl(componentContext: ComponentContext) : StatsFeatureComponent,
    ComponentContext by componentContext {

    override val uiState: StateFlow<StatsFeatureComponent.UiState>
        get() = MutableStateFlow(StatsFeatureComponent.UiState())
}
