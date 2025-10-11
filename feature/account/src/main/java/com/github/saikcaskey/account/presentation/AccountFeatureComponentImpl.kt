package com.github.saikcaskey.account.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.account.domain.model.AccountSettingsAction.*
import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.account.presentation.AccountFeatureComponent.UiState
import com.github.saikcaskey.database.seed.SampleDataSeeder
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.models.User
import com.github.saikcaskey.pokertracker.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.DefaultBuyIn
import com.github.saikcaskey.pokertracker.domain.models.UserPreference.UserId
import com.github.saikcaskey.pokertracker.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

class AccountFeatureComponentImpl(
    private val componentContext: ComponentContext,
    private val database: PokerTrackerDatabase,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val seeder: SampleDataSeeder,
    userRepository: UserRepository,
    dispatchers: CoroutineDispatchers,
) : AccountFeatureComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    override val uiState: StateFlow<UiState> = combine(
        accountSettingsRepository.state,
        userRepository.getAll().stateIn(coroutineScope, Eagerly, emptyList()),
    ) { accountSettingsData, userIdSuggestionsData ->
        UiState(accountSettingsData, userIdSuggestionsData.map(User::id))
    }
        .stateIn(coroutineScope, Eagerly, UiState())

    override fun updatePreferenceValue(preference: UserPreference<*>, value: Any?) {
        accountSettingsRepository.setUserPreference(preference, value)
    }

    override fun setRandomUserId() {
        accountSettingsRepository.setUserPreference(UserId, Random.nextLong(until = 100))
    }

    override fun clearDefaultBuyIn() {
        accountSettingsRepository.setUserPreference(DefaultBuyIn, null)
    }

    override fun clearUserId() {
        accountSettingsRepository.setUserPreference(UserId, null)
    }

    override fun seed(action: SeedData) {
        val selectedUserId = if (uiState.value.accountSettingsData.userId == null) {
            seeder.user()
            database.userQueries.lastInsertRowId().executeAsOne()
        } else {
            requireNotNull(uiState.value.accountSettingsData.userId)
        }

        when (action) {
            SeedData.BadDay -> seeder.badDay(selectedUserId)
            SeedData.GoodDay -> seeder.goodDay(selectedUserId)
            SeedData.SmokeTest -> seeder.smokeTest(selectedUserId)
            SeedData.User -> seeder.user()
        }
    }

    override fun clearAllData() {
        database.transaction {
            database.eventQueries.deleteAll()
            database.userQueries.deleteAll()
            database.venueQueries.deleteAll()
            database.expenseQueries.deleteAll()
        }
    }
}
