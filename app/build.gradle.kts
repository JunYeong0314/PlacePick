import java.util.Properties

plugins {
    id("com.google.devtools.ksp")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("com.google.gms.google-services")
}

// local.properties 파일에 추가한 값 사용
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
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

        buildConfigField("String", "KAKAO_NATIVE_KEY", localProperties.getProperty("KAKAO_NATIVE_KEY"))
        buildConfigField("String", "NAVER_MAP_CLIENT_ID", localProperties.getProperty("NAVER_MAP_CLIENT_ID"))
        manifestPlaceholders["KAKAO_NATIVE_KEY"] =
            localProperties.getProperty("KAKAO_NATIVE_KEY_MF")
        manifestPlaceholders["NAVER_MAP_CLIENT_ID"] =
            localProperties.getProperty("NAVER_MAP_CLIENT_ID_MF")

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
    ksp(libs.hilt.compiler)

    // social login
    implementation(libs.naver)
    implementation(libs.kakao)

    // fire base
    implementation(platform(libs.firebase))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")

    implementation(libs.map.sdk)

}