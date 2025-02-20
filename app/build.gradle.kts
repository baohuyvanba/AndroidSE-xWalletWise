plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.android.tyzen.xwalletwise"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.android.tyzen.xwalletwise"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.common)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Architecture
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.espresso.core)
    ksp(libs.room.compiler)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    //Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //Graphic
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    //Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.compiler)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation.core)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.material.core)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    //Animation
    //implementation(libs.androidx.animation)
    //Security
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.biometric)
    //Room Database
    implementation(libs.androidx.room.common)
    annotationProcessor(libs.room.compiler)
    //Datastore
    implementation(libs.androidx.datastore.preferences)
    //CameraX
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    //AI
    implementation(libs.google.ai.client.generativeai)
    //Markdown
    implementation(libs.richtext.commonmark)
    implementation(libs.richtext.ui.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
}