import org.gradle.kotlin.dsl.android
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.github.saikcaskey.pokertracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.github.saikcaskey.pokertracker"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Load properties from local.properties
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file("local-env.properties")

    if (localPropertiesFile.exists()) {
        FileInputStream(localPropertiesFile).use {
            localProperties.load(it)
        }
    } else {
        println("WARNING: local-env.properties file not found. App Signing won't work correctly.")
    }

    signingConfigs {
        create("release") {
            keyAlias = localProperties.getProperty("POKERTRACKER_F_DROID_ALIAS")
            keyPassword = localProperties.getProperty("POKERTRACKER_F_DROID_PASSWORD")
            storeFile = file("../${localProperties.getProperty("POKERTRACKER_F_DROID_KEYSTORE")}")
            storePassword = localProperties.getProperty("POKERTRACKER_F_DROID_STORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(compose.ui)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.androidx.activity.activityCompose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.br.compose.icons.font.awesome)
    implementation(libs.compose.multiplatform.calendar)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extensionsComposeJetbrains)
    implementation(libs.essenty.lifecycle)
    implementation(libs.kermit)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.insert.koin.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.material.kolor)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.runtime)
}

sqldelight {
    databases {
        create("PokerTrackerDatabase") {
             packageName.set("com.github.saikcaskey.pokertracker.database")
        }
    }
}

