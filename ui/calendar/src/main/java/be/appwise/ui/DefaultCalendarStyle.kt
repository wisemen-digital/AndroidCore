package be.appwise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.appwise.calendar.data.IEvent

object DefaultCalendarStyle {

    @Composable
    fun Clicked(day:String) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.Green.copy(0.2f), RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = day,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun Today(day:String) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.Red.copy(0.2f), RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = day,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun SingleEventIndicator() {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .border(1.dp, Color.Gray, RoundedCornerShape(7.dp)),
        )
    }

    @Composable
    fun EventIndicator(event: IEvent) {
        Box(
            modifier = Modifier
                .padding(horizontal = 1.dp)
                .size(5.dp)
                .clip(CircleShape)
                .background(event.color)
        )
    }

    @Composable
    fun Day(day:String, style: TextStyle, color: Color) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.Transparent, RoundedCornerShape(7.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = day,
                style = style,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}