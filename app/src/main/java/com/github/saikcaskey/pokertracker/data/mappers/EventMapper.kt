package com.github.saikcaskey.pokertracker.data.mappers

import com.github.saikcaskey.pokertracker.domain.models.GameType
import com.github.saikcaskey.pokertracker.data.utils.asInstantOrNow
import com.github.saikcaskey.pokertracker.data.utils.asInstantOrNull
import com.saikcaskey.github.pokertracker.shared.database.Event
import com.github.saikcaskey.pokertracker.domain.models.Event as DomainEvent

fun Event.toDomain(): DomainEvent {
    return DomainEvent(
        id = id,
        venueId = venue_id,
        name = name,
        date = date.asInstantOrNow(),
        gameType = GameType.valueOf(game_type),
        description = description,
        createdAt = created_at.asInstantOrNow(),
        updatedAt = updated_at.asInstantOrNull(),
    )
}
