package com.github.saikcaskey.pokertracker.domain.models

data class DashboardProfitSummaryData(
    val nowBalance: Double,
    val monthBalance: Double,
    val yearBalance: Double,
    val upcomingCosts: Double,
)