package github.returdev.multipickers.types.number

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerItemColors
import github.returdev.multipickers.core.PickerState
import github.returdev.multipickers.types.text.TextPicker

/**
 * This composable function creates a NumberPicker.
 *
 * @param modifier The modifier to be applied to the TextPicker. Defaults to [Modifier].
 * @param items The list of integers to be displayed in the picker.
 * @param pickerItemHeight The height of each item in the picker.
 * @param isEnabled Whether the picker is enabled or not. Defaults to true.
 * @param colors The colors to be used for the picker items. Defaults to [PickerDefaults.pickerItemColors()].
 * @param textStyle The text style to be used for the picker items. Defaults to a bold headline medium style from the current MaterialTheme.
 * @param pickerState The state of the picker.
 */
@ExperimentalComposeUiApi
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    items: List<Int>,
    pickerItemHeight : Dp,
    isEnabled: Boolean = true,
    colors : PickerItemColors = PickerDefaults.pickerItemColors(),
    textStyle : TextStyle = PickerDefaults.pickerTextStyle,
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