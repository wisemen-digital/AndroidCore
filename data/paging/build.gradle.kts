plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    `maven-publish`
}

group = "com.github.appwise-labs"

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
    namespace = "be.appwise.paging"
}

dependencies {
    implementation(project(":data:room"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)

    api("androidx.paging:paging-runtime-ktx:3.1.1")

    kapt(libs.room.compiler)
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

                groupId = "com.github.appwise-labs"

                artifact(sourceJar)
            }
        }
    }
}
