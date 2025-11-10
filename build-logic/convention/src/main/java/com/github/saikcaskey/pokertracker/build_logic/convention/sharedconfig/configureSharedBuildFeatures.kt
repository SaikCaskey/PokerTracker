package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.CommonExtension

internal fun CommonExtension<*, *, *, *, *, *>.configureSharedBuildFeatures(enableCompose: Boolean = false) {
    buildFeatures {
        buildConfig = true
        compose = enableCompose
    }
}
