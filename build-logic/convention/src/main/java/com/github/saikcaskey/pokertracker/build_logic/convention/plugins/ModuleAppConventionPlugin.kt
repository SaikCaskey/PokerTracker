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
                    versionCode = 5
                    versionName = "0.1.4"
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

                configureAppSigning(project)

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
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

private fun BaseAppModuleExtension.configureAppSigning(project: Project) {
    with(project) {
        val props = Properties()
        val localPropertiesFile = project.rootProject.file("local-env.properties")
        if (localPropertiesFile.exists()) {
            // Load properties from local.properties to localProperties
            FileInputStream(localPropertiesFile).use(props::load)

            val keystoreFile =
                project.rootProject.file(props.getProperty("POKERTRACKER_F_DROID_KEYSTORE"))
            if (keystoreFile.exists()) {
                signingConfigs {
                    // Only create release build type if we had the local properties
                    // Since for fdroid this won't be useful without reproducible builds
                    create("release") {
                        keyAlias = props.getProperty("POKERTRACKER_F_DROID_ALIAS")
                        keyPassword = props.getProperty("POKERTRACKER_F_DROID_PASSWORD")
                        storeFile = keystoreFile
                        storePassword = props.getProperty("POKERTRACKER_F_DROID_STORE_PASSWORD")
                    }
                    buildTypes {
                        getByName("release") {
                            signingConfig = signingConfigs.getByName("release")
                        }
                    }
                }
            } else {
                error("No keystore was found in the localPropertiesFile")
            }
        } else {
            println("WARNING: local-env.properties file not found.")
            buildTypes {
                getByName("release") {
                    signingConfig = signingConfigs.getByName("debug")
                }
            }
        }
    }
}
