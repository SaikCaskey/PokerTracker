package com.github.saikcaskey.data.utils

import com.github.saikcaskey.pokertracker.domain.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers

val defaultCoroutineDispatchersProviders = object : CoroutineDispatchers {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}
