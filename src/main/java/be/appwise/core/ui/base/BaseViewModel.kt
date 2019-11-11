package be.appwise.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

open class BaseViewModel(val compositeDisposable: CompositeDisposable = CompositeDisposable()) : ViewModel() {

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
        compositeDisposable.dispose()
    }
}