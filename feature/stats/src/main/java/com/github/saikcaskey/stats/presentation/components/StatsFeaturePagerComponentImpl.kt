package com.github.saikcaskey.stats.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.subscribe
import com.github.saikcaskey.pokertracker.domain.presentation.component.FeatureComponent
import com.github.saikcaskey.stats.domain.components.StatsFeaturePagerComponent
import com.github.saikcaskey.stats.factory.StatsComponentFactory
import com.github.saikcaskey.stats.presentation.navigation.StatsPagerNavigationRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StatsFeaturePagerComponentImpl(
    componentContext: ComponentContext,
    componentFactory: StatsComponentFactory,
) : StatsFeaturePagerComponent, ComponentContext by componentContext {

    private val navigation = PagesNavigation<StatsPagerNavigationRoute>()

    override val pages: Value<ChildPages<*, FeatureComponent>> =
        childPages(
            source = navigation,
            serializer = StatsPagerNavigationRoute.serializer(),
            childFactory = { route, ctx -> componentFactory.buildChildComponent(ctx, route) },
            initialPages = {
                Pages(
                    items = List(StatsPagerItemType.entries.size) { index ->
                        when (index) {
                            2 -> StatsPagerNavigationRoute.ExpensesRoute
                            1 -> StatsPagerNavigationRoute.EventsRoute
                            else -> StatsPagerNavigationRoute.VenuesRoute
                        }
                    },
                    selectedIndex = 0,
                )
            },
        )

    init {
        pages.subscribe(lifecycle) {
            _selectedIndex.value = it.selectedIndex
        }
    }

    private val _selectedIndex = MutableStateFlow(pages.value.selectedIndex)

    override val selectedIndex: StateFlow<Int> = _selectedIndex

    override fun selectPage(index: Int) {
        navigation.select(index = index)
    }
}

private enum class StatsPagerItemType {
    Events,
    Expenses,
    Venues
}

