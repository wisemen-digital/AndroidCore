plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
    implementation("androidx.startup:startup-runtime:1.1.1")

    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation(libs.junit)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.espresso.core)
    api(libs.core.ktx)

    api("androidx.multidex:multidex:2.0.1")

    //Kotlin reflect
    api(libs.kotlin.reflect) //TODO: what does this thing do??

    //ViewModel
    api(libs.lifecycle.viewmodel.ktx)

    api(libs.lifecycle.livedata.ktx)

    //AndroidX Support libraries
    api(libs.appcompat)
    api(libs.material)
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    api("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android")

    // navigation components
    api(libs.navigation.fragment.ktx)
    api(libs.navigation.ui.ktx)

    // gson (https://github.com/google/gson)
    api(libs.gson)

    // logger (https://github.com/orhanobut/logger)
    api("com.orhanobut:logger:2.2.0")

    // Hawk (https://github.com/orhanobut/hawk)
    api("com.orhanobut:hawk:2.0.1")

    //open image chooser
    api("com.github.jkwiecien:EasyImage:3.2.0")

    //image loading/caching
    api(libs.glide.glide)
    kapt(libs.glide.compiler)

    //dialogs
    api(libs.material.dialogs.core)
    api(libs.material.dialogs.datetime)

    // Time manipulation for Java 7
    api("joda-time:joda-time:2.12.2")

    // https://github.com/Ereza/CustomActivityOnCrash
    implementation("cat.ereza:customactivityoncrash:2.4.0")
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
