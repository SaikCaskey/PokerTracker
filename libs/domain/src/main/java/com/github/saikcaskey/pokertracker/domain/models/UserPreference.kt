package com.github.saikcaskey.pokertracker.domain.models

sealed class UserPreference<T> {
    abstract val key: String

    data object UserId : UserPreference<String>() {
        override val key: String = "user_id"
    }

    data object DefaultBuyIn : UserPreference<Int>() {
        override val key: String = "last_selected_tab"
    }

    data object ShowAdvancedSettings : UserPreference<Boolean>() {
        override val key: String = "is_debug"
    }
}
