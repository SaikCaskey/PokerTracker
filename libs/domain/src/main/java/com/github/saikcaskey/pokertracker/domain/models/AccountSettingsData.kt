package com.github.saikcaskey.pokertracker.domain.models

data class AccountSettingsData(
    val userId: Long? = null,
    val showAdvancedSettings: Boolean = false,
    val defaultBuyIn: Int? = null,
    val isProd: Boolean? = null,
    val applicationId: String? = null,
    val buildType: String? = null,
    val versionCode: Int? = null,
    val versionName: String? = null,
    val gitCommitHash: String? = null,
    // TODO add more fields
)
