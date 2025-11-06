package com.wisemen.compose.foundation.extension

import kotlinx.datetime.*

fun Instant.toLocalDateTimeAt(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return this.toLocalDateTime(timeZone)
}

fun LocalDateTime.toInstantAt(timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant {
    return this.toInstant(timeZone)
}

fun LocalDate.isWeekend(): Boolean {
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
}

fun LocalDateTime.startOfDay(): LocalDateTime {
    return LocalDateTime(date, LocalTime(0, 0))
}

fun LocalDateTime.endOfDay(): LocalDateTime {
    return LocalDateTime(date, LocalTime(23, 59, 59, 999_999_999))
}