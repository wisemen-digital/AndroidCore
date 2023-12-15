package be.appwise.sample_compose.feature.overviewDatagroup

import be.appwise.core.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class OverviewDatagroupViewModel: BaseViewModel() {
    private val eventChannel = Channel<OverviewDatagroupUiEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onAction(overviewDatagroupUiAction: OverviewDatagroupUiAction){
        when(overviewDatagroupUiAction){
            is OverviewDatagroupUiAction.Back -> eventChannel.trySend(OverviewDatagroupUiEvent.NavigateBack)
     }
    }
}