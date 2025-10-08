package com.github.saikcaskey.pokertracker.domain.models

import kotlin.time.Instant

data class User(
    val id: Long,
    val name: String,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
