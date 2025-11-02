package com.github.saikcaskey.pokertracker.presentation

import com.github.saikcaskey.account.presentation.AccountFeatureComponent
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
