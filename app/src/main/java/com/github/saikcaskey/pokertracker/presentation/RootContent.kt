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
import com.github.saikcaskey.pokertracker.presentation.RootDestination.*

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
                    is MainDestination -> MainContent(instance.component)
                    is ViewExpensesDestination -> ViewExpensesContent(instance.component)
                    is ViewEventsDestination -> ViewEventsContent(instance.component)
                    is ViewVenuesDestination -> ViewVenuesContent(instance.component)
                    is EventDetailDestination -> EventDetailContent(instance.component)
                    is ExpenseDetailDestination -> ExpenseDetailContent(instance.component)
                    is VenueDetailDestination -> VenueDetailContent(instance.component)
                    is InsertEventDestination -> InsertEventContent(instance.component)
                    is InsertExpenseDestination -> InsertExpenseContent(instance.component)
                    is InsertVenueDestination -> InsertVenueContent(instance.component)
                    is PlannerDayDetailDestination -> PlannerDayDetailContent(instance.component)
                    is SettingsDestination -> AccountFeatureContent(instance.component)
                }
            }
        }
    }
}
