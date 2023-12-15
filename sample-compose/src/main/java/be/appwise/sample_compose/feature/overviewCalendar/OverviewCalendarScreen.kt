package be.appwise.sample_compose.feature.overviewCalendar

import CoreDemoTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import be.appwise.calendar.Calendar
import be.appwise.calendar.data.IType
import be.appwise.calendar.util.extensions.scrollToNextMonth
import be.appwise.calendar.util.extensions.scrollToPrevMonth
import be.appwise.calendar.util.extensions.scrollToToday
import be.appwise.sample_compose.R
import be.appwise.sample_compose.data.entity.Event
import be.appwise.sample_compose.data.mock.MOCK_EVENTS
import be.appwise.sample_compose.feature.destinations.LandingScreenDestination
import be.appwise.sample_compose.feature.navigation.MainNavGraph
import be.appwise.sample_compose.util.extensions.scrollToNextYear
import be.appwise.sample_compose.util.extensions.scrollToPrevYear
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OverviewCalendarLayout(onAction: (OverviewCalendarUiAction) -> Unit = {}) {

    val monthsInPast = 120

    val state = rememberPagerState(
        initialPage = monthsInPast,
        initialPageOffsetFraction = 0f
    ) {
        (monthsInPast * 2) + 1
    }
    val coroutineScope = rememberCoroutineScope()

    var weekStartsOn by remember { mutableStateOf(DayOfWeek.SUNDAY) }

    val month = TextStyle(
        fontSize = 35.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.W700,
        lineHeight = 42.64.sp,
        letterSpacing = 0.39.sp
    )

    val year = TextStyle(
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.W400,
        lineHeight = 42.64.sp,
        letterSpacing = 0.39.sp
    )

    val overviewDay = TextStyle(
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.W600,
        lineHeight = 14.32.sp
    )

    Column {
        IconButton(
            onClick = {
                onAction(OverviewCalendarUiAction.Back)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(R.string.back)
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        coroutineScope.launch {
                            state.scrollToToday()
                        }
                    },
                text = stringResource(R.string.today)
            )
            Text(
                modifier = Modifier
                    .clickable {
                        weekStartsOn = if (weekStartsOn == DayOfWeek.SUNDAY) {
                            DayOfWeek.MONDAY
                        } else {
                            DayOfWeek.SUNDAY
                        }
                    }
                    .padding(horizontal = 5.dp),
                text = stringResource(R.string.switch_week_starts_on)
            )
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.scrollToPrevYear()
                    }
                }
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardDoubleArrowLeft,
                        contentDescription = stringResource(R.string.to_prev_month),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.scrollToPrevMonth()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronLeft,
                    contentDescription = stringResource(R.string.to_prev_month),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.scrollToNextMonth()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = stringResource(R.string.to_next_month),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.scrollToNextYear()
                    }
                }
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardDoubleArrowRight,
                        contentDescription = stringResource(R.string.to_next_month),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Calendar(
            textStyleMonth = month,
            textStyleYear = year,
            textStyleDaysOverview = overviewDay,
            currentDayComp = {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(1.dp, Color.Gray, RoundedCornerShape(7.dp)),
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
            onClickComp = {
                val brush = Brush.linearGradient(listOf(Color.Red, Color.Blue))
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(1.dp, brush, RoundedCornerShape(7.dp)),
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
            eventIndicatorComp = { type ->
                EventIndicator(type = type.type)
            },
            legendItemComp = { type ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    EventIndicator(type = type)
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = type.name
                    )
                }

            },
            legendColumns = 1,
            events = Event.MOCK_EVENTS,
            calendarState = state,
            weekStartsOn = weekStartsOn
        )
    }
}

@Destination
@MainNavGraph
@Composable
fun OverviewCalendar(
    navController: NavController,
    viewModel: OverviewCalendarViewModel = OverviewCalendarViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is OverviewCalendarUiEvent.NavigateBack -> navController.navigate(
                    LandingScreenDestination
                )

            }
        }
    }

    OverviewCalendarLayout(onAction = viewModel::onAction)
}

@Composable
fun EventIndicator(type: IType) {
    Box(
        modifier = Modifier
            .padding(horizontal = 1.dp)
            .size(5.dp)
            .clip(CutCornerShape(5.dp))
            .background(type.color)
    )
}

@Preview
@Composable
fun OverviewCalendarPreview() {
    CoreDemoTheme {
        OverviewCalendarLayout()
    }
}