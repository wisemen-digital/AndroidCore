package com.shahar91.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

open class BaseViewModel(val compositeDisposable: CompositeDisposable = CompositeDisposable(), val realm : Realm = Realm.getDefaultInstance()) : ViewModel() {

    var vmScope = viewModelScope

    fun setDefaultExceptionHandler(handler: CoroutineExceptionHandler) {
        vmScope = viewModelScope + handler
    }

    fun setDefaultExceptionHandler(onError: (error: Throwable) -> Unit = {}){
        vmScope = vmScopeWithCustomExceptionHandler(onError)
    }

    fun vmScopeWithCustomExceptionHandler(onError: (error: Throwable) -> Unit = {}) =
        (viewModelScope + CoroutineExceptionHandler { _, throwable -> onError(throwable) })

    override fun onCleared() {
        super.onCleared()
        realm.close()
        compositeDisposable.dispose()
    }
}