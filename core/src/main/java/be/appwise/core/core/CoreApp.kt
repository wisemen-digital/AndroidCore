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

    internal fun initialize(context: Context): CoreApp {
        appContext = context

        initializeHawk()

        return this
    }

    fun init(function: CoreBuilder.() -> Unit) {
        CoreBuilder().apply(function).build()
    }
}