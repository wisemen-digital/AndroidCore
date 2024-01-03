package be.appwise.networking

import android.content.Context
import androidx.startup.Initializer

internal class NetworkingInitializer : Initializer<Networking> {

    override fun create(context: Context): Networking = Networking.initialize(context)

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}