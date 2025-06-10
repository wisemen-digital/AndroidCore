package be.appwise.core.ui.base

import android.app.Application
import be.appwise.core.BuildConfig
import be.appwise.core.core.CoreApp
import com.orhanobut.hawk.Hawk

class BaseApp: Application() {
    fun init() {
        Hawk.init(this).build()

        initCore()
    }

    private fun initCore() {
        CoreApp.init {
            if(BuildConfig.DEBUG) {
                initializeErrorActivity(true)
            }
        }
    }
}
