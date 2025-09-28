package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val dispatchersProvidersModule = module {
    single<CoroutineDispatchers> {
        object : CoroutineDispatchers {
            override val main = Dispatchers.Main
            override val io = Dispatchers.IO
            override val default = Dispatchers.Default
            override val unconfined = Dispatchers.Unconfined
        }
    }
}

object CoroutineDispatchersProvider : KoinComponent {
    fun provide(): CoroutineDispatchers = get()
}

