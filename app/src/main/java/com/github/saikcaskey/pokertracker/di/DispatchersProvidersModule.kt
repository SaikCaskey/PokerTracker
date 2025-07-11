package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.data.utils.defaultCoroutineDispatchersProviders
import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val dispatchersProvidersModule = module {
    single<CoroutineDispatchers> {
        defaultCoroutineDispatchersProviders
    }
}

object CoroutineDispatchersProvider : KoinComponent {
    fun provide(): CoroutineDispatchers = get()
}