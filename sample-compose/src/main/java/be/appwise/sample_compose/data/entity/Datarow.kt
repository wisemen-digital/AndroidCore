package be.appwise.sample_compose.data.entity

import androidx.compose.ui.graphics.vector.ImageVector
import com.wiselab.groupdatarow.data.IDatarow

data class Datarow(
    override val title: String,
    override val value: String?,
    override val icon: ImageVector?,
    override val onClick: (() -> Unit)?
) : IDatarow {
    companion object {
    }
}