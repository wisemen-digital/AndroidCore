package be.appwise.core.core

import android.content.Context
import io.realm.RealmConfiguration

object CoreApp {
    private var coreFacade: CoreFacade? = CoreFacade.EmptyCoreFacade()

    fun init(context: Context): CoreBuilder {
        coreFacade = null
        return CoreBuilder(context)
    }

    internal fun build(coreBuilder: CoreBuilder) {
        coreFacade = DefaultCoreFacade(coreBuilder)
    }
}