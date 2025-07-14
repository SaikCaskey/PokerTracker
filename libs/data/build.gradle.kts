import org.gradle.kotlin.dsl.android

plugins {
    id("pokertracker.android.lib")
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.github.saikcaskey.pokertracker.data"
}

sqldelight {
    databases {
        create("PokerTrackerDatabase") {
            packageName.set("com.github.saikcaskey.pokertracker.database")
        }
    }
}

dependencies {
    implementation(project(":libs:domain"))

    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.runtime)
}
