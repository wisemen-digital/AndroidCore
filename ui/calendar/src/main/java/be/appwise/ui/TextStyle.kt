package be.appwise.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyle {

    val Month = TextStyle(
    color = Color.Black,
    fontSize = 35.sp,
    fontWeight = FontWeight.W700,
    lineHeight = 42.64.sp,
    letterSpacing = 0.39.sp
    )

    val Year = TextStyle(
    color = Color.Gray,
    fontSize = 16.sp,
    fontWeight = FontWeight.W400,
    lineHeight = 42.64.sp,
    letterSpacing = 0.39.sp
    )

    val OverviewDay = TextStyle(
    color = Color.Black,
    fontSize = 12.sp,
    fontWeight = FontWeight.W600,
    lineHeight = 14.32.sp
    )

    val Day = TextStyle(
    color = Color.Black,
    fontSize = 12.sp,
    lineHeight = 14.32.sp
    )
}