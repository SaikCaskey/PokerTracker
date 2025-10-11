package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.CommonExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getVersionInt
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.libs
import org.gradle.api.Project

internal fun Project.configureSharedDefaultConfig(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.getVersionInt("android-compilesdk")
        defaultConfig {
            minSdk = libs.getVersionInt("android-minsdk")
        }
    }
}
