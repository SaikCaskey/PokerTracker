package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.feature.onboarding.components.OnboardingComponentFactory
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation.OnboardingNavigationRoute
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation.OnboardingNavigator
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.generators.GeneratorUsernames
import com.github.saikcaskey.pokertracker.libs.domain.models.UserPreference
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigationRoute
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.libs.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingFeaturePagerComponentImpl(
    componentContext: ComponentContext,
    private val userRepository: UserRepository,
    private val componentFactory: OnboardingComponentFactory,
    private val rootNavigator: RootNavigator,
    private val onboardingNavigator: OnboardingNavigator,
    private val usernameGenerator: GeneratorUsernames,
    private val accountSettingsRepository: AccountSettingsRepository,
    dispatchers: CoroutineDispatchers,
) : OnboardingFeaturePagerComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(dispatchers.io)

    private val _uiState = MutableStateFlow(OnboardingFeaturePagerComponent.UiState())
    override val uiState: StateFlow<OnboardingFeaturePagerComponent.UiState> = _uiState

    override val stack: Value<ChildStack<OnboardingNavigationRoute, OnboardingFeatureDestination>> =
        childStack(
            initialConfiguration = OnboardingNavigationRoute.IntroRoute,
            source = onboardingNavigator.navigationSource,
            serializer = OnboardingNavigationRoute.serializer(),
            childFactory = { route, ctx -> componentFactory.buildComponent(ctx, route) },
        )

    override fun onUsernameChanged(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    override fun onClickBack() {
        onboardingNavigator.pop()
    }

    override fun onClickNext() {
        when (stack.value.active.configuration) {
            is OnboardingNavigationRoute.IntroRoute -> {
                onboardingNavigator.push(OnboardingNavigationRoute.CreateAccountRoute)
            }

            is OnboardingNavigationRoute.CreateAccountRoute -> {
                onboardingNavigator.push(OnboardingNavigationRoute.InstructionsRoute)
            }

            is OnboardingNavigationRoute.InstructionsRoute -> {
                createInitialUser()
                rootNavigator.push(RootNavigationRoute.DashboardRoute)
            }
        }
    }

    override fun generateUsername() {
        _uiState.value = OnboardingFeaturePagerComponent.UiState(
            usernameGenerator.generate()
        )
    }

    override fun createInitialUser() {
        scope.launch {
            val newUser = _uiState.value.username
            val newUserId = userRepository.insert(newUser.orEmpty())
            accountSettingsRepository.setUserPreference(UserPreference.UserId, newUserId)
        }
    }
}

