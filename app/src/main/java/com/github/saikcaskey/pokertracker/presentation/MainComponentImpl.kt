package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.subscribe
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponentImpl
import com.github.saikcaskey.database.di.PokerTrackerDatabaseProvider
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerFeatureComponentImpl
import com.github.saikcaskey.account.presentation.AccountFeatureComponentImpl
import com.github.saikcaskey.pokertracker.domain.presentation.MainPagerPageComponent
import com.github.saikcaskey.stats.presentation.StatsFeatureComponentImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

class MainComponentImpl(
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
    private val accountSettingsRepository: AccountSettingsRepository,
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
                        3 -> MainMenuPagerPageConfig.Account
                        2 -> MainMenuPagerPageConfig.Stats
                        1 -> MainMenuPagerPageConfig.Planner
                        else -> MainMenuPagerPageConfig.Dashboard
                    }
                },
                selectedIndex = 0,
            )
        },
    ) { config, childComponentContext ->
        when (config) {
            MainMenuPagerPageConfig.Account -> AccountFeatureComponentImpl(
                componentContext = childComponentContext,
                database = PokerTrackerDatabaseProvider.provide(),
                accountSettingsRepository = accountSettingsRepository,
                dispatchers = dispatchers,
            )

            MainMenuPagerPageConfig.Planner -> PlannerFeatureComponentImpl(
                componentContext = childComponentContext,
                eventsRepository = eventRepository,
                onCalendarDayClicked = onShowCalendarDayDetail,
                dispatchers = dispatchers
            )

            MainMenuPagerPageConfig.Dashboard -> DashboardFeatureComponentImpl(
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
                onShowAllVenues = onShowAllVenues
            )

            MainMenuPagerPageConfig.Stats -> StatsFeatureComponentImpl(
                componentContext = childComponentContext,
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

    @Serializable
    sealed class MainMenuPagerPageConfig {
        @Serializable
        data object Dashboard : MainMenuPagerPageConfig()

        @Serializable
        data object Planner : MainMenuPagerPageConfig()

        @Serializable
        data object Stats : MainMenuPagerPageConfig()

        @Serializable
        data object Account : MainMenuPagerPageConfig()
    }
}

private fun Int.toPageTitle(): String {
    return when (this) {
        0 -> "Dashboard"
        1 -> "Planner"
        2 -> "Settings"
        else -> "Settings"
    }
}

private enum class MainMenuPagerItemType {
    Dashboard,
    Planner,
    Stats,
    Settings
}
