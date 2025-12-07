plugins {
    id("pokertracker.android.library")
    id("kotlinx-serialization")
}

android {
    namespace = "com.github.saikcaskey.pokertracker.libs.domain"
}

dependencies {
    implementation(libs.androidx.annotation.experimental)
    implementation(libs.androidx.compose.material3)
    implementation(libs.decompose.decompose)
    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sqldelight.android.driver)
}
