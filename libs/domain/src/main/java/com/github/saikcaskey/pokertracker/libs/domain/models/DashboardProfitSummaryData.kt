package com.github.saikcaskey.pokertracker.libs.domain.models

data class DashboardProfitSummaryData(
    val expensesBeforeNow: List<Expense> = emptyList(),
    val nowBalance: Double = 0.0,
)
