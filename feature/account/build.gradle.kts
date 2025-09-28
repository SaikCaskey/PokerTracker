plugins {
    id("pokertracker.android.composelib")
}

android {
    namespace = "com.github.saikcaskey.pokertracker.account"
}

dependencies {
    implementation(project(":libs:domain"))
    implementation(project(":libs:data"))
    implementation(project(":libs:ui-compose"))

    implementation(compose.ui)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.br.compose.icons.font.awesome)
    implementation(libs.decompose.decompose)
    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
}
