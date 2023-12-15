package be.appwise.calendar.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyle {

    val Month = TextStyle(
        fontSize = 35.sp,
        fontWeight = FontWeight.W700,
        lineHeight = 42.64.sp,
        letterSpacing = 0.39.sp
    )

    val Year = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 42.64.sp,
        letterSpacing = 0.39.sp
    )

    val OverviewDay = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W600,
        lineHeight = 14.32.sp
    )

    val Day = TextStyle(
        fontSize = 12.sp,
        lineHeight = 14.32.sp
    )
}