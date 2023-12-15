package be.appwise.sample_compose.feature.overviewDatagroup

sealed class OverviewDatagroupUiEvent {
    data object NavigateBack: OverviewDatagroupUiEvent()
}