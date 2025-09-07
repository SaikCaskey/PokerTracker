package com.github.saikcaskey.pokertracker.domain.util

import kotlin.time.Clock
import kotlin.time.Instant

fun nowAsInstant(): Instant = Clock.System.now()
