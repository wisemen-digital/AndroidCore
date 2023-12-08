package com.wiselab.groupdatarow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiselab.groupdatarow.data.IDatarow

@Composable
fun Datarow(datarowList: List<IDatarow>) {

    Column {
        for (datarow in datarowList) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        datarow.onClick()
                    }
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = datarow.title
                )
                datarow.value?.let {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = it
                    )
                }
                datarow.icon?.let { Icon(imageVector = it, contentDescription = null) }
            }
        }
    }

}

@Preview
@Composable
fun DatarowPreview() {

    val datarowList = listOf<IDatarow>(
        GenericDatarowPreview("Appwise", "5", Icons.Outlined.ChevronRight),
        GenericDatarowPreview("Appwise", "5"),
        GenericDatarowPreview("Appwise")
    )

    MaterialTheme {
        Datarow(datarowList)
    }
}

data class GenericDatarowPreview(
    override val title: String,
    override val value: String? = null,
    override val icon: ImageVector? = null,
    override val onClick: () -> Unit? = {}
) : IDatarow