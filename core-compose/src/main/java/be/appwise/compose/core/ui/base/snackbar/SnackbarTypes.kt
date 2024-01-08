package be.appwise.compose.core.ui.base.snackbar

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.Serializable


open class SnackbarMessage(
    open val message: String? = null,
    open val icon: ImageVector? = null,
    open val containerColor: Color? = Color.Transparent,
    open vararg val formatParams: Any?
) : Serializable {

    fun getMessage(context: Context) = when {
        message != null -> message
        else -> null
    }

    fun isNotEmpty() = message != null
}

data object Empty : SnackbarMessage()

class Success(
    override val message: String? = null,
    override val icon: ImageVector = Icons.Default.Check,
    override val containerColor: Color = Color.Green,
    override vararg val formatParams: Any?
) : SnackbarMessage(
    message = message,
    icon = icon,
    containerColor = containerColor,
    *formatParams
)

class Error(
    override val message: String? = null,
    override val icon: ImageVector = Icons.Default.Warning,
    override val containerColor: Color = Color.Red,
    override vararg val formatParams: Any?
) : SnackbarMessage(
    message = message,
    icon = icon,
    containerColor = containerColor,
    *formatParams
)
