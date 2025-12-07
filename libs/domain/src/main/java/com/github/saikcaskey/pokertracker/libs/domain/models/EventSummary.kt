package com.github.saikcaskey.pokertracker.libs.domain.models

data class EventSummary(
    val all: List<Event> = emptyList(),
    val upcoming: List<Event> = emptyList(),
    val recent: List<Event> = emptyList(),
    val today: List<Event> = emptyList(),
)
