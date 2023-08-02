plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
}

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
    namespace = "be.appwise.networking"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data:networking:proxyman"))

    implementation(libs.kotlin.stdlib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)

    //compress images for uploads
    implementation("id.zelory:compressor:3.0.1")

    //networking
    api(libs.retrofit)
    api(libs.retrofit.scalars)
    api(libs.retrofit.gson)

    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)
}

val sourceJar: Task by tasks.creating(Jar::class) {
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
