package com.github.saikcaskey.pokertracker.app.di

import com.github.saikcaskey.pokertracker.libs.domain.presentation.navigation.RootNavigator
import com.github.saikcaskey.pokertracker.app.presentation.navigation.RootNavigatorImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val navigationModule = module {
    single<RootNavigator> { RootNavigatorImpl() }
}

object RootNavigatorProvider : KoinComponent {
    fun provide(): RootNavigator = get()
}
