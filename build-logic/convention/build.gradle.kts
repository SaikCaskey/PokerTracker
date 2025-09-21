import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.github.saikcaskey.pokertracker.build_logic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.jetbrains.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("androidapp") {
            id = "pokertracker.android.app"
            implementationClass = "com.github.saikcaskey.pokertracker.build_logic.convention.plugins.ModuleAppConventionPlugin"
        }
        register("androidlibrary") {
            id = "pokertracker.android.library"
            implementationClass = "com.github.saikcaskey.pokertracker.build_logic.convention.plugins.ModuleLibraryConventionPlugin"
        }
        register("androidlib") {
            id = "pokertracker.android.lib"
            implementationClass = "com.github.saikcaskey.pokertracker.build_logic.convention.plugins.ModuleLibConventionPlugin"
        }
        register("composelib") {
            id = "pokertracker.android.composelib"
            implementationClass = "com.github.saikcaskey.pokertracker.build_logic.convention.plugins.ModuleComposeLibraryConventionPlugin"
        }
    }
}
