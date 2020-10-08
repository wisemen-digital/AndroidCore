package be.appwise.core.core

import android.content.Context
import be.appwise.core.BuildConfig
import be.appwise.core.networking.Networking
import be.appwise.core.networking.NetworkingBuilder
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class CoreBuilder(internal val context: Context) {
    fun <T> initializeNetworking(networkingBuilder: NetworkingBuilder, apiManagerService: Class<T>): CoreBuilder {
        Networking.build(networkingBuilder.setContext(context), apiManagerService)

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

    /***
     * Initialize Hawk, This way you don't need to add the dependency to the 'real' application/project
     */
    fun initializeHawk(): CoreBuilder {
        Hawk.init(context)
            .build()

        return this
    }

    fun initializeErrorActivity(showErrorDetails: Boolean = false): CoreBuilder{
        CaocConfig.Builder.create()
            .showErrorDetails(showErrorDetails)
            .apply()

        return this
    }

    fun build() {
        CoreApp.build(this)
    }
}