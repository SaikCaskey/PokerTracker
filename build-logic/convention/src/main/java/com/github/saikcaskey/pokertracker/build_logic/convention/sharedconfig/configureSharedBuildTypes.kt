package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.kotlin.dsl.getByName

internal fun CommonExtension<*, *, *, *, *, *>.configureSharedBuildTypes() {
    buildTypes {
        getByName<ApplicationBuildType>("release") {
            if (this@configureSharedBuildTypes is BaseAppModuleExtension) {
                isMinifyEnabled = true
                isShrinkResources = true
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName<ApplicationBuildType>("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            if (this@configureSharedBuildTypes is BaseAppModuleExtension) {
                applicationIdSuffix = ".debug"
            }
        }
    }
}
