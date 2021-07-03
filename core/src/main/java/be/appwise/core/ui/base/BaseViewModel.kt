package be.appwise.core.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {
    @Suppress("MemberVisibilityCanBePrivate")
    var vmScope = viewModelScope

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading get() = _loading as LiveData<Boolean>

    private fun isLoading(loading: Boolean) {
        _loading.postValue(loading)
    }

    private suspend fun showLoading(onSuccess: suspend () -> Unit) {
        isLoading(true)
        onSuccess()
        isLoading(false)
    }

    fun launchAndLoad(onSuccess: suspend () -> Unit) = vmScope.launch {
        showLoading(onSuccess)
    }

    fun setDefaultExceptionHandler(onError: (error: Throwable) -> Unit = {}) {
        vmScope = vmScopeWithCustomExceptionHandler(onError)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun vmScopeWithCustomExceptionHandler(onError: (error: Throwable) -> Unit = {}) =
        (viewModelScope + CoroutineExceptionHandler { _, throwable ->
            isLoading(false)
            onError(throwable)
        })
}