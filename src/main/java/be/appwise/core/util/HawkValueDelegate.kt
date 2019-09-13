package be.appwise.core.util

import com.orhanobut.hawk.Hawk
import kotlin.reflect.KProperty

class HawkValueDelegate<in R, T>(private val key: String, private val defaultValue: T) {
    operator fun getValue(thisRef: R, property: KProperty<*>): T = Hawk.get<T>(key, defaultValue)
    operator fun setValue(thisRef: R, property: KProperty<*>, newValue: T) {
        if (newValue != null) {
            Hawk.put(key, newValue)
        } else {
            Hawk.delete(key)
            
        }
    }
}