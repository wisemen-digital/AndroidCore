package be.appwise.ui

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckBox(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colorScheme.tertiaryContainer,
        uncheckedColor = MaterialTheme.colorScheme.primary
    ),
    onCheckChanged: (Boolean) -> Unit = {}
) {
    Checkbox(
        modifier = modifier,
        checked = enabled,
        onCheckedChange = onCheckChanged,
        colors = colors
    )
}


@Preview
@Composable
fun CheckboxPreview() {
    CheckBox(enabled = false)
    CheckBox(enabled = true)
}