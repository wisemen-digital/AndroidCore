package be.appwise.core.util

import com.orhanobut.hawk.Hawk
import kotlin.reflect.KProperty

class HawkValueDelegate<in R, T>(private val key: String, private val defaultValue: T) {
    operator fun getValue(thisRef: R, property: KProperty<*>): T {
        if (Hawk.isBuilt()){
            return Hawk.get(key, defaultValue)
        }else{
            throw Exception("Please add Hawk to the Core init in order to use it")
        }
    }

    operator fun setValue(thisRef: R, property: KProperty<*>, newValue: T) {
        if (Hawk.isBuilt()) {
            if (newValue != null) {
                Hawk.put(key, newValue)
            } else {
                Hawk.delete(key)
            }
        }else{
            throw Exception("Please add Hawk to the Core init in order to use it")
        }
    }
}