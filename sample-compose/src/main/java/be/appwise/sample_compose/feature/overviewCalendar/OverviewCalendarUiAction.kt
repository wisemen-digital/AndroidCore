package be.appwise.sample_compose.feature.overviewCalendar

sealed class OverviewCalendarUiAction {
    data object Back: OverviewCalendarUiAction()
}