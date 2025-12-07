package com.github.saikcaskey.pokertracker.libs.domain.models

data class ExpenseSummary(
    val all: List<Expense> = emptyList(),
    val cashes: List<Expense> = emptyList(),
    val costs: List<Expense> = emptyList(),
)
