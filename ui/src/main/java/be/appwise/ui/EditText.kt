package be.appwise.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 *
 * Text fields based on Outlined Text Field with more options and default styling
 * @param input the input text to be shown in the text field
 * @param onInputChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param label the optional label to be displayed above the text field container.
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the input text is empty.
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container, a composable is expected here.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container, a composable is expected here.
 * @param isError indicates if the text field's current value is in error. If set to true, the
 * label, border and trailing icon by default will be displayed in error color
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction]
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.outlinedTextFieldColors].
 * @param isPassword when `true`, this text field will make the input shown as *****.
 * @param isCreditCard when `true`, the input will automatically refactor the input to the credit card format `1111.2222.3333.4444`
 * @param isRequired when `true`, the input will show a * behind the label in the Red color.
 * @param icon the optional icon to be displayed in front of the message or error message. Default value is Icons.Outlined.ErrorOutLine.
 * @param message the optional error/info message. The message is shown underneath the text field,
 * when isError is true this message will be an error message and shown in ErrorColor from the appTheme.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditText(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit = {},
    label: String,
    placeholder: String,
    message: String = "",
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    isPassword: Boolean = false,
    isCreditCard: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        placeholderColor = MaterialTheme.colorScheme.onPrimary.copy(0.4f),
        containerColor = MaterialTheme.colorScheme.tertiary,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        errorIndicatorColor = MaterialTheme.colorScheme.error,
        disabledIndicatorColor = Color.Transparent,
        cursorColor = MaterialTheme.colorScheme.primary
    ),
) {

    Column(modifier = modifier) {
        Label(label = label, isRequired = isRequired)

        OutlinedTextField(value = input,
            enabled = enabled,
            placeholder = { Text(text = placeholder) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else if (isCreditCard) VisualTransformation { number ->
                creditCardTransformation(
                    number
                )
            } else VisualTransformation.None,
            onValueChange = onInputChange,
            colors = colors,
            isError = isError,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            shape = RoundedCornerShape(10.dp),
            singleLine = singleLine,
            modifier = Modifier.fillMaxWidth())
        Message(message = message, icon = icon, isError = isError)

    }
}

/**
 * Sliders allow users to make selections from a range of values.
 *
 * Sliders reflect a range of values along a bar, from which users may select a single value.
 * They are ideal for adjusting settings such as volume, brightness, or applying image filters.
 *
 * Slider using custom track and thumb:
 *
 * @param value current value of the slider. If outside of [valueRange] provided, value will be
 * coerced to this range.
 * @param label the optional label to be displayed above the text field container.
 * @param onValueChange callback in which value should be updated
 * @param modifier the [Modifier] to be applied to this slider
 * @param enabled controls the enabled state of this slider. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param valueRange range of values that this slider can take. The passed [value] will be coerced
 * to this range.
 * @param colors [SliderColors] that will be used to resolve the colors used for this slider in
 * different states. See [SliderDefaults.colors].
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this slider. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this slider in different states.
 * @param thumb the thumb to be displayed on the slider, it is placed on top of the track. It takes a [Brush] so its more customizable
 * @param icon the optional icon to be displayed in front of the message or error message. Default value is Icons.Outlined.ErrorOutLine.
 * @param message the optional error/info message. The message is shown underneath the text field,
 * when isError is true this message will be an error message and shown in ErrorColor from the appTheme.
 * @param isRequired when `true`, the input will show a * behind the label in the Red color.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextSlider(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    onValueChange: (Float) -> Unit = {},
    label: String,
    enabled: Boolean = true,
    message: String = "",
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    isRequired: Boolean = false,
    colors: SliderColors = SliderDefaults.colors(
        thumbColor = Color.Transparent,
        disabledThumbColor = Color.Transparent,
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent,
        activeTrackColor = MaterialTheme.colorScheme.primary
    ),
    thumb: Brush = Brush.radialGradient(
        0.6f to Color.White,
        0.6f to MaterialTheme.colorScheme.primary,
        1f to MaterialTheme.colorScheme.primary,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    Column {
        Label(label = label, isRequired = isRequired)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Slider(value = value,
                onValueChange = onValueChange,
                modifier = modifier.padding(horizontal = 12.dp),
                colors = colors,
                enabled = enabled,
                valueRange = valueRange,
                thumb = remember(interactionSource, colors, enabled) {
                    {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Canvas(
                                modifier = Modifier.size(20.dp),
                                onDraw = { drawCircle(thumb) })
                            Thumb(
                                interactionSource = interactionSource
                            )
                        }

                    }
                },
                track = remember(colors, enabled) {
                    { sliderPositions ->
                        SliderDefaults.Track(
                            colors = colors,
                            enabled = enabled,
                            sliderPositions = sliderPositions
                        )
                    }
                })
        }

        Message(message = message, icon = icon)

    }
}

/**
 *
 * Radio buttons allow users to select one option from a set.
 *
 * @param modifier the [Modifier] to be applied to this radio button
 * @param onOptionSelected callback in which value should be updated, the other values automatically gets updated to false
 * @param values expects a [List] of strings where the string is the label value.
 * @param enabled controls the enabled state of this radio button. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param colors [RadioButtonColors] that will be used to resolve the color used for this radio
 * button in different states. See [RadioButtonDefaults.colors].
 * [Interaction]s and customize the appearance / behavior of this radio button in different states.
 * @param icon the optional icon to be displayed in front of the message or error message. Default value is Icons.Outlined.ErrorOutLine.
 * @param message the optional error/info message. The message is shown underneath the text field,
 * when isError is true this message will be an error message and shown in ErrorColor from the appTheme.
 * @param isRequired when `true`, the input will show a * behind the label in the Red color.
 * @param label the optional label to be displayed above the text field container.
 *
 */

@Composable
fun EditTextRadioButton(
    modifier: Modifier = Modifier,
    onOptionSelected: (String) -> Unit = {},
    values: List<String>,
    label: String,
    message: String = "",
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    isRequired: Boolean = false,
    colors: RadioButtonColors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
    enabled: Boolean = true
) {

    val map = remember {
        mutableStateMapOf(*values.map { it to false }.toTypedArray())
    }

    Column {
        Label(label = label, isRequired = isRequired)

        Column(
            modifier = Modifier.selectableGroup()
        ) {
            map.forEach { radioButton ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        enabled = enabled,
                        selected = radioButton.value,
                        onClick = {
                            map.forEach { radioButton ->
                                map[radioButton.key] = false
                            }
                            map[radioButton.key] = !radioButton.value
                            onOptionSelected(radioButton.key)
                        },
                        colors = colors
                    )
                    Text(radioButton.key)
                }
            }

        }
    }

    Message(message = message, icon = icon)

}

