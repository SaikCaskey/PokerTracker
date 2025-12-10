package com.github.saikcaskey.pokertracker.feature.onboarding.components

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.feature.onboarding.domain.components.CreateAccountComponent
import com.github.saikcaskey.pokertracker.feature.onboarding.domain.components.InstructionsComponent
import com.github.saikcaskey.pokertracker.feature.onboarding.domain.components.IntroComponent
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables.OnboardingFeatureDestination
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.navigation.OnboardingNavigationRoute
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator

class OnboardingComponentFactoryImpl(
    private val navigator: RootNavigator,
    private val dispatchers: CoroutineDispatchers,
) : OnboardingComponentFactory {

    override fun buildComponent(
        ctx: ComponentContext,
        route: OnboardingNavigationRoute,
    ): OnboardingFeatureDestination = when (route) {
        OnboardingNavigationRoute.CreateAccountRoute -> {
            OnboardingFeatureDestination.CreateAccountDestination(
                createAccountComponent(
                    ctx,
                    route
                )
            )
        }

        OnboardingNavigationRoute.InstructionsRoute -> {
            OnboardingFeatureDestination.InstructionsDestination(instructionsComponent(ctx, route))
        }

        OnboardingNavigationRoute.IntroRoute -> OnboardingFeatureDestination.IntroDestination(
            introComponent(ctx, route)
        )
    }


    private fun introComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: OnboardingNavigationRoute,
    ): IntroComponent {
        return OnboardingIntroComponentImpl(
            componentContext = componentContext,
            onNext = { },
        )
    }


    private fun createAccountComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: OnboardingNavigationRoute,
    ): CreateAccountComponent {
        return OnboardingAccountComponentImpl(
            componentContext = componentContext,
            onNext = { },
        )
    }

    private fun instructionsComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: OnboardingNavigationRoute,
    ): InstructionsComponent {
        return OnboardingInstructionsComponentImpl(
            componentContext = componentContext,
            onFinished = { },
        )
    }
}



