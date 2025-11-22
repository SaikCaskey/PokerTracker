package com.github.saikcaskey.stats.presentation.components.factory

import com.arkivanov.decompose.ComponentContext
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import com.github.saikcaskey.pokertracker.domain.presentation.component.FeatureComponent
import com.github.saikcaskey.pokertracker.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.domain.repository.EventRepository
import com.github.saikcaskey.pokertracker.domain.repository.ExpenseRepository
import com.github.saikcaskey.pokertracker.domain.repository.VenueRepository
import com.github.saikcaskey.stats.factory.StatsComponentFactory
import com.github.saikcaskey.stats.domain.components.ViewEventsComponent
import com.github.saikcaskey.stats.presentation.components.ViewEventsComponentImpl
import com.github.saikcaskey.stats.domain.components.ViewExpensesComponent
import com.github.saikcaskey.stats.presentation.components.ViewExpensesComponentImpl
import com.github.saikcaskey.stats.domain.components.ViewVenuesComponent
import com.github.saikcaskey.stats.presentation.components.ViewVenuesComponentImpl
import com.github.saikcaskey.stats.presentation.navigation.StatsPagerNavigationRoute

class StatsComponentFactoryImpl(
    private val dispatchers: CoroutineDispatchers,
    private val navigator: RootNavigator,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    private val venueRepository: VenueRepository,
) : StatsComponentFactory {

    override fun buildChildComponent(
        ctx: ComponentContext,
        route: StatsPagerNavigationRoute,
    ): FeatureComponent = when (route) {
        StatsPagerNavigationRoute.EventsRoute -> viewEventsComponent(ctx, route)
        StatsPagerNavigationRoute.ExpensesRoute -> viewExpensesComponent(ctx, route)
        StatsPagerNavigationRoute.VenuesRoute -> viewVenuesComponent(ctx, route)
    }

    private fun viewExpensesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: StatsPagerNavigationRoute,
    ): ViewExpensesComponent =
        ViewExpensesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            expenseRepository = expenseRepository,
            onFinished = navigator::pop,
            onShowInsertExpense = navigator::onShowInsertExpense,
            onShowExpenseDetail = navigator::onShowExpenseDetail,
        )

    private fun viewVenuesComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: StatsPagerNavigationRoute,
    ): ViewVenuesComponent =
        ViewVenuesComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            venueRepository = venueRepository,
            onFinished = navigator::pop,
            onShowInsertVenue = navigator::onShowInsertVenue,
            onShowVenueDetail = navigator::onShowVenueDetail,
        )

    private fun viewEventsComponent(
        componentContext: ComponentContext,
        @Suppress("unused") route: StatsPagerNavigationRoute,
    ): ViewEventsComponent {
        return ViewEventsComponentImpl(
            componentContext = componentContext,
            dispatchers = dispatchers,
            eventRepository = eventRepository,
            onShowInsertEvent = { navigator.onShowInsertEvent() },
            onShowEventDetail = navigator::onShowEventDetail,
            onFinished = navigator::pop,
        )
    }

}
