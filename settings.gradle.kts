pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }

    plugins {

        id("com.android.application").version("8.0.1")
        id("com.android.library").version("8.0.1")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
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