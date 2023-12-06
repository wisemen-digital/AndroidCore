package be.appwise.sample_compose.data.entity

import be.appwise.calendar.data.IEvent
import be.appwise.calendar.data.IType
import java.time.LocalDate

data class Event(
    override val startDate: LocalDate,
    override val type: IType
) : IEvent {
    companion object {
    }
}