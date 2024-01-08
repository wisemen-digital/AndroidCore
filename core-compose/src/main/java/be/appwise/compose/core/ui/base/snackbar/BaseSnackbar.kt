package be.appwise.compose.core.ui.base.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun BaseSnackbar(
    message: String,
    containerColor: Color,
    icon: ImageVector? = null,
) {
    Snackbar(
        containerColor = containerColor,
        modifier = Modifier.padding(8.dp).zIndex(100f),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(icon != null) {
                Icon(
                    icon,
                    tint = Color.White,
                    contentDescription = null
                )
            }
            Text(
                message,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}
