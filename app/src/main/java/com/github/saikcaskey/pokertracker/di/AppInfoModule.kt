package com.github.saikcaskey.pokertracker.di

import com.github.saikcaskey.pokertracker.BuildConfig
import com.github.saikcaskey.pokertracker.libs.domain.models.AppInfo
import org.koin.dsl.module

val appInfoModule = module {
    single<AppInfo> {
        AppInfo(
            applicationId = BuildConfig.APPLICATION_ID,
            buildType = BuildConfig.BUILD_TYPE,
            versionCode = BuildConfig.VERSION_CODE,
            versionName = BuildConfig.VERSION_NAME,
            isDebug = BuildConfig.DEBUG,
            gitCommitHash = BuildConfig.GIT_COMMIT_HASH,
        )
    }
}
