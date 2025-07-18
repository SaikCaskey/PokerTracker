package com.github.saikcaskey.pokertracker.ui.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.*
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.InsertVenueContent
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.VenueDetailContent
import com.github.saikcaskey.pokertracker.ui_compose.components.venue.ViewVenuesContent
import com.github.saikcaskey.pokertracker.domain.components.RootComponent
import com.github.saikcaskey.pokertracker.domain.components.RootComponent.Child
import com.github.saikcaskey.pokertracker.ui.main.MainContent
import com.github.saikcaskey.pokertracker.ui_compose.common.theme.AppTheme
import com.github.saikcaskey.pokertracker.ui_compose.components.event.EventDetailContent
import com.github.saikcaskey.pokertracker.ui_compose.components.event.InsertEventContent
import com.github.saikcaskey.pokertracker.ui_compose.components.event.ViewEventsContent
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ExpenseDetailContent
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.InsertExpenseContent
import com.github.saikcaskey.pokertracker.ui_compose.components.expense.ViewExpensesContent
import com.github.saikcaskey.pokertracker.planner.composables.PlannerDayDetailContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    AppTheme(Color(70, 51, 250)) {
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
                }
            }
        }
    }
}
