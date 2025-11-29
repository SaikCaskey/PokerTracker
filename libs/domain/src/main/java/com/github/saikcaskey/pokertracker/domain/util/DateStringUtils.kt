package com.github.saikcaskey.pokertracker.domain.util

import com.github.saikcaskey.pokertracker.domain.extensions.toUiDateTimeOrNull

fun nowAsUiDateOrNull(): String? = nowAsInstant().toUiDateTimeOrNull()
