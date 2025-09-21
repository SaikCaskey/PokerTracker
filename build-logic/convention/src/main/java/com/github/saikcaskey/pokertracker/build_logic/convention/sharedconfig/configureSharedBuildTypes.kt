package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension

internal fun CommonExtension<*, *, *, *, *, *>.configureSharedBuildTypes() {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = this is ApplicationExtension
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
}
