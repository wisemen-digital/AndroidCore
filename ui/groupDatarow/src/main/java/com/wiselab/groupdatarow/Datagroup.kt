package com.wiselab.groupdatarow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiselab.groupdatarow.data.IDatarow
import com.wiselab.groupdatarow.data.IDatarowGroup
import com.wiselab.groupdatarow.ui.DefaultDatarow

@Composable
fun Datagroup(
    datarowGroup: IDatarowGroup,
    divider: (@Composable () -> Unit) = { Divider() },
    datarowComp: (@Composable (datarow: IDatarow) -> Unit) = {DefaultDatarow.Datarow(datarow = it)},
    cardColors: CardColors = CardDefaults.cardColors(),
    cardShape: Shape = RoundedCornerShape(7.dp),
    textStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.41).sp
    )
) {

    Column {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = datarowGroup.title,
            style = textStyle
        )

        Card(
            colors= cardColors,
            shape = cardShape
        ) {
            Column {
                for (datarow in datarowGroup.datarowList) {
                    datarowComp(datarow)

                    if(datarowGroup.datarowList.last() != datarow){
                        divider()
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun DatarowPreview() {

    val datarowList = listOf<IDatarow>(
        GenericDatarowPreview("Algemene voorwaarden", icon = Icons.Outlined.ChevronRight) {},
        GenericDatarowPreview("Versie", "1.4.2"),
    )
    
    val datarowGroup = DatarowGroupPreview("Over de app", datarowList)

    MaterialTheme {
        Datagroup(datarowGroup)
    }
}

data class GenericDatarowPreview(
    override val title: String,
    override val value: String? = null,
    override val icon: ImageVector? = null,
    override val onClick: (() -> Unit)? = null
) : IDatarow

data class DatarowGroupPreview(
    override val title: String,
    override val datarowList: List<IDatarow>
):IDatarowGroup