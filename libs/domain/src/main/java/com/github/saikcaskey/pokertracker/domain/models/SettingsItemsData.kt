package com.github.saikcaskey.pokertracker.domain.models

data class SettingsItemsData(
    val userId: String? = null,
    val isDebugEnabled: Boolean? = null,
    val lastSelectedTab: Int? = null,
)
