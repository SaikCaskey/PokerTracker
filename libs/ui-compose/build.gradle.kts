plugins {
    id("pokertracker.android.composelib")
}

android {
    namespace = "com.github.saikcaskey.pokertracker.ui_compose"
}

dependencies {
    implementation(project(":libs:domain"))

    implementation(compose.ui)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.androidx.compose.material3)
    implementation(libs.br.compose.icons.font.awesome)
    implementation(libs.compose.multiplatform.calendar)
    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.material.kolor)
}
