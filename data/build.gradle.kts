plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":domain"))
    // sharedPreference
    implementation(libs.core.ktx)
    // crypto
    implementation(libs.security.crypto)
    // json
    implementation(libs.kotlinx.serialization.json)
    // dagger hilt
    implementation(libs.dagger.hilt)
}