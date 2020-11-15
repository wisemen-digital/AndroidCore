package be.appwise.core.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.appwise.core.R
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import kotlinx.coroutines.*
import retrofit2.Call
import java.net.UnknownHostException

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

    /**
     * Wrap your network call with this function to have a centralized way to handle response errors
     *
     * @param call Retrofit call
     * @return Type returned by the network call
     */
    suspend fun <T : Any?> doCall(call: Call<T>): T? {
        return try {
            withContext(Dispatchers.IO) {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    throw Exception(Networking.parseError(response).message)
                }
            }
        } catch (ex: UnknownHostException) {
            throw Exception(CoreApp.getContext().getString(R.string.internet_connection_error))
        }
    }
}