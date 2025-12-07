package com.github.saikcaskey.pokertracker.libs.domain.util

import kotlin.time.Clock
import kotlin.time.Instant

fun nowAsInstant(): Instant = Clock.System.now()
