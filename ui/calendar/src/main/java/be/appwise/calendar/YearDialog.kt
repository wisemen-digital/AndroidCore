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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun yearDialog(
    currentYear: Int,
    monthsInPast: Long,
    monthsInFuture: Long,
    onYearClick: (year: Int) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val years = (currentYear - (monthsInPast.toInt()/12) ..currentYear + (monthsInFuture.toInt()/12)).toList()

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
        ) {
            years.forEach {
                Text(
                    modifier = Modifier.clickable {
                        onYearClick(it)
                        onDismiss()
                    },
                    text = it.toString(),
                    style = if (it == currentYear)
                        TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.W700,
                            fontSize = 24.sp,
                            lineHeight = 14.32.sp
                        )
                    else
                        TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            lineHeight = 14.32.sp
                        )
                )
            }
        }
    }
}
