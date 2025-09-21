package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

internal fun CommonExtension<*, *, *, *, *, *>.configureSharedCompileOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
