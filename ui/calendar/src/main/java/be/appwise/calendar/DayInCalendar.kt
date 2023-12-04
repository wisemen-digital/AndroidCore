package be.appwise.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.appwise.calendar.data.IEvent
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun dayInCalendar(
    onClickAction: () -> Unit,
    date: LocalDate,
    clickedDate: LocalDate,
    now: LocalDate,
    onClickComp: (@Composable (text: String) -> Unit),
    currentDayComp: (@Composable (text: String) -> Unit),
    dayComp: @Composable (text: String, color:Color) -> Unit,
    eventIndicatorComp: (@Composable (eventPreview: IEvent) -> Unit) = { },
    singleEventIndicatorComp: (@Composable () -> Unit) = { },
    day: Int,
    eventsOfDay: List<IEvent>,
    otherColor: Color
) {

    Box(
        modifier = Modifier
            .clickable {
                onClickAction()
            }
    ) {

        when (date) {
            clickedDate -> onClickComp(day.toString())
            now -> currentDayComp(day.toString())
            else -> dayComp(day.toString(), otherColor)
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
