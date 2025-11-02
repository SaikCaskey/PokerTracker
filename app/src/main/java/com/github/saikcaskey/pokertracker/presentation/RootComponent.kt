package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.account.presentation.AccountFeatureComponent
import com.github.saikcaskey.stats.presentation.EventDetailComponent
import com.github.saikcaskey.stats.presentation.ExpenseDetailComponent
import com.github.saikcaskey.stats.presentation.InsertEventComponent
import com.github.saikcaskey.stats.presentation.InsertExpenseComponent
import com.github.saikcaskey.stats.presentation.InsertVenueComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerDayDetailComponent
import com.github.saikcaskey.stats.presentation.VenueDetailComponent
import com.github.saikcaskey.stats.presentation.ViewEventsComponent
import com.github.saikcaskey.stats.presentation.ViewExpensesComponent
import com.github.saikcaskey.stats.presentation.ViewVenuesComponent

/**
 * The navigation "Root" for the app's decompose structure.
 * All of these children can be pushed onto the Root navigator's Stack.
 */
interface RootComponent {

    val rootNavigationStack: Value<ChildStack<*, RootDestination>>

    fun onBackClicked(toIndex: Int)

}
