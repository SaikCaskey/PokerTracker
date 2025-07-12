package com.github.saikcaskey.pokertracker.domain.components

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {
    val pages: Value<ChildPages<*, MainPagerPageComponent>>
    val selectedIndex: StateFlow<Int>
    val title: StateFlow<String>

    fun selectPage(index: Int)
}
