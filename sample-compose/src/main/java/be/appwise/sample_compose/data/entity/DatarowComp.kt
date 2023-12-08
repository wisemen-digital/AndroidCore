package be.appwise.sample_compose.data.entity

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.wiselab.groupdatarow.data.IDatarowComp

data class DatarowComp(
    override val icon: ImageVector?,
    override val title: String,
    override val value: String?,
    override val iconComp: @Composable() (() -> Unit)?,
    override val onClick: (() -> Unit)?
) : IDatarowComp {
    companion object {
    }
}