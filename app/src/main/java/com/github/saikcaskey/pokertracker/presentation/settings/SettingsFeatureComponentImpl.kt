package com.github.saikcaskey.pokertracker.presentation.settings

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.data.utils.seedSampleData
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.SettingsFeatureComponent
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.*
import com.github.saikcaskey.pokertracker.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.uuid.Uuid

class SettingsFeatureComponentImpl(
    private val componentContext: ComponentContext,
    private val database: PokerTrackerDatabase,
    private val settingsRepository: SettingsRepository,
    dispatchers: CoroutineDispatchers,
) : SettingsFeatureComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<SettingsFeatureComponent.UiState> =
        settingsRepository.state.map(SettingsFeatureComponent::UiState)
            .stateIn(coroutineScope, SharingStarted.Eagerly, SettingsFeatureComponent.UiState())

    override fun inputToggleValue(
        preference: UserPreference<Boolean>,
        value: Boolean,
    ) {
        settingsRepository.setUserPreference(preference, value)
    }

    override fun inputTextValue(
        preference: UserPreference<String>,
        value: String?,
    ) {
        settingsRepository.setUserPreference(preference, value)
    }

    override fun inputNumberValue(
        preference: UserPreference<Int>,
        value: Int?,
    ) {
        settingsRepository.setUserPreference(preference, value)
    }

    override fun setRandomUserId() {
        settingsRepository.setUserPreference(UserId, Uuid.random().toString())
    }

    override fun clearDefaultBuyIn() {
        settingsRepository.setUserPreference(DefaultBuyIn, null)
    }

    override fun clearUserId() {
        settingsRepository.setUserPreference(UserId, null)
    }

    override fun addDummyData() {
        database.seedSampleData()
    }

    override fun clearAllData() {
        database.eventQueries.deleteAll()
        database.expenseQueries.deleteAll()
        database.venueQueries.deleteAll()
    }
}
