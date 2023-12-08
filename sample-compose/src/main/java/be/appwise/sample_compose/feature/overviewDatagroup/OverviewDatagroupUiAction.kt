package be.appwise.sample_compose.feature.overviewDatagroup

sealed class OverviewDatagroupUiAction {
    data object Back: OverviewDatagroupUiAction()
}