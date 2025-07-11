import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.compose).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.sqldelight).apply(false)
}

allprojects {
    tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=com.arkivanov.decompose.ExperimentalDecomposeApi",
                "-opt-in=com.arkivanov.decompose.DelicateDecomposeApi",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlin.time.ExperimentalTime",
            )
        }
    }
}
