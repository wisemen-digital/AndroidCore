package com.wiselab.groupdatarow.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wiselab.groupdatarow.data.IDatarow


object DefaultDatarow {
    @Composable
    fun Datarow(datarow: IDatarow) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 11.dp)
                .clickable(enabled = datarow.onClick != null) {
                    datarow.onClick?.let { it() }
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

            if(datarow.icon != null && datarow.onClick != null){
                datarow.icon?.let { Icon(imageVector = it, contentDescription = null) }
            }
        }
    }
}