package com.github.saikcaskey.pokertracker.libs.domain.models

import kotlin.reflect.KClass

sealed class UserPreference<T : Any> {
    abstract val key: String
    abstract val type: KClass<T>

    data object UserId : UserPreference<Long>() {
        override val key: String = "user_id"
        override val type: KClass<Long> = Long::class
    }

    data object DefaultBuyIn : UserPreference<Int>() {
        override val key: String = "default_buy_in"
        override val type: KClass<Int> = Int::class
    }

    data object ShowAdvancedSettings : UserPreference<Boolean>() {
        override val key: String = "show_advanced_settings"
        override val type: KClass<Boolean> = Boolean::class
    }
}
