package be.appwise.realm

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.kotlin.addChangeListener
import io.realm.kotlin.isValid
import io.realm.kotlin.removeChangeListener

class RealmLiveData<T : RealmModel>(private val model: T) : LiveData<T>() {
    private val listener: RealmChangeListener<T> = RealmChangeListener { update ->
        if (update.isValid())
            value = update
    }

    override fun onActive() {
        super.onActive()
        model.addChangeListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        model.removeChangeListener(listener)
    }
}