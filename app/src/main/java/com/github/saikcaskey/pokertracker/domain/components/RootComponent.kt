package com.github.saikcaskey.pokertracker.domain.components

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val rootNavigationStack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Main(val component: MainComponent) : Child()
        class EventDetail(val component: EventDetailComponent) : Child()
        class VenueDetail(val component: VenueDetailComponent) : Child()
        class ExpenseDetail(val component: ExpenseDetailComponent) : Child()
        class InsertEvent(val component: InsertEventComponent) : Child()
        class InsertVenue(val component: InsertVenueComponent) : Child()
        class InsertExpense(val component: InsertExpenseComponent) : Child()
        class ViewEvents(val component: ViewEventsComponent) : Child()
        class ViewVenues(val component: ViewVenuesComponent) : Child()
        class ViewExpenses(val component: ViewExpensesComponent) : Child()
        class DayDetail(val component: DayDetailComponent) : Child()
    }
}
