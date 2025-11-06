package com.wisemen.compose

interface BaseUiState {
    val isLoading: Boolean
    val snackbarMessage: String?
    val error: String?

    // Helper methods that must be implemented by concrete classes
    fun withLoading(loading: Boolean): BaseUiState
    fun withSnackbar(message: String?): BaseUiState
    fun withError(error: String?): BaseUiState
    fun clearMessages(): BaseUiState = withSnackbar(null).withError(null)

    companion object {
        const val DEFAULT_LOADING = false
        val DEFAULT_SNACKBAR: String? = null
        val DEFAULT_ERROR: String? = null
    }
}

interface UiStateHelpers<T : BaseUiState> {
    fun T.withLoadingTyped(loading: Boolean): T
    fun T.withSnackbarTyped(message: String?): T
    fun T.withErrorTyped(error: String?): T
    fun T.clearMessagesTyped(): T = withSnackbarTyped(null).withErrorTyped(null)
}

data class SimpleUiState(
    override val isLoading: Boolean = BaseUiState.DEFAULT_LOADING,
    override val snackbarMessage: String? = BaseUiState.DEFAULT_SNACKBAR,
    override val error: String? = BaseUiState.DEFAULT_ERROR
) : BaseUiState {

    override fun withLoading(loading: Boolean): BaseUiState = copy(isLoading = loading)
    override fun withSnackbar(message: String?): BaseUiState = copy(snackbarMessage = message)
    override fun withError(error: String?): BaseUiState = copy(error = error)
}

object SimpleUiStateHelpers : UiStateHelpers<SimpleUiState> {
    override fun SimpleUiState.withLoadingTyped(loading: Boolean): SimpleUiState =
        copy(isLoading = loading)

    override fun SimpleUiState.withSnackbarTyped(message: String?): SimpleUiState =
        copy(snackbarMessage = message)

    override fun SimpleUiState.withErrorTyped(error: String?): SimpleUiState =
        copy(error = error)
}