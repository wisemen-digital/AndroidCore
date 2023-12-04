package be.appwise.calendar.data

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

interface IEvent {
    val startDate: LocalDate
    val endDate: LocalDate
        get() = startDate
    val color: Color
}