package com.github.saikcaskey.pokertracker.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.*
import com.github.saikcaskey.pokertracker.dashboard.presentation.composables.DashboardFeatureContent
import com.github.saikcaskey.pokertracker.dashboard.presentation.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.planner.presentation.PlannerFeatureComponent
import com.github.saikcaskey.account.presentation.AccountFeatureComponent
import com.github.saikcaskey.pokertracker.planner.presentation.composables.PlannerFeatureContent
import com.github.saikcaskey.account.presentation.AccountFeatureContent
import com.github.saikcaskey.stats.presentation.StatsFeatureComponent
import com.github.saikcaskey.stats.presentation.StatsFeatureContent

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
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
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
                    is StatsFeatureComponent -> StatsFeatureContent(pageComponent)
                }
            }
        }
    }
}
