package com.github.saikcaskey.pokertracker.build_logic.convention.plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.configureExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getPluginId
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getVersionInt
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension
import java.io.FileInputStream
import java.util.Properties

class ModuleAppConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply(libs.getPluginId("android-application"))
                apply(libs.getPluginId("kotlin-android"))
                apply(libs.getPluginId("jetbrains-compose"))
                apply(libs.getPluginId("kotlin-compose"))
                apply(libs.getPluginId("kotlinx-serialization"))
            }
            configureExtension<BaseAppModuleExtension> {
                namespace = "com.github.saikcaskey.pokertracker"
                compileSdk = libs.getVersionInt("android-compilesdk")

                defaultConfig {
                    applicationId = "com.github.saikcaskey.pokertracker"
                    minSdk = libs.getVersionInt("android-minsdk")
                    targetSdk = libs.getVersionInt("android-targetsdk")
                    versionCode = 3
                    versionName = "0.1.2"
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
                        storeFile =
                            file("../${localProperties.getProperty("POKERTRACKER_F_DROID_KEYSTORE")}")
                        storePassword =
                            localProperties.getProperty("POKERTRACKER_F_DROID_STORE_PASSWORD")
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
            }

            configureExtension<KotlinAndroidExtension> {
                jvmToolchain(libs.getVersionInt("jvmTarget"))
            }
        }
    }
}
