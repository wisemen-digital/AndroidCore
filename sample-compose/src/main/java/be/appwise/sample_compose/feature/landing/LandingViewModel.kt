package be.appwise.sample_compose.feature.landing

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LandingViewModel: BaseViewModel() {
    private val eventChannel = Channel<LandingUiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onAction(landingUiAction: LandingUiAction){
        when(landingUiAction){
            is LandingUiAction.ToButtons -> eventChannel.trySend(LandingUiEvent.NavigateToButtons)
            is LandingUiAction.ToCalendar -> eventChannel.trySend(LandingUiEvent.NavigateToCalendar)
            is LandingUiAction.ToEditText -> eventChannel.trySend(LandingUiEvent.NavigateToEditText)
            is LandingUiAction.ToDatagroup -> eventChannel.trySend(LandingUiEvent.NavigateToDatagroup)
        }
    }
}