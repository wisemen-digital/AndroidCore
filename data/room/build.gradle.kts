plugins {
    kotlin("android")
    kotlin("kapt")
    `maven-publish`

    id("com.android.library")
    id("com.google.devtools.ksp")
}

group = "com.github.wisemen-digital"

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    namespace ="be.appwise.room"
}

dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)

    // Room Database
    api(libs.room.runtime)
    ksp(libs.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    api(libs.room.ktx)
}

val sourceJar by tasks.creating(Jar::class) {
    from(android.sourceSets["main"].java.srcDirs)
//    classifier("sources")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.wisemen-digital"

                artifact(sourceJar)
            }
        }
    }
}
