package com.github.saikcaskey.pokertracker.build_logic.convention.plugins

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.configureExtension
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getPluginId
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.getVersionInt
import com.github.saikcaskey.pokertracker.build_logic.convention.extensions.libs
import com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig.configureSharedBuildFeatures
import com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig.configureSharedBuildTypes
import com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig.configureSharedCompileOptions
import com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig.configureSharedDefaultConfig
import com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig.configureSharedFlavors
import com.github.saikcaskey.pokertracker.build_logic.convention.sharedconfig.configureSharedPackaging
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

                configureSharedBuildTypes()
                configureSharedBuildFeatures()
                configureSharedPackaging()
                configureSharedFlavors()
                configureSharedCompileOptions()
                configureSharedDefaultConfig(this)
            }
            configureExtension<KotlinAndroidExtension> {
                jvmToolchain(libs.getVersionInt("jvmTarget"))
            }
        }
    }
}
