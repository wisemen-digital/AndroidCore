package be.appwise.calendar.data

import java.time.LocalDate

interface IEvent {
    val startDate: LocalDate
    val endDate: LocalDate
        get() = startDate
    val type: IType
}