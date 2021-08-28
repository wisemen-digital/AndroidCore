package be.appwise.realm

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class RealmResultsLiveData<T : RealmModel>(private val results: RealmResults<T>) : LiveData<List<T>>() {
    private val listener: RealmChangeListener<RealmResults<T>> = RealmChangeListener { updates -> value = updates }

    override fun onActive() {
        super.onActive()
        results.addChangeListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        results.removeChangeListener(listener)
    }
}