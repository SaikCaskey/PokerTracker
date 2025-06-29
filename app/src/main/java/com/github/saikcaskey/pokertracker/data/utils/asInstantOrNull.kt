package com.github.saikcaskey.pokertracker.data.utils

import co.touchlab.kermit.Logger
import kotlinx.datetime.*

fun String?.asInstantOrNull(): Instant? {
    Logger.i("asd String.asInstantOrNull parsing date $this")
    return this?.let {
        runCatching { Instant.parse(it) }
            .onSuccess { Logger.i("asd parsed date to: $it") }
            .onFailure { Logger.e("asd failed to parse date: $this"); it.printStackTrace() }
            .getOrNull()
    }
}