/**
 *
 * Radio buttons allow users to select one option from a set.
 *
 * @param modifier the [Modifier] to be applied to this radio button
 * @param onCheckChanged callback in which value should be updated
 * @param values expects a [List] of strings where the string is the label value.
 * [Interaction]s and customize the appearance / behavior of this radio button in different states.
 * @param icon the optional icon to be displayed in front of the message or error message. Default value is Icons.Outlined.ErrorOutLine.
 * @param message the optional error/info message. The message is shown underneath the text field,
 * when isError is true this message will be an error message and shown in ErrorColor from the appTheme.
 * @param isRequired when `true`, the input will show a * behind the label in the Red color.
 * @param label the optional label to be displayed above the text field container.
 *
 */

@Composable
fun EditTextCheckbox(
    modifier: Modifier = Modifier,
    values: List<String>,
    onCheckChanged: (Set<String>) -> Unit = {},
    label: String,
    message: String = "",
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    isRequired: Boolean = false,
) {

    val map = remember {
        mutableStateMapOf(*values.map { it to true }.toTypedArray())
    }

    Column {
        Label(label = label, isRequired = isRequired)

        Column(
            modifier = Modifier.selectableGroup()
        ) {
            map.forEach { checkbox ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CheckBox(
                        enabled = checkbox.value,
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = Color.Black
                        ),
                        onCheckChanged = {
                            map[checkbox.key] = !checkbox.value
                            onCheckChanged(map.filter { it.value }.keys)
                        },

                        )
                    Text(checkbox.key)
                }
            }

        }
    }

    Message(message = message, icon = icon)

}


