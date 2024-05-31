import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    id("com.google.devtools.ksp")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("androidx.room")
}

// local.properties 파일에 추가한 값 사용
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.jyproject.data"
    compileSdk = 34

    // Room schema export to allow db auto migrations between versions.
    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        buildConfigField("String", "NAVER_CLIENT_ID", localProperties.getProperty("NAVER_CLIENT_ID"))
        buildConfigField("String", "NAVER_CLIENT_KEY", localProperties.getProperty("NAVER_CLIENT_KEY"))
        buildConfigField("String", "KAKAO_NATIVE_KEY", localProperties.getProperty("KAKAO_NATIVE_KEY"))
        buildConfigField("String", "SEOUL_API_KEY", localProperties.getProperty("SEOUL_API_KEY"))

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

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // social login
    implementation(libs.naver)
    implementation(libs.kakao)

    // RoomDB
    implementation(libs.room.runtime)
    implementation (libs.room.ktx)
    annotationProcessor (libs.room.compiler)
    ksp(libs.room.compiler)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.moshi.kotlin)

    // Okhttp
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    // Data store
    implementation (libs.androidx.datastore.preferences.v111)
}

