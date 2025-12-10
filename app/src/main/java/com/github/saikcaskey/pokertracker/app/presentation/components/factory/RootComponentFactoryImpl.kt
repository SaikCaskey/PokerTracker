package com.github.saikcaskey.pokertracker.app.presentation.components.factory

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.app.di.OnboardingNavigatorProvider
import com.github.saikcaskey.pokertracker.app.di.StatsComponentFactoryProvider
import com.github.saikcaskey.pokertracker.app.domain.factory.RootComponentFactory
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination
import com.github.saikcaskey.pokertracker.feature.account.presentation.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.feature.account.presentation.AccountFeatureComponentImpl
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.DashboardFeatureComponentImpl
import com.github.saikcaskey.pokertracker.feature.onboarding.di.OnboardingComponentFactoryProvider
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables.OnboardingFeaturePagerComponent
import com.github.saikcaskey.pokertracker.feature.onboarding.presentation.composables.OnboardingFeaturePagerComponentImpl
import com.github.saikcaskey.pokertracker.feature.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.feature.planner.presentation.PlannerDayDetailComponentImpl
import com.github.saikcaskey.pokertracker.feature.planner.presentation.PlannerFeatureComponent
import com.github.saikcaskey.pokertracker.feature.planner.presentation.PlannerFeatureComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.components.EventDetailComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertEventComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertExpenseComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertVenueComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.StatsFeaturePagerComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.VenueDetailComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewEventsComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewExpensesComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewVenuesComponent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.EventDetailComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.ExpenseDetailComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.InsertEventComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.InsertExpenseComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.InsertVenueComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.StatsFeaturePagerComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.VenueDetailComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.ViewEventsComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.ViewExpensesComponentImpl
import com.github.saikcaskey.pokertracker.feature.stats.presentation.components.ViewVenuesComponentImpl
import com.github.saikcaskey.pokertracker.libs.database.di.PokerTrackerDatabaseProvider
import com.github.saikcaskey.pokertracker.libs.database.di.SampleDataSeederProvider
import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.libs.domain.generators.GeneratorUsernames
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigationRoute
import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.libs.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.UserRepository
import com.github.saikcaskey.pokertracker.libs.domain.repository.VenueRepository

class RootComponentFactoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val navigator: RootNavigator,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val venueRepository: VenueRepository,
    private val userRepository: UserRepository,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val usernameGenerator: GeneratorUsernames,
) : RootComponentFactory {

    override fun buildComponent(
        ctx: ComponentContext,
        route: RootNavigationRoute,
    ): RootDestination = when (route) {
        is RootNavigationRoute.OnboardingRoute -> RootDestination.OnboardingDestination(
            onboardingFeatureComponent(ctx, route)
        )

        is RootNavigationRoute.DashboardRoute -> RootDestination.DashboardDestination(
            dashboardFeatureComponent(ctx, route)
        )

        is RootNavigationRoute.PlannerRoute -> RootDestination.PlannerDestination(
            plannerFeatureComponent(ctx, route)
        )

        is RootNavigationRoute.StatsRoute -> RootDestination.StatsDestination(
            statsFeatureComponent(ctx, route)
        )

        is RootNavigationRoute.AccountRoute -> RootDestination.AccountDestination(
            accountFeatureComponent(ctx, route)
        )

        is RootNavigationRoute.DayDetailRoute -> RootDestination.PlannerDayDetailDestination(
            dayDetailComponent(ctx, route)
        )

        is RootNavigationRoute.InsertEventRoute -> RootDestination.InsertEventDestination(
            insertEventComponent(ctx, route)
        )

        is RootNavigationRoute.InsertVenueRoute -> RootDestination.InsertVenueDestination(
            insertVenueComponent(ctx, route)
        )

        is RootNavigationRoute.InsertExpenseRoute -> RootDestination.InsertExpenseDestination(
            insertExpenseComponent(ctx, route)
        )

        is RootNavigationRoute.EventDetailRoute -> RootDestination.EventDetailDestination(
            eventDetailComponent(ctx, route)
        )

        is RootNavigationRoute.VenueDetailRoute -> RootDestination.VenueDetailDestination(
            venueDetailComponent(ctx, route)
        )

        is RootNavigationRoute.ViewEventsRoute -> RootDestination.ViewEventsDestination(
            viewEventsComponent(ctx, route)
        )

        is RootNavigationRoute.ViewVenuesRoute -> RootDestination.ViewVenuesDestination(
            viewVenuesComponent(ctx, route)
        )

        is RootNavigationRoute.ViewExpensesRoute -> RootDestination.ViewExpensesDestination(
            viewExpensesComponent(ctx, route)
        )

        is RootNavigationRoute.ExpenseDetailRoute -> RootDestination.ExpenseDetailDestination(
            expenseDetailComponent(ctx, route)
        )
    }

    private fun dashboardFeatureComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.DashboardRoute,
    ): DashboardFeatureComponent {
        return DashboardFeatureComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            rootNavigator = navigator,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository
        )
    }

    private fun onboardingFeatureComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.OnboardingRoute,
    ): OnboardingFeaturePagerComponent {
        return OnboardingFeaturePagerComponentImpl(
            componentContext = componentContext,
            rootNavigator = navigator,
            userRepository = userRepository,
            usernameGenerator = usernameGenerator,
            accountSettingsRepository = accountSettingsRepository,
            componentFactory = OnboardingComponentFactoryProvider.provide(),
            onboardingNavigator = OnboardingNavigatorProvider.provide(),
            dispatchers = dispatchers,
        )
    }

    private fun eventDetailComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.EventDetailRoute,
    ): EventDetailComponent {
        return EventDetailComponentImpl(
            componentContext = componentContext,
            eventId = route.eventId,
            dispatchers = dispatchers,
            onShowExpenseDetail = navigator::onShowInsertExpense,
            onShowInsertExpense = { eventId, venueId ->
                navigator.onShowInsertExpense(
                    existingExpenseId = null,
                    eventId = eventId,
                    venueId = venueId
                )
            },
            onShowVenueDetail = { venueId ->
                navigator.onShowVenueDetail(venueId)
            },
            onShowEditEvent = { eventId ->
                navigator.onShowInsertEvent(eventId)
            },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
        )
    }

    private fun dayDetailComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.DayDetailRoute,
    ): PlannerDayDetailComponent {
        return PlannerDayDetailComponentImpl(
            componentContext = componentContext,
            date = route.date,
            dispatchers = dispatchers,
            onShowEventDetail = navigator::onShowEventDetail,
            onShowInsertEvent = {
                navigator.onShowInsertEvent(startDate = route.date)
            },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )
    }

    private fun expenseDetailComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.ExpenseDetailRoute,
    ): ExpenseDetailComponent {
        val expenseId = route.expenseId
        return ExpenseDetailComponentImpl(
            componentContext = componentContext,
            expenseId = expenseId,
            dispatchers = dispatchers,
            onShowEditExpense = {
                navigator.onShowInsertExpense(expenseId)
            },
            onShowEventDetail = { eventId ->
                navigator.onShowEventDetail(eventId)
            },
            onShowVenueDetail = { venueId ->
                navigator.onShowVenueDetail(venueId)
            },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
        )
    }

    private fun venueDetailComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.VenueDetailRoute,
    ): VenueDetailComponent {
        val venueId = route.venueId
        return VenueDetailComponentImpl(
            componentContext = componentContext,
            venueId = venueId,
            dispatchers = dispatchers,
            onShowEventDetail = navigator::onShowInsertEvent,
            onShowExpenseDetail = navigator::onShowExpenseDetail,
            onShowInsertEvent = navigator::onShowInsertEvent,
            onShowEditVenue = { navigator.onShowInsertVenue(venueId) },
            // TODO show / open to expenses for a given venueId
            onShowAllExpenses = navigator::onShowAllExpenses,
            // TODO show / open to events for a given venueId
            onShowAllEvents = navigator::onShowAllEvents,
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )
    }

    private fun viewExpensesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.ViewExpensesRoute,
    ): ViewExpensesComponent =
        ViewExpensesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertExpense = navigator::onShowInsertExpense,
            onShowExpenseDetail = navigator::onShowExpenseDetail,
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
        )

    private fun viewVenuesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.ViewVenuesRoute,
    ): ViewVenuesComponent =
        ViewVenuesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(RootNavigationRoute.InsertVenueRoute()) },
            onShowVenueDetail = { navigator.push(RootNavigationRoute.VenueDetailRoute(it)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )

    private fun viewEventsComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.ViewEventsRoute,
    ): ViewEventsComponent {
        return ViewEventsComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertEvent = { navigator.push(RootNavigationRoute.InsertEventRoute()) },
            onShowEventDetail = { navigator.push(RootNavigationRoute.EventDetailRoute(it)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )
    }

    private fun insertEventComponent(
        componentContext: ComponentContext,
        config: RootNavigationRoute.InsertEventRoute,
    ): InsertEventComponent {
        return InsertEventComponentImpl(
            componentContext = componentContext,
            existingEventId = config.existingEventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(RootNavigationRoute.InsertVenueRoute()) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )
    }

    private fun insertExpenseComponent(
        componentContext: ComponentContext,
        config: RootNavigationRoute.InsertExpenseRoute,
    ): InsertExpenseComponent {
        return InsertExpenseComponentImpl(
            componentContext = componentContext,
            existingExpenseId = config.existingExpenseId,
            eventId = config.eventId,
            venueId = config.venueId,
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            onShowInsertVenue = navigator::onShowInsertVenue,
            onShowInsertEvent = navigator::onShowInsertEvent,
        )
    }

    private fun insertVenueComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.InsertVenueRoute,
    ): InsertVenueComponent {
        return InsertVenueComponentImpl(
            componentContext = componentContext,
            existingVenueId = route.venueId,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )
    }

    private fun accountFeatureComponent(
        ctx: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.AccountRoute,
    ): AccountFeatureComponent {
        return AccountFeatureComponentImpl(
            componentContext = ctx,
            database = PokerTrackerDatabaseProvider.provide(),
            accountSettingsRepository = accountSettingsRepository,
            userRepository = userRepository,
            seeder = SampleDataSeederProvider.provide(),
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            onShowOnboarding = navigator::onShowOnboarding
        )
    }

    private fun statsFeatureComponent(
        ctx: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.StatsRoute,
    ): StatsFeaturePagerComponent {
        return StatsFeaturePagerComponentImpl(
            componentContext = ctx,
            componentFactory = StatsComponentFactoryProvider.provide()
        )
    }

    private fun plannerFeatureComponent(
        ctx: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.PlannerRoute,
    ): PlannerFeatureComponent {
        return PlannerFeatureComponentImpl(
            componentContext = ctx,
            eventsRepository = eventRepository,
            onCalendarDayClicked = navigator::onShowCalendarDayDetail,
            onFinished = navigator::pop,
            dispatchers = dispatchers
        )
    }
}
