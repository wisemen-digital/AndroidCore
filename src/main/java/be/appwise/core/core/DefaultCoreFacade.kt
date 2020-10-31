package be.appwise.core.core

import android.content.Context

class DefaultCoreFacade(private var coreBuilder: CoreBuilder) : CoreFacade {
    override fun getContext(): Context {
        return coreBuilder.context
    }
}