package github.returdev.multipickers.types.number

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerItemColors
import github.returdev.multipickers.core.PickerState
import github.returdev.multipickers.types.text.TextPicker

/**
 * Composable function representing a NumberPicker, which allows selecting from a list of integer values.
 *
 * @param modifier The modifier to be applied to the NumberPicker.
 * @param items The list of integer values to be displayed in the NumberPicker.
 * @param pickerItemHeight The height of each picker item.
 * @param pickerSelectedItemCurvaturePadding The padding to be applied to the selected item.
 * @param pickerState The state of the NumberPicker.
 * @param isEnabled A boolean indicating whether the NumberPicker is enabled or disabled for user interaction. Defaults to `true`.
 * @param colors The [PickerItemColors] defining the colors for the NumberPicker in different states. Defaults to PickerDefaults.pickerItemColors().
 * @param textStyle The [TextStyle] defining the text style for the NumberPicker items. Defaults to the current local text style.
 */
@ExperimentalComposeUiApi
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    items: List<Int>,
    pickerItemHeight : Dp,
    isEnabled: Boolean = true,
    colors : PickerItemColors = PickerDefaults.pickerItemColors(),
    textStyle : TextStyle = LocalTextStyle.current,
    pickerState: PickerState
) {
    // Converts the list of integers to a list of strings for TextPicker.
    val stringItems = items.map { it.toString() }

    // Invokes the TextPicker composable with the converted string items and the calculated picker size.
    TextPicker(
        modifier = modifier,
        items = stringItems,
        pickerItemHeight = pickerItemHeight,
        pickerState = pickerState,
        isEnabled = isEnabled,
        colors = colors,
        textStyle = textStyle,
    )

}