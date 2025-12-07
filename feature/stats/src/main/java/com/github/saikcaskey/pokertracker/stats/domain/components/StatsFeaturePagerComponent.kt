package com.github.saikcaskey.pokertracker.stats.domain.components

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.libs.domain.presentation.component.FeatureComponent
import kotlinx.coroutines.flow.StateFlow

interface StatsFeaturePagerComponent : FeatureComponent {
    val pages: Value<ChildPages<*, FeatureComponent>>
    val selectedIndex: StateFlow<Int>
    fun selectPage(index: Int)
}
