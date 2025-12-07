package com.github.saikcaskey.pokertracker.feature.onboarding.composables

import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.FeatureComponent
import kotlinx.coroutines.flow.Flow

interface OnboardingFeatureComponent : FeatureComponent {

    val uiState: Flow<UiState>

    fun generateUsername(): String

    fun finishOnboarding()

    data class UiState(
        val generatedUsername: String? = null,
    )
}
