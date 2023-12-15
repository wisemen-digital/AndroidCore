package be.appwise.sample_compose.data.mock

import be.appwise.sample_compose.data.entity.Datagroup
import be.appwise.sample_compose.data.entity.Datarow
import com.wiselab.groupdatarow.data.IDatarowGroup

val Datagroup.Companion.MOCK_DATAGROUP: IDatarowGroup
    get() = Datagroup("Over de app", Datarow.MOCK_DATAROW)