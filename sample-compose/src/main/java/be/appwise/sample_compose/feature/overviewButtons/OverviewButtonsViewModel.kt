package be.appwise.sample_compose.feature.overviewButtons

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class OverviewButtonsViewModel: BaseViewModel() {
    private val eventChannel = Channel<OverviewButtonsUiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onAction(overviewButtonsUiAction: OverviewButtonsUiAction){
        when(overviewButtonsUiAction){
            is OverviewButtonsUiAction.Back -> eventChannel.trySend(OverviewButtonsUiEvent.NavigateBack)
     }
    }
}