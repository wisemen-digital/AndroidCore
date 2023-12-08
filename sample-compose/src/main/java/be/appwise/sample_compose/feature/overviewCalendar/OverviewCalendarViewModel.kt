package be.appwise.sample_compose.feature.overviewCalendar

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class OverviewCalendarViewModel: BaseViewModel() {
    private val eventChannel = Channel<OverviewCalendarUiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onAction(overviewCalendarUiAction: OverviewCalendarUiAction){
        when(overviewCalendarUiAction){
            is OverviewCalendarUiAction.Back -> eventChannel.trySend(OverviewCalendarUiEvent.NavigateBack)
     }
    }
}