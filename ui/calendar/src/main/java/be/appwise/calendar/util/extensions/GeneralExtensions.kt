package be.appwise.calendar.util.extensions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import be.appwise.calendar.data.IEvent
import be.appwise.calendar.data.IType
import java.time.LocalDate

fun String.capitalize(): String {
    return this.replaceFirstChar { char -> char.uppercaseChar() }
}

fun List<IEvent>.eventsOfDay(date: LocalDate): List<IEvent> {
    return this.filter { item ->
        item.startDate == date
    }
}

fun List<IEvent>.allTypes(): List<IType> {
    return this.distinctBy { event ->
        event.type
    }.map { it.type }
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToToday(monthsInPast: Int = 120) {
    this.animateScrollToPage(monthsInPast)
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToNextMonth() {
    this.animateScrollToPage(this.currentPage + 1)
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToPrevMonth() {
    this.animateScrollToPage(this.currentPage - 1)
}