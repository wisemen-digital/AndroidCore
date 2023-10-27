package be.appwise.compose.core.ui.base

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseComposeViewModel<UIState, UIAction, UIEvent, NavigationType>(initialState: UIState): BaseViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _navigation = Channel<NavigationType>()
    val navigation = _navigation.receiveAsFlow()

    abstract fun onUIAction(action: UIAction)
    abstract fun onUIEvent(event: UIEvent)

    fun updateUIState(state: UIState) {
        _uiState.tryEmit(state)
    }

    fun navigate(navigation: NavigationType) {
        _navigation.trySend(navigation)
    }
}
