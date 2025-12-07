package com.github.saikcaskey.pokertracker.libs.domain.models

/**
 * TODO Bounty, Prize, etc
 */
enum class ExpenseType {
    CASH_OUT,
    DEAL,
    BUY_IN,
    ADD_ON,
    REBUY,
    FOOD,
    PARKING,
    TRANSPORT,
    TRAVEL,
    DRINKS,
    FINE,
    MISC,
    OTHER;

    companion object {
        fun fromString(value: String): ExpenseType {
            return entries.firstOrNull { it.name == value } ?: OTHER
        }
    }
}
