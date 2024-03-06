package github.returdev.multipickers.types.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import github.returdev.multipickers.core.Picker
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerItemColors
import github.returdev.multipickers.core.PickerState


/**
 * This is a TextPicker Composable function that displays a list of text items in a Picker.
 *
 * @param modifier Modifier to be applied to the TextPicker.
 * @param items The list of text items to be displayed in the TextPicker.
 * @param pickerItemHeight The height of each item in the Picker.
 * @param pickerState The state of the Picker, which includes the current selected index and the total number of items.
 * @param colors The colors to be used for the Picker items.
 * @param textStyle The style of the text in the Picker items.
 * @param isEnabled A boolean value indicating whether the Picker is enabled or not.
 */
@ExperimentalComposeUiApi
@Composable
fun TextPicker(
    modifier : Modifier = Modifier,
    items : List<String>,
    pickerItemHeight : Dp,
    pickerState: PickerState,
    colors : PickerItemColors = PickerDefaults.pickerItemColors(),
    textStyle : TextStyle = LocalTextStyle.current,
    isEnabled : Boolean = true
) {

    Picker(
        modifier = modifier,
        pickerItemHeight = pickerItemHeight,
        color = colors,
        isEnabled = isEnabled,
        pickerState = pickerState
    ) { itemIndex, getFirstItemOffset ->

        // Invokes the TextPickerItem composable to represent each item in the TextPicker.
        TextPickerItem(
            itemHeight = pickerItemHeight,
            text = items[itemIndex],
            color = colors.contentColor(enabled = isEnabled),
            textStyle = textStyle,
            getAnimationFloatValue = getFirstItemOffset
        )
    }
}