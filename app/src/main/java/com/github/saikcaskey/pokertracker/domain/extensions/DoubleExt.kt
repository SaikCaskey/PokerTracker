package com.github.saikcaskey.pokertracker.domain.extensions

fun Double.formatAsCurrency(symbol: String = "€"): String {
    val rounded = (this * 100).toInt() / 100.0 // round to 2 decimal places
    val parts = rounded.toString().split(".")
    val whole = parts[0]
    val decimal = parts.getOrNull(1)?.padEnd(2, '0') ?: "00"
    return "${symbol}$whole.$decimal"
}
