package be.appwise.core.core

import android.content.Context

interface CoreFacade {
    fun getContext(): Context

    class EmptyCoreFacade : CoreFacade {
        override fun getContext(): Context {
            throw Exception("Initialize CoreApp in Application class first")
        }
    }
}