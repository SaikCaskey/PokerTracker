package com.github.saikcaskey.account.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.data.utils.seedSampleData
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.components.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.repository.AccountSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.uuid.Uuid

class AccountFeatureComponentImpl(
    private val componentContext: ComponentContext,
    private val database: PokerTrackerDatabase,
    private val accountSettingsRepository: AccountSettingsRepository,
    dispatchers: CoroutineDispatchers,
) : AccountFeatureComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<AccountFeatureComponent.UiState> =
        accountSettingsRepository.state.map(AccountFeatureComponent::UiState)
            .stateIn(coroutineScope, SharingStarted.Companion.Eagerly, AccountFeatureComponent.UiState())

    override fun updatePreferenceValue(preference: UserPreference<*>, value: Any?) {
        @Suppress("UNCHECKED_CAST")
        accountSettingsRepository.setUserPreference(preference as UserPreference<Any>, value)
    }

    override fun setRandomUserId() {
        accountSettingsRepository.setUserPreference(UserPreference.UserId, Uuid.Companion.random().toString())
    }

    override fun clearDefaultBuyIn() {
        accountSettingsRepository.setUserPreference(UserPreference.DefaultBuyIn, null)
    }

    override fun clearUserId() {
        accountSettingsRepository.setUserPreference(UserPreference.UserId, null)
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
