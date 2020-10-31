package be.appwise.core.core

import android.content.Context
import be.appwise.core.BuildConfig
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class CoreBuilder(internal val context: Context) {
    private var initializedErrorActivity = false

    /***
     * Initialize Hawk, This way you don't need to add the dependency to the 'real' application/project
     */
    internal fun initializeHawk(): CoreBuilder {
        Hawk.init(context)
            .build()

        return this
    }

    /***
     * Initialize this logger
     */
    fun initializeLogger(tag: String = "AndroidApp", isLoggable: Boolean = BuildConfig.DEBUG): CoreBuilder {
        val formatStrategy = PrettyFormatStrategy.newBuilder().tag(tag).build() /*Set a tag*/

        // Initialize Logger
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                // This makes sure the logs only show on debug builds, it is the preferred way and safest
                return isLoggable
            }
        })

        return this
    }

    fun initializeErrorActivity(showErrorDetails: Boolean = false): CoreBuilder{
        initializedErrorActivity = true

        CaocConfig.Builder.create()
            .enabled(true)
            .showErrorDetails(showErrorDetails)
            .apply()

        return this
    }

    fun build() {
        if (!initializedErrorActivity){
            CaocConfig.Builder.create()
                .enabled(false)
                .apply()
        }

        CoreApp.build(this)
    }
}