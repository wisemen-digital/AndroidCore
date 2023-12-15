package be.appwise.sample_compose.feature.overviewButtons

sealed class OverviewButtonsUiEvent {
    data object NavigateBack: OverviewButtonsUiEvent()
}