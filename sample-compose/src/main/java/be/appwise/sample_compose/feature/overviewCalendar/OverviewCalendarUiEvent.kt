package be.appwise.sample_compose.feature.overviewCalendar

sealed class OverviewCalendarUiEvent {
    data object NavigateBack: OverviewCalendarUiEvent()
}