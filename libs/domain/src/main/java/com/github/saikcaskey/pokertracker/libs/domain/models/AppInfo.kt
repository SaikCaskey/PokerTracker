package com.github.saikcaskey.pokertracker.libs.domain.models

data class AppInfo(
    val applicationId: String? = null,
    val buildType: String? = null,
    val versionCode: Int? = null,
    val versionName: String? = null,
    val gitCommitHash: String? = null,
    val isDebug: Boolean? = null,
)

