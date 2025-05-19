package be.appwise.core.core

import cat.ereza.customactivityoncrash.config.CaocConfig

class CoreBuilder {
    private var initializedErrorActivity = false

    fun initializeErrorActivity(showErrorDetails: Boolean = false) {
        initializedErrorActivity = true

        CaocConfig.Builder.create()
            .enabled(true)
            .showErrorDetails(showErrorDetails)
            .apply()
    }

    internal fun build() {
        if (!initializedErrorActivity){
            CaocConfig.Builder.create()
                .enabled(false)
                .apply()
        }
    }
}