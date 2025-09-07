package com.github.saikcaskey.pokertracker.presentation.settings

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.components.SettingsComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsFeatureComponentImpl(
    private val componentContext: ComponentContext,
) : SettingsComponent, ComponentContext by componentContext {

    override val uiState: StateFlow<SettingsComponent.UiState> =
        MutableStateFlow(SettingsComponent.UiState())
}
