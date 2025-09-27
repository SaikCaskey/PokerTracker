package com.github.saikcaskey.pokertracker.domain.models

data class SettingsData(
    val userId: String? = null,
    val showDebugSettings: Boolean = false,
    val lastSelectedTab: Int? = null,
    // TODO add more fields
)
