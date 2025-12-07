plugins {
    id("pokertracker.android.library")
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.github.saikcaskey.pokertracker.libs.database"
}

sqldelight {
    databases {
        create("PokerTrackerDatabase") {
            packageName.set("com.github.saikcaskey.pokertracker.libs.database")
        }
    }
}

dependencies {
    implementation(project(":libs:domain"))

    implementation(libs.kermit)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.runtime)


}
