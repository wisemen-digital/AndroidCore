package be.appwise.sample_compose.data.mock

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import be.appwise.sample_compose.data.entity.Datarow
import com.wiselab.groupdatarow.CustomDatarowPreview
import com.wiselab.groupdatarow.GenericDatarowPreview
import com.wiselab.groupdatarow.data.IDatarow

val Datarow.Companion.MOCK_DATAROW: List<IDatarow>
    get() = listOf(
        GenericDatarowPreview("Navigate Back", icon = Icons.Outlined.ChevronRight) {},
        GenericDatarowPreview("Group", "5",icon = Icons.Outlined.ChevronRight) {},
        GenericDatarowPreview("Versie", "1.4.2"),
        CustomDatarowPreview(
            "Datarow with composable",
            iconComp = {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        )
    )