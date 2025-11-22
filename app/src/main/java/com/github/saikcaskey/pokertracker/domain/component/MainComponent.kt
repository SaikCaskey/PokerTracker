package com.github.saikcaskey.pokertracker.domain.component

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.domain.presentation.component.FeatureComponent
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {
   
    val pages: Value<ChildPages<*, FeatureComponent>>
    val selectedIndex: StateFlow<Int>
    val title: StateFlow<String>

    fun selectPage(index: Int)
}
