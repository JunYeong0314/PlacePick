import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.jyproject.placepick"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jyproject.placepick"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "KAKAO_NATIVE_KEY", getApiKey("KAKAO_NATIVE_KEY"))
        manifestPlaceholders["KAKAO_NATIVE_KEY"] =
            getApiKey("KAKAO_NATIVE_KEY_MF")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // social login
    implementation(libs.naver)
    implementation(libs.kakao)

    // fire base
    implementation(platform(libs.firebase))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}