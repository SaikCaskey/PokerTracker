package com.github.saikcaskey.pokertracker.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.domain.components.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.domain.components.EventDetailComponent
import com.github.saikcaskey.pokertracker.domain.components.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.domain.components.InsertEventComponent
import com.github.saikcaskey.pokertracker.domain.components.InsertExpenseComponent
import com.github.saikcaskey.pokertracker.domain.components.InsertVenueComponent
import com.github.saikcaskey.pokertracker.domain.components.MainComponent
import com.github.saikcaskey.pokertracker.domain.components.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.domain.components.VenueDetailComponent
import com.github.saikcaskey.stats.domain.ViewEventsComponent
import com.github.saikcaskey.stats.domain.ViewExpensesComponent
import com.github.saikcaskey.stats.domain.ViewVenuesComponent

/**
 * The navigation "Root" for the app's decompose structure.
 * All of these children can be pushed onto the Root navigator's Stack.
 */
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
        class PlannerDayDetail(val component: PlannerDayDetailComponent) : Child()
        class Settings(val component: AccountFeatureComponent) : Child()
    }
}
