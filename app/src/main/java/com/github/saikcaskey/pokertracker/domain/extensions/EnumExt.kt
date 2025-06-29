package com.github.saikcaskey.pokertracker.domain.extensions

inline fun <reified T : Enum<T>> valueOfOrDefault(name: String, default: T): T {
    return enumValues<T>().firstOrNull { it.name == name } ?: default
}