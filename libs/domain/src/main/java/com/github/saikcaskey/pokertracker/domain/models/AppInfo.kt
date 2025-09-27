package com.github.saikcaskey.pokertracker.domain.models

data class AppInfo(
    val applicationId: String? = null,
    val buildType: String? = null,
    val flavor: String? = null,
    val versionCode: Int? = null,
    val versionName: String? = null,
    val gitCommitHash: String? = null,
    val isProd: Boolean? = null,
)

