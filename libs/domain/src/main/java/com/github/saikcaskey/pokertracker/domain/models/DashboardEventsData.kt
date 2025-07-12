package com.github.saikcaskey.pokertracker.domain.models

data class DashboardEventsData(
    val recentEvents: List<Event>,
    val todayEvents: List<Event>,
    val upcomingEvents: List<Event>,
)
