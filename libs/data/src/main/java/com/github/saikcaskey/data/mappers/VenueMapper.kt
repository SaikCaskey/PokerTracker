package com.github.saikcaskey.data.mappers

import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNow
import com.github.saikcaskey.pokertracker.domain.extensions.asInstantOrNull
import com.github.saikcaskey.pokertracker.database.Venue
import com.github.saikcaskey.pokertracker.domain.models.Venue as DomainVenue

fun Venue.toDomain() = DomainVenue(
    id = id,
    name = name,
    address = address,
    description = description,
    createdAt = created_at.asInstantOrNow(),
    updatedAt = updated_at.asInstantOrNull()
)
