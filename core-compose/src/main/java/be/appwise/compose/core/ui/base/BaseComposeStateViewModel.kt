package be.appwise.compose.core.ui.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

open class BaseComposeStateViewModel<UIState, NavigationType>(initialState: UIState): BaseComposeViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _navigation = Channel<NavigationType>()
    val navigation = _navigation.receiveAsFlow()

    fun updateUIState(state: UIState) {
        _uiState.tryEmit(state)
    }

    fun navigate(navigation: NavigationType) {
        _navigation.trySend(navigation)
    }
}
