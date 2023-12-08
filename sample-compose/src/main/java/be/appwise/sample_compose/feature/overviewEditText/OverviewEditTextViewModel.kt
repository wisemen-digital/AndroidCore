package be.appwise.sample_compose.feature.overviewEditText

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class OverviewEditTextViewModel: BaseViewModel() {
    private val eventChannel = Channel<OverviewEditTextUiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onAction(overviewEditTextUiAction: OverviewEditTextUiAction){
        when(overviewEditTextUiAction){
            is OverviewEditTextUiAction.Back -> eventChannel.trySend(OverviewEditTextUiEvent.NavigateBack)
     }
    }
}