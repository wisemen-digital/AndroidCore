package be.appwise.sample_compose.data.entity

import androidx.compose.ui.graphics.Color
import be.appwise.calendar.data.IType

data class Type(
    override val name: String,
    override val color: Color
) : IType {
    companion object {
    }
}