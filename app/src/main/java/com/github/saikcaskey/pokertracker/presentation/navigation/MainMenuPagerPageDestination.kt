package com.github.saikcaskey.pokertracker.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainMenuPagerPageDestination {
    @Serializable
    data object Dashboard : MainMenuPagerPageDestination()

    @Serializable
    data object Planner : MainMenuPagerPageDestination()

    @Serializable
    data object Stats : MainMenuPagerPageDestination()

    @Serializable
    data object Account : MainMenuPagerPageDestination()
}
