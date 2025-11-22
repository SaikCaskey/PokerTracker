package com.github.saikcaskey.pokertracker.presentation.components.factory

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.account.presentation.AccountFeatureComponentImpl
import com.github.saikcaskey.database.di.PokerTrackerDatabaseProvider
import com.github.saikcaskey.database.di.SampleDataSeederProvider
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponentImpl
import com.github.saikcaskey.pokertracker.di.RootNavigatorProvider
import com.github.saikcaskey.pokertracker.di.StatsComponentFactoryProvider
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.factory.MainPagerComponentFactory
import com.github.saikcaskey.pokertracker.domain.presentation.component.FeatureComponent
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.UserRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerFeatureComponentImpl
import com.github.saikcaskey.pokertracker.presentation.navigation.MainPagerPageNavigationRoute
import com.github.saikcaskey.pokertracker.stats.presentation.components.StatsFeaturePagerComponentImpl

class MainPagerComponentFactoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val navigator: RootNavigator,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val userRepository: UserRepository,
    private val venueRepository: VenueRepository,
) : MainPagerComponentFactory {

    override fun buildChildComponent(
        ctx: ComponentContext,
        route: MainPagerPageNavigationRoute,
    ): FeatureComponent = when (route) {
        MainPagerPageNavigationRoute.Account -> accountFeatureComponent(ctx, route)
        MainPagerPageNavigationRoute.Planner -> plannerFeatureComponentImpl(ctx, route)
        MainPagerPageNavigationRoute.Dashboard -> dashboardFeatureComponentImpl(ctx, route)
        MainPagerPageNavigationRoute.Stats -> statsFeatureComponentImpl(ctx, route)
    }

    private fun statsFeatureComponentImpl(
        ctx: ComponentContext,
        @Suppress("unused") route: MainPagerPageNavigationRoute,
    ): FeatureComponent {
        return StatsFeaturePagerComponentImpl(
            componentContext = ctx,
            componentFactory = StatsComponentFactoryProvider.provide()
        )
    }

    private fun dashboardFeatureComponentImpl(
        ctx: ComponentContext,
        @Suppress("unused") route: MainPagerPageNavigationRoute,
    ): FeatureComponent {
        return DashboardFeatureComponentImpl(
            componentContext = ctx,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
            dispatchers = dispatchers,
            rootNavigator = RootNavigatorProvider.provide(),
        )
    }

    private fun plannerFeatureComponentImpl(
        ctx: ComponentContext,
        @Suppress("unused") route: MainPagerPageNavigationRoute,
    ): FeatureComponent {
        return PlannerFeatureComponentImpl(
            componentContext = ctx,
            eventsRepository = eventRepository,
            onCalendarDayClicked = navigator::onShowCalendarDayDetail,
            dispatchers = dispatchers
        )
    }

    private fun accountFeatureComponent(
        ctx: ComponentContext,
        @Suppress("unused") route: MainPagerPageNavigationRoute,
    ): FeatureComponent {
        return AccountFeatureComponentImpl(
            componentContext = ctx,
            database = PokerTrackerDatabaseProvider.provide(),
            accountSettingsRepository = accountSettingsRepository,
            userRepository = userRepository,
            seeder = SampleDataSeederProvider.provide(),
            dispatchers = dispatchers,
        )
    }
}
