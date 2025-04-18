plugins {
    kotlin("android")
    alias(libs.plugins.android.application)
    alias(libs.plugins.navigation.safeargs)
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.coredemo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    namespace = "com.example.coredemo"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data:room"))
    implementation(project(":data:networking"))
    implementation(project(":data:paging"))
    implementation(project(":list:emptyRecyclerView"))
    implementation(project(":views:dataRow"))

    implementation(libs.mockwebserver)
    implementation(libs.mockinizer)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
}
