package be.appwise.sample_compose.feature.overviewEditText

sealed class OverviewEditTextUiEvent {
    data object NavigateBack: OverviewEditTextUiEvent()
}