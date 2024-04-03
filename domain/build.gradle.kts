@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

// Added as suggested in Hilt documentation at https://dagger.dev/hilt/gradle-setup
kapt {
    correctErrorTypes = true
}

dependencies {
    // hilt
    kapt(libs.hilt.compiler)
    implementation(libs.dagger)

}