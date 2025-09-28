package com.github.saikcaskey.pokertracker.ui.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.*
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.InsertVenueContent
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.VenueDetailContent
import com.github.saikcaskey.stats.presentation.ViewVenuesContent
import com.github.saikcaskey.pokertracker.ui.root.RootComponent
import com.github.saikcaskey.pokertracker.ui.root.RootComponent.Child
import com.github.saikcaskey.pokertracker.ui.main.MainContent
import com.github.saikcaskey.pokertracker.ui_compose.common.theme.AppTheme
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventDetailContent
import com.github.saikcaskey.pokertracker.ui_compose.components.event.InsertEventContent
import com.github.saikcaskey.stats.presentation.ViewEventsContent
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ExpenseDetailContent
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.InsertExpenseContent
import com.github.saikcaskey.stats.presentation.ViewExpensesContent
import com.github.saikcaskey.pokertracker.planner.composables.PlannerDayDetailContent
import com.github.saikcaskey.account.presentation.AccountFeatureContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    AppTheme {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            Children(
                stack = component.rootNavigationStack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val instance = it.instance) {
                    is Child.Main -> MainContent(component = instance.component)
                    is Child.ViewExpenses -> ViewExpensesContent(component = instance.component)
                    is Child.ViewEvents -> ViewEventsContent(component = instance.component)
                    is Child.ViewVenues -> ViewVenuesContent(component = instance.component)
                    is Child.EventDetail -> EventDetailContent(component = instance.component)
                    is Child.ExpenseDetail -> ExpenseDetailContent(component = instance.component)
                    is Child.VenueDetail -> VenueDetailContent(component = instance.component)
                    is Child.InsertEvent -> InsertEventContent(component = instance.component)
                    is Child.InsertExpense -> InsertExpenseContent(component = instance.component)
                    is Child.InsertVenue -> InsertVenueContent(component = instance.component)
                    is Child.PlannerDayDetail -> PlannerDayDetailContent(component = instance.component)
                    is Child.Settings -> AccountFeatureContent(component = instance.component)
                }
            }
        }
    }
}
