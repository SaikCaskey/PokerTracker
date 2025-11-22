package com.github.saikcaskey.pokertracker.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.subscribe
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.component.MainComponent
import com.github.saikcaskey.pokertracker.domain.factory.MainPagerComponentFactory
import com.github.saikcaskey.pokertracker.domain.presentation.component.FeatureComponent
import com.github.saikcaskey.pokertracker.presentation.navigation.MainPagerPageNavigationRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainPagerComponentImpl(
    componentContext: ComponentContext,
    private val componentFactory: MainPagerComponentFactory,
    dispatchers: CoroutineDispatchers,
) : MainComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val pagerNavigation = PagesNavigation<MainPagerPageNavigationRoute>()

    override val pages: Value<ChildPages<*, FeatureComponent>> = childPages(
        source = pagerNavigation,
        serializer = MainPagerPageNavigationRoute.serializer(),
        initialPages = {
            Pages(
                items = List(MainMenuPagerItemType.entries.size) { index ->
                    when (index) {
                        3 -> MainPagerPageNavigationRoute.Account
                        2 -> MainPagerPageNavigationRoute.Stats
                        1 -> MainPagerPageNavigationRoute.Planner
                        else -> MainPagerPageNavigationRoute.Dashboard
                    }
                },
                selectedIndex = 0,
            )
        },
        childFactory = { route, ctx -> componentFactory.buildChildComponent(ctx, route) },
    )
    
    init {
        pages.subscribe(lifecycle) {
            _selectedIndex.value = it.selectedIndex
        }
    }

    private val _selectedIndex = MutableStateFlow(pages.value.selectedIndex)
    override val selectedIndex: StateFlow<Int> = _selectedIndex

    override val title: StateFlow<String> = selectedIndex.map(Int::toPageTitle)
        .stateIn(coroutineScope, Eagerly, selectedIndex.value.toPageTitle())

    override fun selectPage(index: Int) {
        pagerNavigation.select(index = index)
    }
}

private fun Int.toPageTitle(): String {
    return when (this) {
        0 -> "Dashboard"
        1 -> "Planner"
        2 -> "Stats"
        else -> "Settings"
    }
}

private enum class MainMenuPagerItemType {
    Dashboard,
    Planner,
    Stats,
    Settings
}
