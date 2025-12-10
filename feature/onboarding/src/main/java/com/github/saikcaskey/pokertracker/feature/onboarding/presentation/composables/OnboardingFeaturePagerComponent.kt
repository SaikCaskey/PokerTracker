package com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.feature.onboarding.domain.components.CreateAccountComponent
import com.github.saikcaskey.pokertracker.feature.onboarding.domain.components.InstructionsComponent
import com.github.saikcaskey.pokertracker.feature.onboarding.domain.components.IntroComponent
import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.FeatureComponent
import kotlinx.coroutines.flow.Flow

interface OnboardingFeaturePagerComponent : FeatureComponent {
    val stack: Value<ChildStack<*, OnboardingFeatureDestination>>
    val uiState: Flow<UiState>

    fun onClickBack()

    fun onClickNext()

    fun onUsernameChanged(username: String)

    fun generateUsername()

    fun createInitialUser()

    data class UiState(
        val username: String? = null,
        val storedUsers: List<Long> = emptyList(),
    )
}

sealed class OnboardingFeatureDestination {
    class IntroDestination(val component: IntroComponent) : OnboardingFeatureDestination()
    class CreateAccountDestination(val component: CreateAccountComponent) :
        OnboardingFeatureDestination()

    class InstructionsDestination(val component: InstructionsComponent) :
        OnboardingFeatureDestination()
}
