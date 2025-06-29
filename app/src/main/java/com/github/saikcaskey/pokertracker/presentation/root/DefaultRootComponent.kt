package com.github.saikcaskey.pokertracker.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DecomposeExperimentFlags
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.data.utils.defaultCoroutineDispatchersProviders
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.saikcaskey.github.pokertracker.shared.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.pokertracker.presentation.event.DefaultEventDetailComponent
import com.github.saikcaskey.pokertracker.presentation.event.DefaultInsertEventComponent
import com.github.saikcaskey.pokertracker.presentation.event.DefaultViewEventsComponent
import com.github.saikcaskey.pokertracker.presentation.event.EventDetailComponent
import com.github.saikcaskey.pokertracker.presentation.event.InsertEventComponent
import com.github.saikcaskey.pokertracker.presentation.event.ViewEventsComponent
import com.saikcaskey.github.pokertracker.shared.presentation.expense.DefaultExpenseDetailComponent
import com.github.saikcaskey.pokertracker.presentation.expense.DefaultInsertExpenseComponent
import com.saikcaskey.github.pokertracker.shared.presentation.expense.DefaultViewExpensesComponent
import com.github.saikcaskey.pokertracker.presentation.expense.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.presentation.expense.InsertExpenseComponent
import com.saikcaskey.github.pokertracker.shared.presentation.expense.ViewExpensesComponent
import com.saikcaskey.github.pokertracker.shared.presentation.main.DefaultMainComponent
import com.saikcaskey.github.pokertracker.shared.presentation.main.MainComponent
import com.github.saikcaskey.pokertracker.presentation.planner.DayDetailComponent
import com.saikcaskey.github.pokertracker.shared.presentation.planner.DefaultDayDetailComponent
import com.saikcaskey.github.pokertracker.shared.presentation.root.RootComponent.Child
import com.saikcaskey.github.pokertracker.shared.presentation.root.RootComponent.Child.*
import com.saikcaskey.github.pokertracker.shared.presentation.venue.DefaultInsertVenueComponent
import com.github.saikcaskey.pokertracker.presentation.venue.DefaultVenueDetailComponent
import com.saikcaskey.github.pokertracker.shared.presentation.venue.DefaultViewVenuesComponent
import com.saikcaskey.github.pokertracker.shared.presentation.venue.InsertVenueComponent
import com.saikcaskey.github.pokertracker.shared.presentation.venue.VenueDetailComponent
import com.github.saikcaskey.pokertracker.presentation.venue.ViewVenuesComponent
import com.saikcaskey.github.pokertracker.shared.presentation.root.RootComponent
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@OptIn(DelicateDecomposeApi::class, ExperimentalDecomposeApi::class)
class DefaultRootComponent(
    componentContext: ComponentContext,
    private val eventRepository: EventRepository,
    private val venueRepository: VenueRepository,
    private val expenseRepository: ExpenseRepository,
    private val dispatchers: CoroutineDispatchers,
) : RootComponent, ComponentContext by componentContext {

    init {
        DecomposeExperimentFlags.duplicateConfigurationsEnabled = true
    }

    private val navigator = StackNavigation<Config>()

    override val rootNavigationStack: Value<ChildStack<*, Child>> = childStack(
        source = navigator,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::buildChildComponent,
    )

    private fun buildChildComponent(config: Config, context: ComponentContext): Child = when (config) {
        is Config.Main -> Main(mainComponent(context))
        is Config.DayDetail -> DayDetail(dayDetailComponent(context, config.date))
        is Config.InsertEvent -> InsertEvent(insertEventComponent(context, config))
        is Config.InsertVenue -> InsertVenue(insertVenueComponent(context, config.venueId))
        is Config.InsertExpense -> InsertExpense(insertExpenseComponent(context, config))
        is Config.EventDetail -> EventDetail(eventDetailComponent(context, config.eventId))
        is Config.VenueDetail -> VenueDetail(venueDetailComponent(context, config.venueId))
        is Config.ExpenseDetail -> ExpenseDetail(expenseDetailComponent(context, config.expenseId))
        Config.ViewEvents -> ViewEvents(viewEventsComponent(context))
        Config.ViewVenues -> ViewVenues(viewVenuesComponent(context))
        Config.ViewExpenses -> ViewExpenses(viewExpensesComponent(context))
    }

    private fun mainComponent(componentContext: ComponentContext): MainComponent = DefaultMainComponent(
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
    )

    private fun eventDetailComponent(componentContext: ComponentContext, eventId: Long): EventDetailComponent =
        DefaultEventDetailComponent(
            componentContext = componentContext,
            eventId = eventId,
            dispatchers = defaultCoroutineDispatchersProviders,
            onShowExpenseDetail = { navigator.push(Config.ExpenseDetail(it)) },
            onShowInsertExpense = { eventId, venueId -> navigator.push(Config.InsertExpense(null, eventId, venueId)) },
            onShowVenueDetail = { venueId -> navigator.push(Config.VenueDetail(venueId)) },
            onShowEditEvent = { eventId -> navigator.push(Config.InsertEvent(eventId)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
            expenseRepository = expenseRepository,
            venueRepository = venueRepository,
        )

    private fun dayDetailComponent(componentContext: ComponentContext, date: LocalDate): DayDetailComponent =
        DefaultDayDetailComponent(
            componentContext = componentContext,
            date = date,
            dispatchers = defaultCoroutineDispatchersProviders,
            onShowEventDetail = { navigator.push(Config.EventDetail(it)) },
            onShowInsertEvent = { navigator.push(Config.InsertEvent(null, null, date)) },
            onFinished = navigator::pop,
            eventRepository = eventRepository,
        )

    private fun expenseDetailComponent(componentContext: ComponentContext, expenseId: Long): ExpenseDetailComponent =
        DefaultExpenseDetailComponent(
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
        DefaultVenueDetailComponent(
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
        DefaultViewExpensesComponent(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertExpense = { navigator.push(Config.InsertExpense()) },
            onShowExpenseDetail = { navigator.push(Config.ExpenseDetail(it)) },
            onFinished = navigator::pop,
            expenseRepository = expenseRepository,
        )

    private fun viewVenuesComponent(componentContext: ComponentContext): ViewVenuesComponent =
        DefaultViewVenuesComponent(
            componentContext = componentContext,
            dispatchers = dispatchers,
            onShowInsertVenue = { navigator.push(Config.InsertVenue()) },
            onShowVenueDetail = { navigator.push(Config.VenueDetail(it)) },
            onFinished = navigator::pop,
            venueRepository = venueRepository,
        )

    private fun viewEventsComponent(componentContext: ComponentContext): ViewEventsComponent =
        DefaultViewEventsComponent(
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
        DefaultInsertEventComponent(
            componentContext = componentContext,
            startDate = config.startDate ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
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
        DefaultInsertExpenseComponent(
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
        DefaultInsertVenueComponent(
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
