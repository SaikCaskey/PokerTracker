import org.gradle.kotlin.dsl.android

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.github.saikcaskey.pokertracker.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    jvmToolchain(libs.versions.jvmTarget.get().toInt())
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

    implementation(libs.decompose.decompose)
    implementation(libs.essenty.lifecycle)
    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.runtime)
}
