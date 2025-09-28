package com.github.saikcaskey.pokertracker.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.*
import com.github.saikcaskey.stats.presentation.InsertVenueContent
import com.github.saikcaskey.stats.presentation.VenueDetailContent
import com.github.saikcaskey.stats.presentation.ViewVenuesContent
import com.github.saikcaskey.pokertracker.ui_compose.common.theme.AppTheme
import com.github.saikcaskey.stats.presentation.EventDetailContent
import com.github.saikcaskey.stats.presentation.InsertEventContent
import com.github.saikcaskey.stats.presentation.ViewEventsContent
import com.github.saikcaskey.stats.presentation.ExpenseDetailContent
import com.github.saikcaskey.stats.presentation.InsertExpenseContent
import com.github.saikcaskey.stats.presentation.ViewExpensesContent
import com.github.saikcaskey.pokertracker.planner.presentation.composables.PlannerDayDetailContent
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
                    is RootComponent.Child.Main -> MainContent(component = instance.component)
                    is RootComponent.Child.ViewExpenses -> ViewExpensesContent(component = instance.component)
                    is RootComponent.Child.ViewEvents -> ViewEventsContent(component = instance.component)
                    is RootComponent.Child.ViewVenues -> ViewVenuesContent(component = instance.component)
                    is RootComponent.Child.EventDetail -> EventDetailContent(component = instance.component)
                    is RootComponent.Child.ExpenseDetail -> ExpenseDetailContent(component = instance.component)
                    is RootComponent.Child.VenueDetail -> VenueDetailContent(component = instance.component)
                    is RootComponent.Child.InsertEvent -> InsertEventContent(component = instance.component)
                    is RootComponent.Child.InsertExpense -> InsertExpenseContent(component = instance.component)
                    is RootComponent.Child.InsertVenue -> InsertVenueContent(component = instance.component)
                    is RootComponent.Child.PlannerDayDetail -> PlannerDayDetailContent(component = instance.component)
                    is RootComponent.Child.Settings -> AccountFeatureContent(component = instance.component)
                }
            }
        }
    }
}
