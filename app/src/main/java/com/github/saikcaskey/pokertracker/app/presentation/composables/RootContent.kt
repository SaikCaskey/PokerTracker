package com.github.saikcaskey.pokertracker.app.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.github.saikcaskey.pokertracker.app.domain.component.RootComponent
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.AccountDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.DashboardDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.EventDetailDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.ExpenseDetailDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.InsertEventDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.InsertExpenseDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.InsertVenueDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.PlannerDayDetailDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.PlannerDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.StatsDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.VenueDetailDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.ViewEventsDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.ViewExpensesDestination
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination.ViewVenuesDestination
import com.github.saikcaskey.pokertracker.feature.account.presentation.AccountFeatureContent
import com.github.saikcaskey.pokertracker.feature.dashboard.presentation.composables.DashboardFeatureContent
import com.github.saikcaskey.pokertracker.feature.onboarding.OnboardingFeatureContent
import com.github.saikcaskey.pokertracker.feature.planner.presentation.composables.PlannerDayDetailContent
import com.github.saikcaskey.pokertracker.feature.planner.presentation.composables.PlannerFeatureContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.EventDetailContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.ExpenseDetailContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.InsertEventContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.InsertExpenseContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.InsertVenueContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.StatsFeatureContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.VenueDetailContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.ViewEventsContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.ViewExpensesContent
import com.github.saikcaskey.pokertracker.feature.stats.presentation.composables.ViewVenuesContent
import com.github.saikcaskey.pokertracker.ui_compose.common.theme.AppTheme

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
                    is RootDestination.OnboardingDestination -> OnboardingFeatureContent(instance.component)
                    is DashboardDestination -> DashboardFeatureContent(instance.component)
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
                    is AccountDestination -> AccountFeatureContent(instance.component)
                    is PlannerDestination -> PlannerFeatureContent(instance.component)
                    is StatsDestination -> StatsFeatureContent(instance.component)
                }
            }
        }
    }
}
