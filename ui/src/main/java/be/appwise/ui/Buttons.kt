package be.appwise.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object Buttons {
    @Composable
    fun Primary(
        text: String,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        containerColor: Color = MaterialTheme.colorScheme.primary,
        style: TextStyle = LocalTextStyle.current,
        buttonShape: Shape = RoundedCornerShape(5.dp),
        leading: (@Composable () -> Unit) = { },
        trailing: (@Composable () -> Unit) = { },
        onClick: () -> Unit = {}
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                leading()
                Text(
                    text = text,
                    style = style
                )
                trailing()
            }
        }
    }

    @Composable
    fun Secondary(
        text: String,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        style: TextStyle = LocalTextStyle.current,
        buttonShape: Shape = RoundedCornerShape(5.dp),
        onClick: () -> Unit = {}
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Text(
                text = text,
                style = style
            )
        }
    }

    @Composable
    fun Tertiary(
        text: String,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
        containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
        style: TextStyle = LocalTextStyle.current,
        buttonShape: Shape = RoundedCornerShape(5.dp),
        onClick: () -> Unit = {}
    ) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContentColor = contentColor,
                disabledContainerColor = containerColor.copy(0.4f)
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = text,
                    style = style
                )
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }
    }

    @Composable
    fun Delete(
        text: String,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentColor: Color = MaterialTheme.colorScheme.error,
        containerColor: Color = MaterialTheme.colorScheme.errorContainer,
        style: TextStyle = LocalTextStyle.current,
        buttonShape: Shape = RoundedCornerShape(5.dp),
        onClick: () -> Unit = {}
    ) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = text,
                style = style
            )
        }
    }
}

@Preview
@Composable
fun ButtonsPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Buttons.Primary(
            text = "Primary",
            Modifier.fillMaxWidth()
        ) {}
        Buttons.Primary(
            text = "Primary",
            Modifier.fillMaxWidth(),
            leading = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "") }
        ) {}
        Buttons.Primary(
            text = "Primary",
            Modifier.fillMaxWidth(),
            trailing = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "") }
        ) {}
        Buttons.Primary(
            text = "Primary",
            Modifier.fillMaxWidth(),
            leading = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "") },
            trailing = { Icon(imageVector = Icons.Outlined.Add, contentDescription = "") }
        ) {}
        Buttons.Secondary(
            text = "Secondary",
            Modifier.fillMaxWidth()
        ) {}

        Buttons.Tertiary(
            text = "Tertiary",
            enabled = true,
            modifier = Modifier.fillMaxWidth()
        ) {}

        Buttons.Tertiary(
            text = "Tertiary disabled",
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) {}

        Buttons.Delete(
            text = "Delete",
            Modifier.fillMaxWidth()
        ) {}
    }

}