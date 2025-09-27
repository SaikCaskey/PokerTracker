package com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig

import com.android.build.api.dsl.CommonExtension

internal fun CommonExtension<*, *, *, *, *, *>.configureSharedPackaging() {
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
