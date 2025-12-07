package com.github.saikcaskey.pokertracker.app.domain.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootDestination

interface RootComponent {

    val rootNavigationStack: Value<ChildStack<*, RootDestination>>

    fun onBackClicked(toIndex: Int)

}
