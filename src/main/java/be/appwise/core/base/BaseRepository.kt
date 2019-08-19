package be.appwise.core.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.realm.Realm

open class BaseRepository {
    val realm: Realm = Realm.getDefaultInstance()
    private val mDisposables = CompositeDisposable()

    protected fun addCall(d: Disposable) = mDisposables.add(d)

    fun disposeCalls() = mDisposables.dispose()
}