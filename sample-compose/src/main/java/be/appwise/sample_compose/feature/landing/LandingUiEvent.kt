package be.appwise.sample_compose.feature.landing

sealed class LandingUiEvent {
    data object NavigateToButtons: LandingUiEvent()
    data object NavigateToCalendar: LandingUiEvent()
    data object NavigateToEditText: LandingUiEvent()
    data object NavigateToDatagroup: LandingUiEvent()
}