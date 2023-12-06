package be.appwise.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import be.appwise.calendar.util.extensions.capitalize
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun monthDialogPreview() {
    val resources = LocalContext.current.resources
    val locale = resources.configuration.locales.get(0)
    monthDialog(locale, Month.DECEMBER)
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun monthDialog(
    locale: Locale,
    currentMonth: Month,
    onMonthClick: (month: Month) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val months = (1..12).toList()
    val date = LocalDate.now()

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
        ) {
            months.forEach {
                Text(
                    modifier = Modifier.clickable {
                        onMonthClick(Month.of(it))
                        onDismiss()
                    },
                    text = date.withMonth(it).month.getDisplayName(TextStyle.FULL, locale).capitalize(),
                    style = if (date.withMonth(it).month == currentMonth)
                        androidx.compose.ui.text.TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 24.sp,
                            lineHeight = 14.32.sp
                        )
                    else
                        androidx.compose.ui.text.TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            lineHeight = 14.32.sp
                        )
                )
            }
        }
    }
}
