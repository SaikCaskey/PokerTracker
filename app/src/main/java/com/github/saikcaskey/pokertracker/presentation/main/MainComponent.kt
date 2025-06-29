package com.saikcaskey.github.pokertracker.shared.presentation.main

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface MainComponent {
    val pages: Value<ChildPages<*, MainPagerPageComponent>>
    val selectedIndex: StateFlow<Int>
    val title: StateFlow<String>

    fun selectPage(index: Int)

    @Serializable
    sealed class MainMenuPagerPageConfig {
        @Serializable
        data object Dashboard : MainMenuPagerPageConfig()

        @Serializable
        data object Planner : MainMenuPagerPageConfig()
    }
}
