package com.github.saikcaskey.pokertracker.feature.onboarding.composables

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.generators.GeneratorUsernames
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.libs.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingFeatureComponentImpl(
    componentContext: ComponentContext,
    private val userRepository: UserRepository,
    private val rootNavigator: RootNavigator,
    private val usernameGenerator: GeneratorUsernames,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val dispatchers: CoroutineDispatchers,
) : OnboardingFeatureComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(dispatchers.io)

    override val uiState = flowOf(OnboardingFeatureComponent.UiState())

    override fun generateUsername(): String {
        return usernameGenerator.generate()
    }

    override fun finishOnboarding() {
        scope.launch {
            val newUserId = userRepository.insert(generateUsername())
            accountSettingsRepository.setUserPreference(UserPreference.UserId, newUserId)

            withContext(dispatchers.main) {
                rootNavigator.onShowAccount()
            }
        }
    }
}




