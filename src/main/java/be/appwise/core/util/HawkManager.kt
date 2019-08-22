package be.appwise.core.util

import com.orhanobut.hawk.Hawk
import kotlin.reflect.KProperty

class HawkManager {
    companion object {
        const val CURRENT_USER = "current_user"

        @JvmStatic
        var currentUserId: Int by HawkValueDelegate(CURRENT_USER,-1)
    }

    class HawkBooleanDelegate<R>(private val key: String) {
        operator fun getValue(thisRef: R, property: KProperty<*>): Boolean = Hawk.contains(key)
        operator fun setValue(thisRef: R, property: KProperty<*>, newValue: Boolean) {
            if (newValue) {
                Hawk.put(key, newValue)
            } else {
                Hawk.delete(key)
            }
        }
    }

    class HawkValueDelegate<in R, T>(private val key: String,private val defaultValue : T) {
        operator fun getValue(thisRef: R, property: KProperty<*>): T = Hawk.get<T>(key,defaultValue)
        operator fun setValue(thisRef: R, property: KProperty<*>, newValue: T) {
            if (newValue != null) {
                Hawk.put(key, newValue)
            } else {
                Hawk.delete(key)
            }
        }
    }
}