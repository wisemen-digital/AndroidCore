plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

project.tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}