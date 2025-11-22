package com.github.saikcaskey.pokertracker.presentation.components.factory

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.factory.ComponentFactory
import com.github.saikcaskey.pokertracker.domain.presentation.NavigationRoute
import com.github.saikcaskey.pokertracker.domain.presentation.RootNavigator
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.UserRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponentImpl
import com.github.saikcaskey.pokertracker.presentation.MainComponent
import com.github.saikcaskey.pokertracker.presentation.MainComponentImpl
import com.github.saikcaskey.pokertracker.presentation.RootDestination
import com.github.saikcaskey.stats.presentation.EventDetailComponent
import com.github.saikcaskey.stats.presentation.EventDetailComponentImpl
import com.github.saikcaskey.stats.presentation.ExpenseDetailComponent
import com.github.saikcaskey.stats.presentation.ExpenseDetailComponentImpl
import com.github.saikcaskey.stats.presentation.InsertEventComponent
import com.github.saikcaskey.stats.presentation.InsertEventComponentImpl
import com.github.saikcaskey.stats.presentation.InsertExpenseComponent
import com.github.saikcaskey.stats.presentation.InsertExpenseComponentImpl
import com.github.saikcaskey.stats.presentation.InsertVenueComponent
import com.github.saikcaskey.stats.presentation.InsertVenueComponentImpl
import com.github.saikcaskey.stats.presentation.VenueDetailComponent
import com.github.saikcaskey.stats.presentation.VenueDetailComponentImpl
import com.github.saikcaskey.stats.presentation.ViewEventsComponent
import com.github.saikcaskey.stats.presentation.ViewEventsComponentImpl
import com.github.saikcaskey.stats.presentation.ViewExpensesComponent
import com.github.saikcaskey.stats.presentation.ViewExpensesComponentImpl
import com.github.saikcaskey.stats.presentation.ViewVenuesComponent
import com.github.saikcaskey.stats.presentation.ViewVenuesComponentImpl

class ComponentFactoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val navigator: RootNavigator,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val venueRepository: VenueRepository,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val userRepository: UserRepository,
) : ComponentFactory {

    override fun buildComponent(
        ctx: ComponentContext,
        route: NavigationRoute,
    ): RootDestination = when (route) {
        is NavigationRoute.MainRoute -> RootDestination.MainDestination(
            mainComponent(ctx, route)
        )

        is NavigationRoute.DayDetailRoute -> RootDestination.PlannerDayDetailDestination(
            dayDetailComponent(ctx, route)
        )

        is NavigationRoute.InsertEventRoute -> RootDestination.InsertEventDestination(
            insertEventComponent(ctx, route)
        )

        is NavigationRoute.InsertVenueRoute -> RootDestination.InsertVenueDestination(
            insertVenueComponent(ctx, route)
        )

        is NavigationRoute.InsertExpenseRoute -> RootDestination.InsertExpenseDestination(
            insertExpenseComponent(ctx, route)
        )

        is NavigationRoute.EventDetailRoute -> RootDestination.EventDetailDestination(
            eventDetailComponent(ctx, route)
        )

        is NavigationRoute.VenueDetailRoute -> RootDestination.VenueDetailDestination(
            venueDetailComponent(ctx, route)
        )

        is NavigationRoute.ViewEventsRoute -> RootDestination.ViewEventsDestination(
            viewEventsComponent(ctx, route)
        )

        is NavigationRoute.ViewVenuesRoute -> RootDestination.ViewVenuesDestination(
            viewVenuesComponent(ctx, route)
        )

        is NavigationRoute.ViewExpensesRoute -> RootDestination.ViewExpensesDestination(
            viewExpensesComponent(ctx, route)
        )

        is NavigationRoute.ExpenseDetailRoute -> RootDestination.ExpenseDetailDestination(
            expenseDetailComponent(ctx, route)
        )
    }

    private fun mainComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: NavigationRoute.MainRoute,
    ): MainComponent {
        return MainComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            userRepository = userRepository,
            accountSettingsRepository = accountSettingsRepository,
            rootNavigator = navigator,
        )
    }

    private fun eventDetailComponent(
        componentContext: ComponentContext,
        route: NavigationRoute.EventDetailRoute,
    ): EventDetailComponent {
        return EventDetailComponentImpl(
            componentContext = componentContext,
            eventId = route.eventId,
            dispatchers = dispatchers,
            onShowExpenseDetail = { navigator.push(NavigationRoute.ExpenseDetailRoute(it)) },
            onShowInsertExpense = { eventId, venueId ->
                navigator.push(
                    NavigationRoute.InsertExpenseRoute(
                        existingExpenseId = null,
                        eventId = eventId,
                        venueId = venueId
                    )
                )
            },
            onShowVenueDetail = { venueId ->
                navigator.push(
                    NavigationRoute.VenueDetailRoute(
                        venueId
                    )
                )
            },
            onShowEditEvent = { eventId ->
                navigator.push(NavigationRoute.InsertEventRoute(eventId))
            },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
        )
    }

    private fun dayDetailComponent(
        componentContext: ComponentContext,
        route: NavigationRoute.DayDetailRoute,
    ): PlannerDayDetailComponent {
        return PlannerDayDetailComponentImpl(
            componentContext = componentContext,
            date = route.date,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(NavigationRoute.EventDetailRoute(it)) },
            onShowInsertEvent = {
                navigator.push(
                    NavigationRoute.InsertEventRoute(
                        existingEventId = null,
                        venueId = null,
                    )
                )
            },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )
    }

    private fun expenseDetailComponent(
        componentContext: ComponentContext,
        route: NavigationRoute.ExpenseDetailRoute,
    ): ExpenseDetailComponent {
        val expenseId = route.expenseId
        return ExpenseDetailComponentImpl(
            componentContext = componentContext,
            expenseId = expenseId,
            dispatchers = dispatchers,
            onShowEditExpense = { navigator.push(NavigationRoute.InsertExpenseRoute(expenseId)) },
            onShowEventDetail = { eventId ->
                navigator.push(
                    NavigationRoute.EventDetailRoute(
                        eventId
                    )
                )
            },
            onShowVenueDetail = { venueId ->
                navigator.push(
                    NavigationRoute.VenueDetailRoute(
                        venueId
                    )
                )
            },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
        )
    }

    private fun venueDetailComponent(
        componentContext: ComponentContext,
        route: NavigationRoute.VenueDetailRoute,
    ): VenueDetailComponent {
        val venueId = route.venueId
        return VenueDetailComponentImpl(
            componentContext = componentContext,
            venueId = venueId,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(NavigationRoute.EventDetailRoute(it)) },
            onShowInsertEvent = { navigator.push(NavigationRoute.InsertEventRoute(venueId = venueId)) },
            onShowEditVenue = { navigator.push(NavigationRoute.InsertVenueRoute(venueId = venueId)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )
    }

    private fun viewExpensesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: NavigationRoute.ViewExpensesRoute,
    ): ViewExpensesComponent {
        return ViewExpensesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertExpense = { navigator.push(NavigationRoute.InsertExpenseRoute()) },
            onShowExpenseDetail = { navigator.push(NavigationRoute.ExpenseDetailRoute(it)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
        )
    }

    private fun viewVenuesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: NavigationRoute.ViewVenuesRoute,
    ): ViewVenuesComponent {
        return ViewVenuesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(NavigationRoute.InsertVenueRoute()) },
            onShowVenueDetail = { navigator.push(NavigationRoute.VenueDetailRoute(it)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )
    }

    private fun viewEventsComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: NavigationRoute.ViewEventsRoute,
    ): ViewEventsComponent {
        return ViewEventsComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertEvent = { navigator.push(NavigationRoute.InsertEventRoute()) },
            onShowEventDetail = { navigator.push(NavigationRoute.EventDetailRoute(it)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )
    }

    private fun insertEventComponent(
        componentContext: ComponentContext,
        config: NavigationRoute.InsertEventRoute,
    ): InsertEventComponent {
        return InsertEventComponentImpl(
            componentContext = componentContext,
            existingEventId = config.existingEventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(NavigationRoute.InsertVenueRoute()) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )
    }

    private fun insertExpenseComponent(
        componentContext: ComponentContext,
        config: NavigationRoute.InsertExpenseRoute,
    ): InsertExpenseComponent {
        return InsertExpenseComponentImpl(
            componentContext = componentContext,
            existingExpenseId = config.existingExpenseId,
            eventId = config.eventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            onShowInsertVenue = { navigator.push(NavigationRoute.InsertVenueRoute()) },
            onShowInsertEvent = { navigator.push(NavigationRoute.InsertEventRoute(venueId = it)) },
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
        )
    }

    private fun insertVenueComponent(
        componentContext: ComponentContext,
        route: NavigationRoute.InsertVenueRoute,
    ): InsertVenueComponent {
        return InsertVenueComponentImpl(
            componentContext = componentContext,
            existingVenueId = route.venueId,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )
    }
}
