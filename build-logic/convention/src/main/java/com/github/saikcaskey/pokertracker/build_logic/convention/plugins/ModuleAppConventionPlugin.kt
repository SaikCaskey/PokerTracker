package com.github.saikcaskey.pokertracker.build_logic.convention.plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
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
import com.github.saikcaskey.pokertracker.build_logic.convention.utils.gitCommitHash
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension

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
                    targetSdk = libs.getVersionInt("android-targetsdk")
                    versionCode = 3
                    versionName = "0.1.2"
                    buildConfigField("String", "GIT_COMMIT_HASH", "\"${project.gitCommitHash()}\"")
                }

                // F DROID - Disable AGP signing block
                // See https://gitlab.com/fdroid/fdroiddata/-/merge_requests/24283#note_2636169667
                dependenciesInfo {
                    // Disables dependency metadata when building APKs.
                    includeInApk = false
                    // Disables dependency metadata when building Android App Bundles.
                    includeInBundle = false
                }

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

