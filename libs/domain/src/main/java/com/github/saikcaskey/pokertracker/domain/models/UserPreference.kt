package com.github.saikcaskey.pokertracker.domain.models

sealed class UserPreference<T> {
    abstract val key: String

    data object UserId : UserPreference<String>() {
        override val key: String = "user_id"
    }

    data object DefaultBuyIn : UserPreference<Int>() {
        override val key: String = "default_buy_in"
    }

    data object ShowAdvancedSettings : UserPreference<Boolean>() {
        override val key: String = "show_advanced_settings"
    }
}
