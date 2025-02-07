plugins {
    kotlin("android")
    kotlin("kapt")
    `maven-publish`

    id("com.android.library")
    id("com.google.devtools.ksp")
}

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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    namespace = "be.appwise.core"
}

dependencies {
    implementation(libs.startup.runtime)

    // https://github.com/Ereza/CustomActivityOnCrash
    implementation(libs.customactivityoncrash)

    api(libs.core.ktx)

    api(libs.multidex)

    //Kotlin reflect
    api(libs.kotlin.reflect)

    //ViewModel
    api(libs.lifecycle.viewmodel.ktx)

    api(libs.lifecycle.livedata.ktx)

    //AndroidX Support libraries
    api(libs.appcompat)
    api(libs.material)
    api(libs.constraintlayout)
    api(libs.swiperefreshlayout)

    //coroutines
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    // navigation components
    api(libs.navigation.fragment.ktx)
    api(libs.navigation.ui.ktx)

    // gson (https://github.com/google/gson)
    api(libs.gson)

    // logger (https://github.com/orhanobut/logger)
    api(libs.logger)

    // Hawk (https://github.com/orhanobut/hawk)
    api(libs.hawk)

    //open image chooser
    api(libs.easyimage)

    //image loading/caching
    api(libs.glide.glide)
    ksp(libs.glide.compiler)

    //dialogs
    api(libs.material.dialogs.core)
    api(libs.material.dialogs.datetime)

    // Time manipulation for Java 7
    api(libs.joda.time)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.espresso.core)
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
