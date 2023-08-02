pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        jcenter() // this is still needed for the current Realm version
        maven("https://plugins.gradle.org/m2/")
    }

    plugins {
        val agpVersion = extra["agp.version"] as String
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // this is still needed for the current Realm version
        maven("https://jitpack.io")
    }
}

rootProject.name = "CoreDemo"
include(":sample")
include(":core")
include(":list:emptyRecyclerView")
include(":data:networking")
include(":data:room")
include(":data:paging")
include(":views:dataRow")
include(":data:networking:proxyman")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.8.21")
            version("lifecycle", "2.6.1")
            version("navigation", "2.6.0")
            version("glide", "4.15.1")
            version("materialDialogs", "3.3.0")
            version("retrofit", "2.9.0")
            version("okhttp", "4.10.0")
            version("room", "2.5.0")

            library("junit", "junit:junit:4.13.2")
            library("junit-ext", "androidx.test.ext:junit:1.1.5")

            library("room-runtime", "androidx.room", "room-runtime").versionRef("room")
            library("room-compiler", "androidx.room", "room-compiler").versionRef("room")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("room")

            library("test-runner", "androidx.test:runner:1.5.2")
            library("espresso-core", "androidx.test.espresso:espresso-core:3.5.1")
            library("core-ktx", "androidx.core:core-ktx:1.10.1")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")

            library("lifecycle-viewmodel-ktx", "androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")
            library("lifecycle-livedata-ktx", "androidx.lifecycle", "lifecycle-livedata-ktx").versionRef("lifecycle")

            library("appcompat", "androidx.appcompat:appcompat:1.6.1")
            library("material", "com.google.android.material:material:1.9.0")

            library("navigation-fragment-ktx", "androidx.navigation", "navigation-fragment-ktx").versionRef("navigation")
            library("navigation-ui-ktx", "androidx.navigation", "navigation-ui-ktx").versionRef("navigation")

            library("gson", "com.google.code.gson:gson:2.10.1")
            library("glide-glide", "com.github.bumptech.glide", "glide").versionRef("glide")
            library("glide-compiler", "com.github.bumptech.glide", "compiler").versionRef("glide")

            library("material-dialogs-core", "com.afollestad.material-dialogs", "core").versionRef("materialDialogs")
            library("material-dialogs-datetime", "com.afollestad.material-dialogs", "datetime").versionRef("materialDialogs")

            library("retrofit", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("retrofit-scalars", "com.squareup.retrofit2", "converter-scalars").versionRef("retrofit")
            library("retrofit-gson", "com.squareup.retrofit2", "converter-gson").versionRef("retrofit")

            library("okhttp", "com.squareup.okhttp3", "okhttp").versionRef("okhttp")
            library("okhttp-logging-interceptor", "com.squareup.okhttp3", "logging-interceptor").versionRef("okhttp")
        }
    }
}