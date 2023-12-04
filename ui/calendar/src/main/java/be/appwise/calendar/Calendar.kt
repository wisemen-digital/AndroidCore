package be.appwise.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.appwise.calendar.data.IEvent
import be.appwise.ui.DefaultCalendarStyle
import be.appwise.util.extensions.capitalize
import be.appwise.util.extensions.eventsOfDay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import be.appwise.ui.TextStyle as defaultTextStyle
import java.time.format.TextStyle as dateTimeFormat

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(
    onClickAction: () -> Unit = {},
    onClickComp: (@Composable (text: String) -> Unit) = { DefaultCalendarStyle.Clicked(day = it) },
    currentDayComp: (@Composable (text: String) -> Unit) = { DefaultCalendarStyle.Today(day = it) },
    eventIndicatorComp: (@Composable (eventPreview: IEvent) -> Unit) = {
        DefaultCalendarStyle.EventIndicator(
            event = it
        )
    },
    singleEventIndicatorComp: (@Composable () -> Unit) = { DefaultCalendarStyle.SingleEventIndicator() },
    eventPreviews: List<EventPreview> = emptyList<EventPreview>(),
    textStyleMonth: TextStyle = defaultTextStyle.Month,
    textStyleYear: TextStyle = defaultTextStyle.Year,
    textStyleDaysOverview: TextStyle = defaultTextStyle.OverviewDay,
    textStyleDays: TextStyle = defaultTextStyle.Day,
    dayComp: @Composable (text: String, color:Color) -> Unit = { text, color ->
        DefaultCalendarStyle.Day(
            day = text,
            style = textStyleDays,
            color = color
        )
    },
    monthsInPast: Long = 120,
    monthsInFuture: Long = 120,
    weekStartsOn: DayOfWeek = WeekFields.of(
        LocalContext.current.resources.configuration.locales[0]
    ).firstDayOfWeek,
) {
    val resources = LocalContext.current.resources
    val locale = resources.configuration.locales[0]

    val now = LocalDate.now()

    val listMonths = setup(
        now.minusMonths(monthsInPast),
        now.plusMonths(monthsInFuture),
        weekStartsOn
    )

    val clickedDate = remember {
        mutableStateOf(LocalDate.now())
    }

    val pagerState = rememberPagerState(monthsInPast.toInt())
    val coroutineScope = rememberCoroutineScope()

    fun toNextMonth() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    fun toPrevMonth() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }

    Column {

        HorizontalPager(
            state = pagerState,
            pageCount = listMonths.size,
            verticalAlignment = Alignment.Top
        ) { index ->

            val pagerYearMonth = now.minusMonths(monthsInPast - index)
            val showMonthDialog = remember { mutableStateOf(false) }
            val showYearDialog = remember { mutableStateOf(false) }

            if (showMonthDialog.value) {
                monthDialog(
                    locale = locale,
                    currentMonth = pagerYearMonth.month,
                    onMonthClick = {
                        val move = it.value - pagerYearMonth.monthValue
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + move)
                        }
                    },
                    onDismiss = { showMonthDialog.value = !showMonthDialog.value })
            }

            if (showYearDialog.value) {
                yearDialog(
                    currentYear = pagerYearMonth.year,
                    monthsInFuture = monthsInFuture,
                    monthsInPast = monthsInPast,
                    onYearClick = {
                        val move = it - pagerYearMonth.year
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + (move * 12))
                        }
                    },
                    onDismiss = { showYearDialog.value = !showYearDialog.value })
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 7.dp),
            ) {
                Row() {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .padding(end = 4.dp)
                            .alignByBaseline()
                            .clickable {
                                showMonthDialog.value = !showMonthDialog.value
                            },
                        text = pagerYearMonth.month.getDisplayName(
                            dateTimeFormat.FULL,
                            locale
                        ).capitalize(),
                        style = textStyleMonth
                    )
                    Text(
                        modifier = Modifier
                            .alignByBaseline()
                            .clickable {
                                showYearDialog.value = !showYearDialog.value
                            },
                        text = pagerYearMonth.year.toString(),
                        style = textStyleYear
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(7) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = weekStartsOn.plus((it).toLong())
                                .getDisplayName(dateTimeFormat.SHORT, locale)
                                .capitalize(),
                            style = textStyleDaysOverview,
                            textAlign = TextAlign.Center
                        )

                    }
                    itemsIndexed(listMonths[index]) { index, day ->

                        val lengthPrevMonth = pagerYearMonth.minusMonths(1).month.maxLength()
                        val lengthThisMonth = pagerYearMonth.month.maxLength()
                        val lengthWeek = 7

                        val date: LocalDate = when {
                            (index <= lengthWeek && day > lengthPrevMonth - lengthWeek) -> {
                                pagerYearMonth.minusMonths(
                                    1
                                ).withDayOfMonth(day)
                            }

                            (index > lengthThisMonth && day < lengthWeek) -> {
                                pagerYearMonth.plusMonths(
                                    1
                                ).withDayOfMonth(day)
                            }

                            else -> pagerYearMonth.withDayOfMonth(day)
                        }

                        val color: Color = when {
                            (index <= lengthWeek && day > lengthPrevMonth - lengthWeek) -> {
                                textStyleDays.color.copy(0.4f)
                            }
                            (index >= lengthThisMonth && day < lengthWeek) -> {
                                textStyleDays.color.copy(0.4f)
                            }
                            else -> textStyleDays.color

                        }

                        dayInCalendar(
                            onClickAction = {
                                onClickAction()
                                clickedDate.value = date
                            },
                            date = date,
                            clickedDate = clickedDate.value,
                            now = now,
                            onClickComp = onClickComp,
                            currentDayComp = currentDayComp,
                            dayComp = dayComp,
                            eventIndicatorComp = eventIndicatorComp,
                            singleEventIndicatorComp = singleEventIndicatorComp,
                            day = day,
                            eventsOfDay = eventPreviews.eventsOfDay(date),
                            otherColor = color
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CalendarPreview() {

    val eventPreviews = listOf(
        EventPreview(LocalDate.now(), color = Color.Red),
        EventPreview(LocalDate.now(), color = Color.Green),
        EventPreview(LocalDate.now().plusDays(2), color = Color.Green),
        EventPreview(LocalDate.now().plusDays(8), color = Color.Green),
    )

    Calendar(
        onClickAction = {},
        onClickComp = { DefaultCalendarStyle.Clicked(day = it) },
        currentDayComp = { DefaultCalendarStyle.Today(day = it) },
        eventIndicatorComp = { DefaultCalendarStyle.EventIndicator(it) },
        singleEventIndicatorComp = { DefaultCalendarStyle.SingleEventIndicator() },
        eventPreviews = eventPreviews
    )
}

data class EventPreview(
    override val startDate: LocalDate,
    override val color: Color
) : IEvent

@RequiresApi(Build.VERSION_CODES.O)
fun setup(startDate: LocalDate, endDate: LocalDate, firstDayOfWeek: DayOfWeek): List<List<Int>> {

    var current = startDate

    val calendarSetup = mutableListOf<List<Int>>()

    while (current <= endDate) {

        val prevMonth = current.minusMonths(1L)

        val firstDayOfMonth = current.withDayOfMonth(1).dayOfWeek.value
        val lastDayOfMonth = current.withDayOfMonth(current.lengthOfMonth()).dayOfWeek.value

        var daysPrevMonth = IntRange.EMPTY
        var daysNextMonth = IntRange.EMPTY

        val days = (1..current.lengthOfMonth()).toList()

        if (firstDayOfMonth != 1) {
            daysPrevMonth =
                prevMonth.lengthOfMonth() - (firstDayOfMonth - 2)..prevMonth.lengthOfMonth()
        }

        if (lastDayOfMonth != 7) {
            daysNextMonth =
                1..(7 - lastDayOfMonth)
        }

        val fullMonth = daysPrevMonth + days + daysNextMonth

        current = current.plusMonths(1)
        calendarSetup.add(fullMonth)
    }

    return calendarSetup.toList()
}