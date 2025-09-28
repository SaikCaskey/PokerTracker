package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DecomposeExperimentFlags
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.stats.presentation.EventDetailComponent
import com.github.saikcaskey.stats.presentation.ExpenseDetailComponent
import com.github.saikcaskey.stats.presentation.InsertEventComponent
import com.github.saikcaskey.stats.presentation.InsertExpenseComponent
import com.github.saikcaskey.stats.presentation.InsertVenueComponent
import com.github.saikcaskey.stats.presentation.VenueDetailComponent
import com.github.saikcaskey.stats.presentation.ViewEventsComponent
import com.github.saikcaskey.stats.presentation.ViewExpensesComponent
import com.github.saikcaskey.stats.presentation.ViewVenuesComponent
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.AccountSettingsRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.stats.presentation.EventDetailComponentImpl
import com.github.saikcaskey.stats.presentation.InsertEventComponentImpl
import com.github.saikcaskey.stats.presentation.ViewEventsComponentImpl
import com.github.saikcaskey.stats.presentation.ExpenseDetailComponentImpl
import com.github.saikcaskey.stats.presentation.InsertExpenseComponentImpl
import com.github.saikcaskey.stats.presentation.ViewExpensesComponentImpl
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponentImpl
import com.github.saikcaskey.stats.presentation.InsertVenueComponentImpl
import com.github.saikcaskey.stats.presentation.VenueDetailComponentImpl
import com.github.saikcaskey.stats.presentation.ViewVenuesComponentImpl
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val accountSettingsRepository: AccountSettingsRepository,
    private val venueRepository: VenueRepository,
    private val dispatchers: CoroutineDispatchers,
) : RootComponent, ComponentContext by componentContext {

    init {
        // Enable duplicate destinations in the stack
        DecomposeExperimentFlags.duplicateConfigurationsEnabled = true
    }

    private val navigator = StackNavigation<Config>()

    override val rootNavigationStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigator,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::buildChildComponent,
    )

    private fun buildChildComponent(config: Config, context: ComponentContext): RootComponent.Child = when (config) {
        is Config.Main -> RootComponent.Child.Main(mainComponent(context))
        is Config.DayDetail -> RootComponent.Child.PlannerDayDetail(
            dayDetailComponent(
                context,
                config.date
            )
        )
        is Config.InsertEvent -> RootComponent.Child.InsertEvent(
            insertEventComponent(
                context,
                config
            )
        )
        is Config.InsertVenue -> RootComponent.Child.InsertVenue(
            insertVenueComponent(
                context,
                config.venueId
            )
        )
        is Config.InsertExpense -> RootComponent.Child.InsertExpense(
            insertExpenseComponent(
                context,
                config
            )
        )
        is Config.EventDetail -> RootComponent.Child.EventDetail(
            eventDetailComponent(
                context,
                config.eventId
            )
        )
        is Config.VenueDetail -> RootComponent.Child.VenueDetail(
            venueDetailComponent(
                context,
                config.venueId
            )
        )
        is Config.ExpenseDetail -> RootComponent.Child.ExpenseDetail(
            expenseDetailComponent(
                context,
                config.expenseId
            )
        )
        is Config.ViewEvents -> RootComponent.Child.ViewEvents(viewEventsComponent(context))
        is Config.ViewVenues -> RootComponent.Child.ViewVenues(viewVenuesComponent(context))
        is Config.ViewExpenses -> RootComponent.Child.ViewExpenses(viewExpensesComponent(context))
    }

    private fun mainComponent(componentContext: ComponentContext): MainComponent = MainComponentImpl(
        componentContext = componentContext,
        dispatchers = dispatchers,
        onShowAllEvents = { navigator.push(Config.ViewEvents) },
        onShowAllVenues = { navigator.push(Config.ViewVenues) },
        onShowAllExpenses = { navigator.push(Config.ViewExpenses) },
        onShowEventDetail = { eventId -> navigator.push(Config.EventDetail(eventId)) },
        onShowVenueDetail = { venueId -> navigator.push(Config.VenueDetail(venueId)) },
        onShowExpenseDetail = { expenseId -> navigator.push(Config.ExpenseDetail(expenseId)) },
        onShowInsertEvent = { id, venueId, date -> navigator.push(Config.InsertEvent(id, venueId, date)) },
        onShowInsertVenue = { venueId -> navigator.push(Config.InsertVenue(venueId)) },
        onShowInsertExpense = { id, venueId, eventId -> navigator.push(Config.InsertExpense(id, venueId, eventId)) },
        onShowCalendarDayDetail = { date, hasEvent ->
            navigator.push(if (hasEvent) Config.DayDetail(date) else Config.InsertEvent(null, null, date))
        },
        eventRepository = eventRepository,
        venueRepository = venueRepository,
        expenseRepository = expenseRepository,
        accountSettingsRepository = accountSettingsRepository,
    )

    private fun eventDetailComponent(componentContext: ComponentContext, eventId: Long): EventDetailComponent =
        EventDetailComponentImpl(
            componentContext = componentContext,
            eventId = eventId,
            dispatchers = dispatchers,
            onShowExpenseDetail = { navigator.push(Config.ExpenseDetail(it)) },
            onShowInsertExpense = { eventId, venueId -> navigator.push(Config.InsertExpense(null, eventId, venueId)) },
            onShowVenueDetail = { venueId -> navigator.push(Config.VenueDetail(venueId)) },
            onShowEditEvent = { eventId -> navigator.push(Config.InsertEvent(eventId)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
        )

    private fun dayDetailComponent(componentContext: ComponentContext, date: LocalDate): PlannerDayDetailComponent =
        PlannerDayDetailComponentImpl(
            componentContext = componentContext,
            date = date,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(Config.EventDetail(it)) },
            onShowInsertEvent = { navigator.push(Config.InsertEvent(null, null, date)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )

    private fun expenseDetailComponent(componentContext: ComponentContext, expenseId: Long): ExpenseDetailComponent =
        ExpenseDetailComponentImpl(
            componentContext = componentContext,
            expenseId = expenseId,
            dispatchers = dispatchers,
            onShowEditExpense = { navigator.push(Config.InsertExpense(expenseId)) },
            onShowEventDetail = { eventId -> navigator.push(Config.EventDetail(eventId)) },
            onShowVenueDetail = { venueId -> navigator.push(Config.VenueDetail(venueId)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
        )

    private fun venueDetailComponent(componentContext: ComponentContext, venueId: Long): VenueDetailComponent =
        VenueDetailComponentImpl(
            componentContext = componentContext,
            venueId = venueId,
            dispatchers = dispatchers,
            onShowEventDetail = { navigator.push(Config.EventDetail(it)) },
            onShowInsertEvent = { navigator.push(Config.InsertEvent(venueId = venueId)) },
            onShowEditVenue = { navigator.push(Config.InsertVenue(venueId = venueId)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )

    private fun viewExpensesComponent(componentContext: ComponentContext): ViewExpensesComponent =
        ViewExpensesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertExpense = { navigator.push(Config.InsertExpense()) },
            onShowExpenseDetail = { navigator.push(Config.ExpenseDetail(it)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
        )

    private fun viewVenuesComponent(componentContext: ComponentContext): ViewVenuesComponent =
        ViewVenuesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(Config.InsertVenue()) },
            onShowVenueDetail = { navigator.push(Config.VenueDetail(it)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )

    private fun viewEventsComponent(componentContext: ComponentContext): ViewEventsComponent =
        ViewEventsComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertEvent = { navigator.push(Config.InsertEvent()) },
            onShowEventDetail = { navigator.push(Config.EventDetail(it)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )

    private fun insertEventComponent(
        componentContext: ComponentContext,
        config: Config.InsertEvent,
    ): InsertEventComponent =
        InsertEventComponentImpl(
            componentContext = componentContext,
            startDate = config.startDate ?: nowAsLocalDateTime().date,
            existingEventId = config.existingEventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(Config.InsertVenue()) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
            eventRepository = eventRepository,
        )

    private fun insertExpenseComponent(
        componentContext: ComponentContext,
        config: Config.InsertExpense,
    ): InsertExpenseComponent =
        InsertExpenseComponentImpl(
            componentContext = componentContext,
            existingExpenseId = config.existingExpenseId,
            eventId = config.eventId,
            venueId = config.venueId,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            onShowInsertVenue = { navigator.push(Config.InsertVenue()) },
            onShowInsertEvent = { navigator.push(Config.InsertEvent(venueId = it)) },
            expenseRepository = expenseRepository,
            eventRepository = eventRepository,
            venueRepository = venueRepository,
        )

    private fun insertVenueComponent(
        componentContext: ComponentContext,
        venueId: Long? = null,
    ): InsertVenueComponent =
        InsertVenueComponentImpl(
            componentContext = componentContext,
            existingVenueId = venueId,
            dispatchers = dispatchers,
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )

    override fun onBackClicked(toIndex: Int) = navigator.popTo(index = toIndex)

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Main : Config

        @Serializable
        data class DayDetail(val date: LocalDate) : Config

        @Serializable
        data class VenueDetail(val venueId: Long) : Config

        @Serializable
        data class EventDetail(val eventId: Long) : Config

        @Serializable
        data class ExpenseDetail(val expenseId: Long) : Config

        @Serializable
        data class InsertVenue(val venueId: Long? = null) : Config

        @Serializable
        data class InsertEvent(
            val existingEventId: Long? = null,
            val venueId: Long? = null,
            val startDate: LocalDate? = null,
        ) : Config

        @Serializable
        data class InsertExpense(
            val existingExpenseId: Long? = null,
            val eventId: Long? = null,
            val venueId: Long? = null,
        ) : Config

        @Serializable
        data object ViewVenues : Config

        @Serializable
        data object ViewEvents : Config

        @Serializable
        data object ViewExpenses : Config
    }
}
