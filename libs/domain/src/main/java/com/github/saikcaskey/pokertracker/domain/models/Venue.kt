package com.github.saikcaskey.pokertracker.domain.models

import kotlin.time.Instant

data class Venue(
    val id: Long,
    val name: String,
    val address: String?,
    val description: String?,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
