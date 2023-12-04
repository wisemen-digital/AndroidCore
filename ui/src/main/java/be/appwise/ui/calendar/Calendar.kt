package be.appwise.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.appwise.util.extensions.capitalize
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.time.format.TextStyle as dateTimeFormat

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(
    onClickAction: () -> Unit = {},
    onClickComp: (@Composable (text: String) -> Unit) = { },
    currentDayComp: (@Composable (text: String) -> Unit) = { },
    eventIndicatorComp: (@Composable (event: Event) -> Unit) = { },
    singleEventIndicatorComp: (@Composable () -> Unit) = { },
    events: List<Event> = emptyList<Event>(),
    textStyleMonth: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 35.sp,
        fontWeight = FontWeight.W700,
        lineHeight = 42.64.sp,
        letterSpacing = 0.39.sp
    ),
    textStyleYear: TextStyle = TextStyle(
        color = Color.Gray,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 42.64.sp,
        letterSpacing = 0.39.sp
    ),
    textStyleDaysOverview: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 12.sp,
        fontWeight = FontWeight.W600,
        lineHeight = 14.32.sp
    ),
    textStyleDays: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 12.sp,
        lineHeight = 14.32.sp
    ),
    monthsInPast: Long = 120L,
    monthsInFuture: Long = 120L,
    weekStartsOn: DayOfWeek = WeekFields.of(
        LocalContext.current.resources.configuration.locales.get(
            0
        )
    ).firstDayOfWeek,

    ) {

    val resources = LocalContext.current.resources
    val locale = resources.configuration.locales.get(0)

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
                    itemsIndexed(listMonths[index]) { index, item ->

                        val date: LocalDate
                        var dayStyling = textStyleDays

                        if (index <= 7 && item > (pagerYearMonth.minusMonths(1).month.maxLength() - 7)) {
                            date = pagerYearMonth.minusMonths(1L).withDayOfMonth(item)
                            dayStyling = textStyleDays.copy(color = textStyleDays.color.copy(0.4f))
                        } else if (index > (pagerYearMonth.month.maxLength() - 7) && item < 7) {
                            date = pagerYearMonth.plusMonths(1L).withDayOfMonth(item)
                            dayStyling = textStyleDays.copy(color = textStyleDays.color.copy(0.4f))
                        } else {
                            date = pagerYearMonth.withDayOfMonth(item)
                        }

                        val eventsOfDay = getEventsOfDay(events, date)

                        Box(
                            modifier = Modifier
                                .clickable {
                                    onClickAction()
                                    clickedDate.value = date
                                }
                        ) {

                            if (clickedDate.value == date) {
                                onClickComp(item.toString())
                            } else if (now == date) {
                                currentDayComp(item.toString())
                            } else {
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .background(Color.Transparent, RoundedCornerShape(7.dp)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = item.toString(),
                                        style = dayStyling,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            if (eventsOfDay.isNotEmpty()) {
                                singleEventIndicatorComp()
                            }

                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .background(Color.Transparent, RoundedCornerShape(7.dp)),
                                contentAlignment = Alignment.BottomCenter,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 5.dp)
                                ) {

                                    eventsOfDay.forEach { event ->
                                        eventIndicatorComp(event)
                                    }
                                }
                            }
                        }
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

    val events = listOf(
        Event(LocalDate.now(), name = "test Event", color = Color.Red),
        Event(LocalDate.now(), name = "test Event", color = Color.Green),
        Event(LocalDate.now().plusDays(2), name = "test Event", color = Color.Green),
        Event(LocalDate.now().plusDays(8), name = "test Event", color = Color.Green),
    )

    Calendar(
        onClickAction = {},
        onClickComp = {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(Color.Green.copy(0.2f), RoundedCornerShape(7.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        },
        currentDayComp = {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(Color.Red.copy(0.2f), RoundedCornerShape(7.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        },
        eventIndicatorComp = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 1.dp)
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(it.color)
            )
        },
        singleEventIndicatorComp = {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(1.dp, Color.Gray, RoundedCornerShape(7.dp)),
            )
        },
        events = events
    )
}

data class Event(
    override val startDate: LocalDate,
    override val name: String,
    override val color: Color
) : IEvent


fun getEventsOfDay(list: List<Event>, date: LocalDate): List<Event> {
    return list.filter { item ->
        item.startDate == date
    }
}

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