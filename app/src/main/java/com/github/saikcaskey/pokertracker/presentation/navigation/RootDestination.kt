package com.github.saikcaskey.pokertracker.presentation.navigation

import com.github.saikcaskey.account.presentation.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.domain.component.MainComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.EventDetailComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.InsertEventComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.InsertExpenseComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.InsertVenueComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.VenueDetailComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewEventsComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewExpensesComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewVenuesComponent

/**
 * All of the Destinations that can be pushed onto the root stack
 */
sealed class RootDestination {
    class MainDestination(val component: MainComponent) : RootDestination()
    class EventDetailDestination(val component: EventDetailComponent) : RootDestination()
    class VenueDetailDestination(val component: VenueDetailComponent) : RootDestination()
    class ExpenseDetailDestination(val component: ExpenseDetailComponent) : RootDestination()
    class InsertEventDestination(val component: InsertEventComponent) : RootDestination()
    class InsertVenueDestination(val component: InsertVenueComponent) : RootDestination()
    class InsertExpenseDestination(val component: InsertExpenseComponent) : RootDestination()
    class ViewEventsDestination(val component: ViewEventsComponent) : RootDestination()
    class ViewVenuesDestination(val component: ViewVenuesComponent) : RootDestination()
    class ViewExpensesDestination(val component: ViewExpensesComponent) : RootDestination()
    class PlannerDayDetailDestination(val component: PlannerDayDetailComponent) : RootDestination()
    class SettingsDestination(val component: AccountFeatureComponent) : RootDestination()
}
