package com.github.saikcaskey.pokertracker.build_logic.convention.plugins

import com.android.build.api.dsl.LibraryExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.configureExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getPluginId
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getVersionInt
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension

class ModuleLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply(libs.getPluginId("android-library"))
                apply(libs.getPluginId("kotlin-android"))
            }
            configureExtension<LibraryExtension> {
                compileSdk = libs.getVersionInt("android-compilesdk")

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

            configureExtension<KotlinAndroidExtension> {
                jvmToolchain(libs.getVersionInt("jvmTarget"))
            }
        }
    }
}
