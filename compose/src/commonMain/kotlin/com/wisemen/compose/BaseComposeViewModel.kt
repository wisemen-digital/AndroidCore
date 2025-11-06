package com.wisemen.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wisemen.compose.foundation.logger.logD
import com.wisemen.compose.foundation.logger.logE
import com.wisemen.compose.foundation.result.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseComposeViewModel<UiState : BaseUiState, UiAction, UiEvent> : ViewModel() {

    // Subclasses only need to provide the initial state
    protected abstract fun initialState(): UiState

    // Automatically managed state - no need to override
    protected val _uiState by lazy { MutableStateFlow(initialState()) }
    val uiState: StateFlow<UiState> by lazy { _uiState.asStateFlow() }

    // Events channel for one-time UI events
    private val _events = MutableSharedFlow<UiEvent>(replay = 0)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    // Loading state management
    private val _loadingStates = MutableStateFlow<Set<String>>(emptySet())
    val isLoading: StateFlow<Boolean> = _loadingStates.map { it.isNotEmpty() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    // Abstract method for subclasses to implement
    abstract fun handleAction(action: UiAction)

    // Default implementation - no need to override
    protected fun updateUiState(update: (UiState) -> UiState) {
        _uiState.value = update(_uiState.value)
    }

    /**
     * Enhanced launchAndLoad with comprehensive loading state management
     */
    fun launchAndLoad(
        loadingKey: String = DEFAULT_LOADING_KEY,
        showLoading: Boolean = true,
        onError: (Throwable) -> Unit = ::handleError,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (showLoading) {
                    setLoading(loadingKey, true)
                }

                logD("Starting operation: $loadingKey")
                block()
                logD("Completed operation: $loadingKey")

            } catch (e: Exception) {
                logE("Error in operation $loadingKey: ${e.message}", e)
                onError(e)
            } finally {
                if (showLoading) {
                    setLoading(loadingKey, false)
                }
            }
        }
    }

    /**
     * Execute a Resource-based operation with automatic state management
     */
    fun <T> executeResource(
        loadingKey: String = DEFAULT_LOADING_KEY,
        operation: suspend () -> Resource<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable, String?) -> Unit = { throwable, message ->
            handleError(throwable, message)
        }
    ) {
        launchAndLoad(loadingKey = loadingKey) {
            when (val result = operation()) {
                is Resource.Success -> {
                    logD("Resource operation successful: $loadingKey")
                    onSuccess(result.data)
                }
                is Resource.Error -> {
                    logE("Resource operation failed: $loadingKey - ${result.message}", result.exception)
                    onError(result.exception, result.message)
                }
                is Resource.Loading -> {
                    // Loading state already handled by launchAndLoad
                }
            }
        }
    }

    /**
     * Send a one-time event to the UI
     */
    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            logD("Sending event: ${event!!::class.simpleName ?: "Unknown"}")
            _events.emit(event)
        }
    }

    /**
     * Show a snackbar message
     */
    protected fun showSnackbar(message: String) {
        updateUiState { state ->
            state.withSnackbar(message) as UiState
        }
    }

    /**
     * Clear the snackbar message
     */
    protected fun clearSnackbar() {
        updateUiState { state ->
            state.withSnackbar(null) as UiState
        }
    }

    /**
     * Show an error message
     */
    protected fun showError(error: String) {
        updateUiState { state ->
            state.withError(error) as UiState
        }
    }

    /**
     * Clear the error message
     */
    protected fun clearError() {
        updateUiState { state ->
            state.withError(null) as UiState
        }
    }

    /**
     * Handle errors with optional custom message
     */
    protected open fun handleError(error: Throwable, customMessage: String? = null) {
        val message = customMessage ?: error.message ?: "An unexpected error occurred"
        logE("Handling error: $message", error)
        showError(message)
    }

    /**
     * Manage loading states for multiple concurrent operations
     */
    private fun setLoading(key: String, loading: Boolean) {
        _loadingStates.value = if (loading) {
            _loadingStates.value + key
        } else {
            _loadingStates.value - key
        }

        // Update UI state loading flag
        updateUiState { state ->
            state.withLoading(_loadingStates.value.isNotEmpty()) as UiState
        }
    }

    /**
     * Check if a specific operation is currently loading
     */
    protected fun isLoading(key: String): Boolean = _loadingStates.value.contains(key)

    companion object {
        private const val DEFAULT_LOADING_KEY = "default"
    }
}

