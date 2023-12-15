package be.appwise.sample_compose.feature.landing

sealed class LandingUiAction {
    data object ToCalendar: LandingUiAction()
    data object ToButtons: LandingUiAction()
    data object ToEditText: LandingUiAction()
}