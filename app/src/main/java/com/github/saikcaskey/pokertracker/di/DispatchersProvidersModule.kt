package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.libs.domain.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
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
