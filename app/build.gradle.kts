plugins {
    id("pokertracker.android.app")
}

dependencies {
    implementation(project(":feature:dashboard"))
    implementation(project(":libs:ui-compose"))
    implementation(project(":libs:data"))
    implementation(project(":libs:domain"))

    implementation(compose.ui)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.androidx.activity.activityCompose)
    implementation(libs.br.compose.icons.font.awesome)
    implementation(libs.compose.multiplatform.calendar)
    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extensionsComposeJetbrains)
    implementation(libs.essenty.lifecycle)
    implementation(libs.kermit)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.insert.koin.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.sqldelight.android.driver)
}