/**
 *
 * EditTextDate, when clicked a datePicker pops up, when a date is selected the callback is triggered
 * @param value the input text to be shown in the text field
 * @param onChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 * @param label the optional label to be displayed above the text field container.
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the input text is empty.
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container, a composable is expected here.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container, a composable is expected here.
 * @param isError indicates if the text field's current value is in error. If set to true, the
 * label, border and trailing icon by default will be displayed in error color
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.outlinedTextFieldColors].
 * @param isRequired when `true`, the input will show a * behind the label in the Red color.
 * @param icon the optional icon to be displayed in front of the message or error message. Default value is Icons.Outlined.ErrorOutLine.
 * @param message the optional error/info message. The message is shown underneath the text field,
 * when isError is true this message will be an error message and shown in ErrorColor from the appTheme.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextDate(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String,
    isError: Boolean = false,
    placeholder: String,
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    message: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isRequired: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        placeholderColor = MaterialTheme.colorScheme.onPrimary.copy(0.4f),
        containerColor = Color.Gray,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        errorIndicatorColor = MaterialTheme.colorScheme.error,
        cursorColor = MaterialTheme.colorScheme.primary
    ),
    onChange: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember {
        mutableStateOf("")
    }

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        },
        year, month, dayOfMonth,
    )

    datePicker.datePicker.minDate = calendar.timeInMillis

    LaunchedEffect(key1 = selectedDateText) {
        if (selectedDateText != "") {
            onChange(selectedDateText)
        }
    }

    EditText(
        modifier = modifier.clickable {
            datePicker.show()
        },
        input = value,
        enabled = false,
        isError = isError,
        onInputChange = onChange,
        label = label,
        colors = colors,
        placeholder = placeholder,
        message = message,
        icon = icon,
        isRequired = isRequired,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}


@Composable
fun Thumb(
    interactionSource: MutableInteractionSource
) {
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }
}

@Composable
fun Label(label: String, isRequired: Boolean = false) {
    Row() {
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 5.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        if (isRequired) {
            Text(
                text = " *", color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun Message(
    message: String = "", icon: ImageVector = Icons.Outlined.ErrorOutline, isError: Boolean = false
) {
    if (message.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .height(14.dp)
                    .padding(end = 5.dp)
            )
            Text(
                text = message,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,

                )
        }
    }
}

fun creditCardTransformation(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += "."
    }

    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset <= 3 -> offset
                offset <= 7 -> offset + 1
                offset <= 11 -> offset + 2
                offset <= 16 -> offset + 3
                else -> 19
            }

        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset <= 4 -> offset
                offset <= 9 -> offset - 1
                offset <= 14 -> offset - 2
                offset <= 19 -> offset - 3
                else -> 16
            }
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}


@Preview(showBackground = true)
@Composable
fun EditTextPreview() {
    var date by remember {
        mutableStateOf("")
    }
    var slider by remember {
        mutableStateOf(0f)
    }

    var password by remember {
        mutableStateOf("")
    }

    var visa by remember {
        mutableStateOf("")
    }


    val label: String = "Label here"
    val placeholder: String = "Type here ..."

    Column(modifier = Modifier.padding(5.dp)) {
        EditText(
            label = label,
            input = "",
            placeholder = placeholder,
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            })
        EditText(
            label = label, input = "", placeholder = placeholder, trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }, message = "Message"
        )
        EditText(
            label = label,
            input = "",
            isError = true,
            placeholder = placeholder,
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            message = "Message"
        )

        EditText(label = label,
            input = password,
            onInputChange = { password = it },
            placeholder = placeholder,
            isPassword = true,
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            })

        EditTextDate(
            label = label,
            value = date,
            placeholder = "../../....",
            onChange = { date = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        )

        EditTextSlider(
            label = label,
            isRequired = true,
            value = slider,
            onValueChange = { slider = it }
        )

        EditTextRadioButton(
            values = listOf<String>("Test", "Test2"),
            label = label,
            isRequired = true,
        )

        EditTextCheckbox(
            values = listOf<String>("Test", "Test2"),
            label = label
        )

//            EditText(
//                input = "",
//                label = label,
//                placeholder = "0000.0000.0000.0000",
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.visa_logo),
//                        contentDescription = null
//                    )
//                })
//            EditText(
//                input = visa,
//                onInputChange = { visa = it },
//                label = label,
//                placeholder = "0000.0000.0000.0000",
//                isCreditCard = true,
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.visa_logo),
//                        contentDescription = null
//                    )
//                })


    }
}