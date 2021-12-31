package be.appwise.core.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.appwise.core.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel() {
    @Suppress("MemberVisibilityCanBePrivate")
    var vmScope = viewModelScope

    private val _coroutineException = SingleLiveEvent<Throwable?>()
    val coroutineException: LiveData<Throwable?> = _coroutineException

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading get() = _loading as LiveData<Boolean>

    fun isLoading(loading: Boolean) {
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

    private fun setDefaultExceptionHandler() {
        vmScope = (viewModelScope + CoroutineExceptionHandler { _, throwable ->
            _coroutineException.value = throwable
        })
    }

    init {
        setDefaultExceptionHandler()
    }
}