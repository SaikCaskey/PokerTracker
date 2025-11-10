package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.jetbrains.annotations.Debug

/**
 * Configures the flavor dimensions for this app or library module, creates the flavors and adds
 * relevant build config fields and manifest placeholders.
 */
internal fun CommonExtension<*, *, *, *, *, *>.configureSharedFlavors() {
    flavorDimensions += listOf("env")

    productFlavors {
        create("alpha") {
            dimension = "env"
            buildConfigField("Boolean", "isProd", "false")
            if (this is ApplicationExtension) {
                defaultConfig.applicationIdSuffix = ".alpha"
            }
        }
        create("beta") {
            dimension = "env"
            buildConfigField("Boolean", "isProd", "false")
            if (this is ApplicationExtension) {
                defaultConfig.applicationIdSuffix = ".beta"
            }
        }
        create("prod") {
            dimension = "env"
            buildConfigField("Boolean", "isProd", "true")
            if (this is ApplicationExtension) {
                defaultConfig.applicationIdSuffix = ".prod"
            }
        }
    }
}
