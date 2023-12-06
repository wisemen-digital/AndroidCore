package be.appwise.sample_compose.data.mock

import androidx.compose.ui.graphics.Color
import be.appwise.sample_compose.data.entity.Type

val Type.Companion.MOCK_TYPES: List<Type>
    get() = listOf(
        Type(
            "Local Event",
            Color.Red
        ),
        Type(
            "National Event",
            Color.Blue
        ),
        Type(
            "International Event",
            Color.Black
        )
    )