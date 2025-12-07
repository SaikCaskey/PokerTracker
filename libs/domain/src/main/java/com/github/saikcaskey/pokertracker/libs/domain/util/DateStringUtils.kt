package com.github.saikcaskey.pokertracker.libs.domain.util

import com.github.saikcaskey.pokertracker.libs.domain.extensions.toUiDateTimeOrNull

fun nowAsUiDateOrNull(): String? = nowAsInstant().toUiDateTimeOrNull()
