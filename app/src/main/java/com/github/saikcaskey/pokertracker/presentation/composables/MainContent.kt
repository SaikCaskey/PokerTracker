package com.github.saikcaskey.pokertracker.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.*
import com.github.saikcaskey.pokertracker.dashboard.presentation.composables.DashboardFeatureContent
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerFeatureComponent
import com.github.saikcaskey.account.presentation.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.planner.presentation.composables.PlannerFeatureContent
import com.github.saikcaskey.account.presentation.AccountFeatureContent
import com.github.saikcaskey.pokertracker.domain.component.MainComponent
import com.github.saikcaskey.stats.domain.components.StatsFeaturePagerComponent
import com.github.saikcaskey.pokertracker.stats.presentation.composables.StatsFeatureContent

@Composable
internal fun MainContent(component: MainComponent, modifier: Modifier = Modifier) {
    val title = component.title.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = { MainPagerBottomAppBar(selectPage = component::selectPage) },
        topBar = { TopAppBar(title = { Text(text = title.value) }) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = paddingValues.calculateEndPadding(LayoutDirection.Ltr))
        ) {
            ChildPages(
                pages = component.pages,
                onPageSelected = component::selectPage,
                scrollAnimation = PagesScrollAnimation.Default,
            ) { idx, pageComponent ->
                when (pageComponent) {
                    is AccountFeatureComponent -> AccountFeatureContent(pageComponent)
                    is PlannerFeatureComponent -> PlannerFeatureContent(pageComponent)
                    is DashboardFeatureComponent -> DashboardFeatureContent(pageComponent)
                    is StatsFeaturePagerComponent -> StatsFeatureContent(pageComponent)
                }
            }
        }
    }
}
