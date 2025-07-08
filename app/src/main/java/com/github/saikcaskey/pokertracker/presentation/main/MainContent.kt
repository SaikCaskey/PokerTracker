package com.github.saikcaskey.pokertracker.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.*
import com.github.saikcaskey.pokertracker.domain.components.MainComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(component: MainComponent, modifier: Modifier = Modifier) {
    val title = component.title.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            MainPagerBottomAppBar(
                selectPage = component::selectPage,
            )
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
                    is MainPagerPagePlannerComponent -> MainPagerPlannerContent(pageComponent)
                    is MainPagerPageDashboardComponent -> MainPagerDashboardContent(pageComponent)
                }
            }
        }
    }
}
