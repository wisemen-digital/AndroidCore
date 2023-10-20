package com.tomtruyen.twodimensional.core.compose.ui.base

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseComposeViewModel<NavigationType, UIState, UIEvent>: BaseViewModel() {
    private val _uiState = MutableStateFlow<UIState?>(null)
    val uiState = _uiState.asStateFlow()

    private val _navigation = Channel<NavigationType>()
    val navigation = _navigation.receiveAsFlow()

    abstract fun onUIEvent(event: UIEvent)

    fun updateUIState(state: UIState) {
        _uiState.tryEmit(state)
    }

    fun navigate(navigation: NavigationType) {
        _navigation.trySend(navigation)
    }
}
