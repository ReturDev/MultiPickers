package github.returdev.multipickers.types.text

import androidx.compose.foundation.layout.height
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
 * A composable function that creates a TextPicker.
 *
 * @param modifier The modifier to be applied to the TextPicker.
 * @param items The list of items to be displayed in the TextPicker.
 * @param pickerItemHeight The height of each item in the TextPicker.
 * @param pickerSelectedItemCurvaturePadding The padding for the selected item in the TextPicker.
 * @param pickerState The state of the TextPicker.
 * @param isEnabled A boolean indicating whether the TextPicker is enabled or not.
 * @param colors The colors to be used for the TextPicker items.
 * @param textStyle The style to be applied to the text in the TextPicker.
 */
@ExperimentalComposeUiApi
@Composable
fun TextPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    pickerItemHeight : Dp,
    pickerSelectedItemCurvaturePadding : Dp,
    pickerState: PickerState,
    isEnabled: Boolean = true,
    colors: PickerItemColors = PickerDefaults.pickerItemColors(),
    textStyle: TextStyle = LocalTextStyle.current
) {
    // Invokes the generic Picker composable with TextPickerItem as the item representation.
    Picker(
        modifier = Modifier
            .height(pickerItemHeight * pickerState.pickerLength.value)
            .then(modifier),
        color = colors.containerColor(enabled = isEnabled).value,
        isEnabled = isEnabled,
        pickerState = pickerState
    ) { itemIndex, getPickerItemType ->

        // Invokes the TextPickerItem composable to represent each item in the TextPicker.
        TextPickerItem(
            modifier = Modifier.height(pickerItemHeight),
            text = items[itemIndex],
            pickerSelectedItemCurvaturePadding = pickerSelectedItemCurvaturePadding,
            colors = colors,
            textStyle = textStyle,
            getPickerItemType = getPickerItemType,
            isEnabled = isEnabled,
        )
    }
}