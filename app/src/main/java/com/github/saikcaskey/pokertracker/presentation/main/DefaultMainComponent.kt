package com.github.saikcaskey.pokertracker.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.subscribe
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.shared.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.components.MainComponent
import com.github.saikcaskey.pokertracker.domain.components.MainComponent.MainMenuPagerPageConfig
import com.github.saikcaskey.pokertracker.domain.components.MainComponent.MainMenuPagerPageConfig.*
import com.github.saikcaskey.pokertracker.domain.components.MainPagerPageComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val onShowEventDetail: (Long) -> Unit,
    private val onShowExpenseDetail: (Long) -> Unit,
    private val onShowVenueDetail: (Long) -> Unit,
    private val onShowCalendarDayDetail: (LocalDate, Boolean) -> Unit,
    private val onShowInsertExpense: (existingExpenseId: Long?, venueId: Long?, eventId: Long?) -> Unit,
    private val onShowInsertEvent: (existingExpenseId: Long?, venueId: Long?, LocalDate?) -> Unit,
    private val onShowInsertVenue: (Long?) -> Unit,
    private val onShowAllVenues: () -> Unit,
    private val onShowAllEvents: () -> Unit,
    private val onShowAllExpenses: () -> Unit,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val venueRepository: VenueRepository,
    private val dispatchers: CoroutineDispatchers,
) : MainComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)
    private val navigation = PagesNavigation<MainMenuPagerPageConfig>()

    override val pages: Value<ChildPages<*, MainPagerPageComponent>> = childPages(
        source = navigation,
        serializer = MainMenuPagerPageConfig.serializer(),
        initialPages = {
            Pages(
                items = List(MainMenuPagerItemType.entries.size) { index ->
                    when (index) {
                        1 -> Planner
                        else -> Dashboard
                    }
                },
                selectedIndex = 0,
            )
        },
    ) { config, childComponentContext ->
        when (config) {
            Planner -> MainPagerPagePlannerComponent(
                componentContext = childComponentContext,
                eventsRepository = eventRepository,
                onCalendarDayClicked = onShowCalendarDayDetail,
                dispatchers = dispatchers
            )

            Dashboard -> MainPagerPageDashboardComponent(
                componentContext = childComponentContext,
                eventRepository = eventRepository,
                expenseRepository = expenseRepository,
                venueRepository = venueRepository,
                dispatchers = dispatchers,
                onShowEventDetail = onShowEventDetail,
                onShowExpenseDetail = onShowExpenseDetail,
                onShowVenueDetail = onShowVenueDetail,
                onShowInsertExpense = { onShowInsertExpense(null, null, null) },
                onShowInsertEvent = { onShowInsertEvent(null, null, null) },
                onShowInsertVenue = { onShowInsertVenue(null) },
                onShowAllEvents = onShowAllEvents,
                onShowAllExpenses = onShowAllExpenses,
                onShowAllVenues = onShowAllVenues,
            )
        }
    }

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
        navigation.select(index = index)
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
