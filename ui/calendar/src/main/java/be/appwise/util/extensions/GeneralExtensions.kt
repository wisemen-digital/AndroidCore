package be.appwise.util.extensions

import be.appwise.calendar.data.IEvent
import java.time.LocalDate

fun String.capitalize(): String {
    return this.replaceFirstChar { char -> char.uppercaseChar() }
}

fun List<IEvent>.eventsOfDay(date: LocalDate): List<IEvent> {
    return this.filter { item ->
        item.startDate == date
    }
}