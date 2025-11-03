package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.account.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.UserRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponentImpl
import com.github.saikcaskey.pokertracker.presentation.NavigationRoute.*
import com.github.saikcaskey.pokertracker.presentation.RootDestination.*
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
        is MainRoute -> MainDestination(mainComponent(ctx, route))
        is DayDetailRoute -> PlannerDayDetailDestination(dayDetailComponent(ctx, route))
        is InsertEventRoute -> InsertEventDestination(insertEventComponent(ctx, route))
        is InsertVenueRoute -> InsertVenueDestination(insertVenueComponent(ctx, route))
        is InsertExpenseRoute -> InsertExpenseDestination(insertExpenseComponent(ctx, route))
        is EventDetailRoute -> EventDetailDestination(eventDetailComponent(ctx, route))
        is VenueDetailRoute -> VenueDetailDestination(venueDetailComponent(ctx, route))
        is ViewEventsRoute -> ViewEventsDestination(viewEventsComponent(ctx, route))
        is ViewVenuesRoute -> ViewVenuesDestination(viewVenuesComponent(ctx, route))
        is ViewExpensesRoute -> ViewExpensesDestination(viewExpensesComponent(ctx, route))
        is ExpenseDetailRoute -> ExpenseDetailDestination(expenseDetailComponent(ctx, route))
    }

    private fun mainComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: MainRoute,
    ): MainComponent {
        return MainComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            accountSettingsRepository = accountSettingsRepository,
            userRepository = userRepository,
            onShowAllEvents = { navigator.push(ViewEventsRoute) },
            onShowAllVenues = { navigator.push(ViewVenuesRoute) },
            onShowAllExpenses = { navigator.push(ViewExpensesRoute) },
            onShowEventDetail = { eventId -> navigator.push(EventDetailRoute(eventId)) },
            onShowVenueDetail = { venueId -> navigator.push(VenueDetailRoute(venueId)) },
            onShowExpenseDetail = { expenseId -> navigator.push(ExpenseDetailRoute(expenseId)) },
            onShowInsertVenue = { venueId -> navigator.push(InsertVenueRoute(venueId)) },
            onShowInsertEvent = { id, venueId, date ->
                navigator.push(InsertEventRoute(id, venueId, date))
            },
            onShowInsertExpense = { id, venueId, eventId ->
                navigator.push(InsertExpenseRoute(id, venueId, eventId))
            },
            onShowCalendarDayDetail = { date, hasEvent ->
                navigator.push(
                    if (hasEvent) {
                        DayDetailRoute(date)
                    } else {
                        InsertEventRoute(
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
        route: EventDetailRoute,
    ): EventDetailComponent =
        EventDetailComponentImpl(
            componentContext = componentContext,
            eventId = route.eventId,
            dispatchers = dispatchers,
            onShowExpenseDetail = { navigator.push(ExpenseDetailRoute(it)) },
            onShowInsertExpense = { eventId, venueId ->
                navigator.push(
                    InsertExpenseRoute(
                        existingExpenseId = null,
                        eventId = eventId,
                        venueId = venueId
                    )
                )
            },
            onShowVenueDetail = { venueId -> navigator.push(VenueDetailRoute(venueId)) },
            onShowEditEvent = { eventId -> navigator.push(InsertEventRoute(eventId)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
        )

    private fun dayDetailComponent(
        componentContext: ComponentContext,
        route: DayDetailRoute,
    ): PlannerDayDetailComponent {
        return PlannerDayDetailComponentImpl(
            componentContext = componentContext,
            date = route.date,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(EventDetailRoute(it)) },
            onShowInsertEvent = {
                navigator.push(
                    InsertEventRoute(
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
        route: ExpenseDetailRoute,
    ): ExpenseDetailComponent {
        val expenseId = route.expenseId
        return ExpenseDetailComponentImpl(
            componentContext = componentContext,
            expenseId = expenseId,
            dispatchers = dispatchers,
            onShowEditExpense = { navigator.push(InsertExpenseRoute(expenseId)) },
            onShowEventDetail = { eventId -> navigator.push(EventDetailRoute(eventId)) },
            onShowVenueDetail = { venueId -> navigator.push(VenueDetailRoute(venueId)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
        )
    }

    private fun venueDetailComponent(
        componentContext: ComponentContext,
        route: VenueDetailRoute,
    ): VenueDetailComponent {
        val venueId = route.venueId
        return VenueDetailComponentImpl(
            componentContext = componentContext,
            venueId = venueId,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(EventDetailRoute(it)) },
            onShowInsertEvent = { navigator.push(InsertEventRoute(venueId = venueId)) },
            onShowEditVenue = { navigator.push(InsertVenueRoute(venueId = venueId)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )
    }

    private fun viewExpensesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: ViewExpensesRoute,
    ): ViewExpensesComponent =
        ViewExpensesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertExpense = { navigator.push(InsertExpenseRoute()) },
            onShowExpenseDetail = { navigator.push(ExpenseDetailRoute(it)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
        )

    private fun viewVenuesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: ViewVenuesRoute,
    ): ViewVenuesComponent =
        ViewVenuesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(InsertVenueRoute()) },
            onShowVenueDetail = { navigator.push(VenueDetailRoute(it)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )

    private fun viewEventsComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: ViewEventsRoute,
    ): ViewEventsComponent {
        return ViewEventsComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertEvent = { navigator.push(InsertEventRoute()) },
            onShowEventDetail = { navigator.push(EventDetailRoute(it)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )
    }

    private fun insertEventComponent(
        componentContext: ComponentContext,
        config: InsertEventRoute,
    ): InsertEventComponent {
        return InsertEventComponentImpl(
            componentContext = componentContext,
            startDate = config.startDate ?: nowAsLocalDateTime().date,
            existingEventId = config.existingEventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(InsertVenueRoute()) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )
    }

    private fun insertExpenseComponent(
        componentContext: ComponentContext,
        config: InsertExpenseRoute,
    ): InsertExpenseComponent {
        return InsertExpenseComponentImpl(
            componentContext = componentContext,
            existingExpenseId = config.existingExpenseId,
            eventId = config.eventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            onShowInsertVenue = { navigator.push(InsertVenueRoute()) },
            onShowInsertEvent = { navigator.push(InsertEventRoute(venueId = it)) },
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
        )
    }

    private fun insertVenueComponent(
        componentContext: ComponentContext,
        route: InsertVenueRoute,
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
