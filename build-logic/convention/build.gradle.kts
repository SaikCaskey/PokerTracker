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
        freeCompilerArgs.addAll(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=com.arkivanov.decompose.ExperimentalDecomposeApi",
            "-opt-in=com.arkivanov.decompose.DelicateDecomposeApi",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlin.time.ExperimentalTime",
        )
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
        register("androidlib") {
            id = "pokertracker.android.lib"
            implementationClass = "com.github.saikcaskey.pokertracker.build_logic.convention.plugins.ModuleLibraryConventionPlugin"
        }
        register("composelib") {
            id = "pokertracker.android.composelib"
            implementationClass = "com.github.saikcaskey.pokertracker.build_logic.convention.plugins.ModuleComposeLibraryConventionPlugin"
        }
    }
}