package com.github.saikcaskey.pokertracker.account.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.account.domain.model.AccountSettingsAction.SeedData
import com.github.saikcaskey.pokertracker.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.account.presentation.AccountFeatureComponent.UiState
import com.github.saikcaskey.pokertracker.database.seed.SampleDataSeeder
import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.models.User
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference.DefaultBuyIn
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference.UserId
import com.github.saikcaskey.pokertracker.libs.domain.repository.UserRepository
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
    private val onFinished: () -> Unit,
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

    override fun onBackClicked() {
        onFinished()
    }
}
