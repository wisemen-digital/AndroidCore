package be.appwise.sample_compose.feature.overviewCalendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.appwise.calendar.Calendar
import be.appwise.calendar.data.IType
import be.appwise.calendar.util.extensions.scrollToNextMonth
import be.appwise.calendar.util.extensions.scrollToPrevMonth
import be.appwise.calendar.util.extensions.scrollToToday
import be.appwise.sample_compose.data.entity.Event
import be.appwise.sample_compose.data.mock.MOCK_EVENTS
import be.appwise.sample_compose.ui.theme.CoreDemoTheme
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OverviewCalendar() {

    val state = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var weekStartsOn by remember { mutableStateOf(DayOfWeek.SUNDAY) }

    Column {
        Row(
            modifier= Modifier.padding(horizontal= 16.dp),
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
                text = "Today"
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
                text = "Switch Week starts on"
            )
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.scrollToPrevMonth()
                    }
                }
            ) {
                Image(imageVector = Icons.Outlined.ChevronLeft, contentDescription = "to next month")
            }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.scrollToNextMonth()
                    }
                }
            ){
                Image(imageVector = Icons.Outlined.ChevronRight, contentDescription = "to next month")
            }
        }

        Calendar(
            singleEventIndicatorComp = {},
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
        OverviewCalendar()
    }
}