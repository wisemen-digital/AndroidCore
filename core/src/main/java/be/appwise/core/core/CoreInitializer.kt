package be.appwise.core.core

import android.content.Context
import androidx.startup.Initializer

internal class CoreInitializer : Initializer<CoreApp> {

    override fun create(context: Context): CoreApp = CoreApp.initialize(context)

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}