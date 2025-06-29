package com.saikcaskey.github.pokertracker.shared.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.presentation.event.EventDetailComponent
import com.github.saikcaskey.pokertracker.presentation.event.InsertEventComponent
import com.github.saikcaskey.pokertracker.presentation.event.ViewEventsComponent
import com.github.saikcaskey.pokertracker.presentation.expense.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.presentation.expense.InsertExpenseComponent
import com.saikcaskey.github.pokertracker.shared.presentation.expense.ViewExpensesComponent
import com.saikcaskey.github.pokertracker.shared.presentation.main.MainComponent
import com.github.saikcaskey.pokertracker.presentation.planner.DayDetailComponent
import com.saikcaskey.github.pokertracker.shared.presentation.venue.InsertVenueComponent
import com.saikcaskey.github.pokertracker.shared.presentation.venue.VenueDetailComponent
import com.github.saikcaskey.pokertracker.presentation.venue.ViewVenuesComponent

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
