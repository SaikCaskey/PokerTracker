package com.github.saikcaskey.pokertracker.presentation.components.factory

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.factory.RootComponentFactory
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.UserRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponentImpl
import com.github.saikcaskey.pokertracker.presentation.navigation.RootDestination
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigationRoute
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.domain.component.MainComponent
import com.github.saikcaskey.pokertracker.presentation.components.MainComponentImpl
import com.github.saikcaskey.stats.domain.components.EventDetailComponent
import com.github.saikcaskey.stats.presentation.components.EventDetailComponentImpl
import com.github.saikcaskey.stats.domain.components.ExpenseDetailComponent
import com.github.saikcaskey.stats.presentation.components.ExpenseDetailComponentImpl
import com.github.saikcaskey.stats.domain.components.InsertEventComponent
import com.github.saikcaskey.stats.presentation.components.InsertEventComponentImpl
import com.github.saikcaskey.stats.domain.components.InsertExpenseComponent
import com.github.saikcaskey.stats.presentation.components.InsertExpenseComponentImpl
import com.github.saikcaskey.stats.domain.components.InsertVenueComponent
import com.github.saikcaskey.stats.presentation.components.InsertVenueComponentImpl
import com.github.saikcaskey.stats.domain.components.VenueDetailComponent
import com.github.saikcaskey.stats.presentation.components.VenueDetailComponentImpl
import com.github.saikcaskey.stats.domain.components.ViewEventsComponent
import com.github.saikcaskey.stats.presentation.components.ViewEventsComponentImpl
import com.github.saikcaskey.stats.domain.components.ViewExpensesComponent
import com.github.saikcaskey.stats.presentation.components.ViewExpensesComponentImpl
import com.github.saikcaskey.stats.domain.components.ViewVenuesComponent
import com.github.saikcaskey.stats.presentation.components.ViewVenuesComponentImpl

class RootComponentFactoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val navigator: RootNavigator,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val venueRepository: VenueRepository,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val userRepository: UserRepository,
) : RootComponentFactory {

    override fun buildComponent(
        ctx: ComponentContext,
        route: RootNavigationRoute,
    ): RootDestination = when (route) {
        is RootNavigationRoute.MainRoute -> RootDestination.MainDestination(
            mainComponent(ctx, route)
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

    private fun mainComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: RootNavigationRoute.MainRoute,
    ): MainComponent {
        return MainComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            accountSettingsRepository = accountSettingsRepository,
            userRepository = userRepository,
            onShowAllEvents = { navigator.push(RootNavigationRoute.ViewEventsRoute) },
            onShowAllVenues = { navigator.push(RootNavigationRoute.ViewVenuesRoute) },
            onShowAllExpenses = { navigator.push(RootNavigationRoute.ViewExpensesRoute) },
            onShowEventDetail = { eventId ->
                navigator.push(
                    RootNavigationRoute.EventDetailRoute(
                        eventId
                    )
                )
            },
            onShowVenueDetail = { venueId ->
                navigator.push(
                    RootNavigationRoute.VenueDetailRoute(
                        venueId
                    )
                )
            },
            onShowExpenseDetail = { expenseId ->
                navigator.push(
                    RootNavigationRoute.ExpenseDetailRoute(
                        expenseId
                    )
                )
            },
            onShowInsertVenue = { venueId ->
                navigator.push(
                    RootNavigationRoute.InsertVenueRoute(
                        venueId
                    )
                )
            },
            onShowInsertEvent = { id, venueId, date ->
                navigator.push(RootNavigationRoute.InsertEventRoute(id, venueId, date))
            },
            onShowInsertExpense = { id, venueId, eventId ->
                navigator.push(RootNavigationRoute.InsertExpenseRoute(id, venueId, eventId))
            },
            onShowCalendarDayDetail = { date, hasEvent ->
                navigator.push(
                    if (hasEvent) {
                        RootNavigationRoute.DayDetailRoute(date)
                    } else {
                        RootNavigationRoute.InsertEventRoute(
                            existingEventId = null,
                            venueId = null,
                            startDate = date
                        )
                    }
                )
            },
        )
    }

    private fun eventDetailComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.EventDetailRoute,
    ): EventDetailComponent =
        EventDetailComponentImpl(
            componentContext = componentContext,
            eventId = route.eventId,
            dispatchers = dispatchers,
            onShowExpenseDetail = { navigator.push(RootNavigationRoute.ExpenseDetailRoute(it)) },
            onShowInsertExpense = { eventId, venueId ->
                navigator.push(
                    RootNavigationRoute.InsertExpenseRoute(
                        existingExpenseId = null,
                        eventId = eventId,
                        venueId = venueId
                    )
                )
            },
            onShowVenueDetail = { venueId ->
                navigator.push(
                    RootNavigationRoute.VenueDetailRoute(
                        venueId
                    )
                )
            },
            onShowEditEvent = { eventId ->
                navigator.push(
                    RootNavigationRoute.InsertEventRoute(
                        eventId
                    )
                )
            },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
        )

    private fun dayDetailComponent(
        componentContext: ComponentContext,
        route: RootNavigationRoute.DayDetailRoute,
    ): PlannerDayDetailComponent {
        return PlannerDayDetailComponentImpl(
            componentContext = componentContext,
            date = route.date,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(RootNavigationRoute.EventDetailRoute(it)) },
            onShowInsertEvent = {
                navigator.push(
                    RootNavigationRoute.InsertEventRoute(
                        existingEventId = null,
                        venueId = null,
                        startDate = route.date
                    )
                )
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
            onShowEditExpense = { navigator.push(RootNavigationRoute.InsertExpenseRoute(expenseId)) },
            onShowEventDetail = { eventId ->
                navigator.push(
                    RootNavigationRoute.EventDetailRoute(
                        eventId
                    )
                )
            },
            onShowVenueDetail = { venueId ->
                navigator.push(
                    RootNavigationRoute.VenueDetailRoute(
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
        route: RootNavigationRoute.VenueDetailRoute,
    ): VenueDetailComponent {
        val venueId = route.venueId
        return VenueDetailComponentImpl(
            componentContext = componentContext,
            venueId = venueId,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(RootNavigationRoute.EventDetailRoute(it)) },
            onShowInsertEvent = { navigator.push(RootNavigationRoute.InsertEventRoute(venueId = venueId)) },
            onShowEditVenue = { navigator.push(RootNavigationRoute.InsertVenueRoute(venueId = venueId)) },
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
            onShowInsertExpense = { navigator.push(RootNavigationRoute.InsertExpenseRoute()) },
            onShowExpenseDetail = { navigator.push(RootNavigationRoute.ExpenseDetailRoute(it)) },
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
            startDate = config.startDate ?: nowAsLocalDateTime().date,
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
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            onShowInsertVenue = { navigator.push(RootNavigationRoute.InsertVenueRoute()) },
            onShowInsertEvent = { navigator.push(RootNavigationRoute.InsertEventRoute(venueId = it)) },
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
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
}
