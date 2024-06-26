import java.util.Properties

plugins {
    id("com.google.devtools.ksp")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.com.google.dagger.hilt.android)
}

// local.properties 파일에 추가한 값 사용
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.jyproject.presentation"
    compileSdk = 34

    defaultConfig {
        buildConfigField("String", "NAVER_CLIENT_ID", localProperties.getProperty("NAVER_CLIENT_ID"))
        buildConfigField("String", "NAVER_CLIENT_KEY", localProperties.getProperty("NAVER_CLIENT_KEY"))
        buildConfigField("String", "KAKAO_NATIVE_KEY", localProperties.getProperty("KAKAO_NATIVE_KEY"))
        buildConfigField("String", "NAVER_MAP_CLIENT_ID", localProperties.getProperty("NAVER_MAP_CLIENT_ID"))
        buildConfigField("String", "NAVER_MAP_CLIENT_KEY", localProperties.getProperty("NAVER_MAP_CLIENT_KEY"))

        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // compose
    implementation(libs.androidx.animation)
    implementation(libs.lifecycle)
    implementation(libs.compose)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.4")
    implementation("androidx.compose.material3:material3")
    implementation(libs.compose.navigation)
    implementation(libs.compose.material)
    implementation(libs.compose.lifecycle)

    // coroutine
    implementation(libs.kotlin.coroutines.play)

    // social login
    implementation(libs.naver)
    implementation(libs.kakao)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.compose.hilt.navigation)

    // permission
    implementation(libs.accompanist.permissions)

    // location
    implementation(libs.play.services.location)

    // Naver Map
    implementation(libs.naver.map)
    implementation(libs.naver.map.location)

    // Lottie
    implementation(libs.lottie.compose)

    // Vico
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m2)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
    implementation(libs.vico.views)
}
