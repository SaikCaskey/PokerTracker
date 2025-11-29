package com.github.saikcaskey.pokertracker.domain.models

data class DashboardProfitSummaryData(
    val recentExpenses: List<Expense> = emptyList(),
    val nowBalance: Double = 0.0,
    val monthBalance: Double = 0.0,
    val yearBalance: Double = 0.0,
    val upcomingCosts: Double = 0.0,
)
