package be.appwise.core.ui.base

import android.app.Application
import be.appwise.core.BuildConfig
import be.appwise.core.core.CoreApp
import com.orhanobut.hawk.Hawk

class BaseApp: Application() {
    fun init(appName: String) {
        Hawk.init(this).build()

        initCore(appName)
    }

    private fun initCore(appName: String) {
        CoreApp.init {
            initializeLogger(appName, BuildConfig.DEBUG)

            if(BuildConfig.DEBUG) {
                initializeErrorActivity(true)
            }
        }
    }
}
