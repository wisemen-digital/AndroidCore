package be.appwise.core.core

import android.content.Context
import com.orhanobut.hawk.Hawk

object CoreApp {

    @Volatile
    internal lateinit var appContext: Context

    /***
     * Initialize Hawk, This way you don't need to add the dependency to the 'real' application/project
     */
    private fun initializeHawk() {
        Hawk.init(appContext)
            .build()
    }

    /**
     * Initialize the object with the Android Start Up library using the [CoreInitializer]
     *
     * @param context The context provided by the initializer
     * @return [CoreApp] The initialized object
     */
    internal fun initialize(context: Context): CoreApp {
        appContext = context

        initializeHawk()

        return this
    }

    fun init(function: CoreBuilder.() -> Unit) {
        CoreBuilder().apply(function).build()
    }
}