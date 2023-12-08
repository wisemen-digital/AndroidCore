package be.appwise.sample_compose.data.entity

import com.wiselab.groupdatarow.data.IDatarow
import com.wiselab.groupdatarow.data.IDatarowGroup

data class Datagroup(
    override val title: String,
    override val datarowList: List<IDatarow>
) : IDatarowGroup {
    companion object {
    }
}