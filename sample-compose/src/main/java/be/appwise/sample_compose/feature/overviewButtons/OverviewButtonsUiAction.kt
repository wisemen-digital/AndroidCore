package be.appwise.sample_compose.feature.overviewButtons

sealed class OverviewButtonsUiAction {
    data object Back: OverviewButtonsUiAction()
}