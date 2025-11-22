package com.github.saikcaskey.pokertracker.stats.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.github.saikcaskey.pokertracker.stats.domain.components.StatsFeaturePagerComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewEventsComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewExpensesComponent
import com.github.saikcaskey.pokertracker.stats.domain.components.ViewVenuesComponent

@Composable
fun StatsFeatureContent(component: StatsFeaturePagerComponent) {
    ChildPages(
        modifier = Modifier.fillMaxSize(),
        pages = component.pages,
        onPageSelected = component::selectPage,
        scrollAnimation = PagesScrollAnimation.Default,
    ) { idx, pageComponent ->
        when (idx) {
            2 -> ViewExpensesContent(pageComponent as ViewExpensesComponent)
            1 -> ViewEventsContent(pageComponent as ViewEventsComponent)
            else -> ViewVenuesContent(pageComponent as ViewVenuesComponent)
        }
    }
}

