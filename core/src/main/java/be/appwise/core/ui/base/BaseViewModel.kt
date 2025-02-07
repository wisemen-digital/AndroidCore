package be.appwise.core.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.appwise.core.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

open class BaseViewModel : ViewModel() {
    @Suppress("MemberVisibilityCanBePrivate")
    var vmScope = viewModelScope

    private val _coroutineException = SingleLiveEvent<Throwable?>()
    val coroutineException: LiveData<Throwable?> = _coroutineException
    fun setCoroutineException(exception: Throwable?) {
        _coroutineException.postValue(exception)
    }

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    fun isLoading(loading: Boolean) {
        _loading.tryEmit(loading)
    }

    fun launchAndLoad(block: suspend CoroutineScope.() -> Unit) = vmScope.launch {
        isLoading(true)
        block()
        isLoading(false)
    }

    private fun setDefaultExceptionHandler(onError: (error: Throwable) -> Unit = {}) {
        vmScope = vmScopeWithCustomExceptionHandler(onError)
    }

    /**
     * Function that can be used to have a custom exception handler per coroutine launch
     *
     * ```
     * fun addToSomething(onFinish: (Throwable?) -> Unit) = vmScopeWithCustomExceptionHandler { onFinish(it) }.launch {
     *     callSomething {}
     *     onFinish(null)
     * }
     * ``
     *
     * Can also be overridden in a viewModel to make it more reusable
     */
    open fun vmScopeWithCustomExceptionHandler(onError: (error: Throwable) -> Unit = {}) =
        (viewModelScope + CoroutineExceptionHandler { _, throwable ->
            isLoading(false)
            onError(throwable)
            setCoroutineException(throwable)
        })

    init {
        setDefaultExceptionHandler()
    }
}
