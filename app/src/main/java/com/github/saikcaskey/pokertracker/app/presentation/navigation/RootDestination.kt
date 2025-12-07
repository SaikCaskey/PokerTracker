package com.github.saikcaskey.pokertracker.app.presentation.navigation

import com.github.saikcaskey.pokertracker.feature.account.presentation.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.feature.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.pokertracker.feature.planner.presentation.PlannerFeatureComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.EventDetailComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ExpenseDetailComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertEventComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertExpenseComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.InsertVenueComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.StatsFeaturePagerComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.VenueDetailComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewEventsComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewExpensesComponent
import com.github.saikcaskey.pokertracker.feature.stats.components.ViewVenuesComponent

/**
 * All of the Destinations that can be pushed onto the root stack
 */
sealed class RootDestination {
    class DashboardDestination(val component: DashboardFeatureComponent) : RootDestination()
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
    class PlannerDestination(val component: PlannerFeatureComponent) : RootDestination()
    class StatsDestination(val component: StatsFeaturePagerComponent) : RootDestination()
    class AccountDestination(val component: AccountFeatureComponent) : RootDestination()
}
