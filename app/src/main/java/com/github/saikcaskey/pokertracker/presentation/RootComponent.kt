package com.github.saikcaskey.pokertracker.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

/**
 * The navigation "Root" for the app's decompose structure.
 * All of these children can be pushed onto the Root navigator's Stack.
 */
interface RootComponent {
    val rootNavigationStack: Value<ChildStack<*, RootDestination>>
    fun onBackClicked(toIndex: Int)
}
