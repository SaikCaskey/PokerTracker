package com.github.saikcaskey.pokertracker.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.*
import com.github.saikcaskey.data.utils.seedSampleData
import com.github.saikcaskey.pokertracker.dashboard.composables.DashboardFeatureContent
import com.github.saikcaskey.pokertracker.di.PokerTrackerDatabaseProvider
import com.github.saikcaskey.pokertracker.domain.components.MainComponent
import com.github.saikcaskey.pokertracker.domain.components.DashboardFeatureComponent
import com.github.saikcaskey.pokertracker.domain.components.PlannerFeatureComponent
import com.github.saikcaskey.pokertracker.ui_compose.extensions.asIcon
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.Clone

@Composable
internal fun MainContent(component: MainComponent, modifier: Modifier = Modifier) {
    val title = component.title.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = { MainPagerBottomAppBar(selectPage = component::selectPage) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                PokerTrackerDatabaseProvider.provide().seedSampleData()
            }) {
                FontAwesomeIcons.Regular.Clone.asIcon(
                    height = 32.dp,
                    contentDescription = "Seed data button"
                )
            }
        },
        topBar = {
            TopAppBar(title = { Text(text = title.value) })
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 12.dp)
        ) {
            ChildPages(
                pages = component.pages,
                onPageSelected = component::selectPage,
                scrollAnimation = PagesScrollAnimation.Default,
            ) { idx, pageComponent ->
                when (pageComponent) {
                    is PlannerFeatureComponent -> PlannerFeatureContent(pageComponent)
                    is DashboardFeatureComponent -> DashboardFeatureContent(pageComponent)
                }
            }
        }
    }
}
