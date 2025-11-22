package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

internal fun CommonExtension<*, *, *, *, *, *>.configureSharedBuildTypes() {
    buildTypes {
        getByName("release") {
            if (this@configureSharedBuildTypes is BaseAppModuleExtension) {
                isMinifyEnabled = true
                defaultConfig.proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false

            if (this@configureSharedBuildTypes is BaseAppModuleExtension) {
                defaultConfig.applicationIdSuffix = ".debug"
            }
        }
    }
}
