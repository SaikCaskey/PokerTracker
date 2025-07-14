plugins {
    id("pokertracker.android.lib")
}

android {
    namespace = "com.github.saikcaskey.pokertracker.domain"
}

dependencies {
    implementation(libs.decompose.decompose)
    implementation(libs.kermit)
    implementation(libs.androidx.compose.material3)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sqldelight.android.driver)
}
