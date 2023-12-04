package be.appwise.calendar

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

interface IEvent {
    val startDate: LocalDate
    val endDate: LocalDate
        get() = startDate
    val name: String
    val description: String
        get() = ""
    val color: Color
}