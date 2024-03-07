package github.returdev.multipickers.types.text

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import github.returdev.multipickers.core.Picker
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerItemColors
import github.returdev.multipickers.core.PickerState


/**
 * This is a TextPicker Composable function that displays a list of text items in a Picker.
 *
 * @param modifier Modifier to be applied to the TextPicker.
 * @param items The list of text items to be displayed in the TextPicker.
 * @param pickerItemHeight The height of each item in the Picker. Defaults to 0.dp.
 * @param pickerState The state of the Picker, which includes the current selected index and the total number of items.
 * @param colors The colors to be used for the Picker items.
 * @param textStyle The style of the text in the Picker items.
 * @param isEnabled A boolean value indicating whether the Picker is enabled or not.
 */
@OptIn(ExperimentalTextApi::class)
@ExperimentalComposeUiApi
@Composable
fun TextPicker(
    modifier : Modifier = Modifier,
    items : List<String>,
    pickerItemHeight : Dp = 0.dp,
    pickerState: PickerState,
    colors : PickerItemColors = PickerDefaults.pickerItemColors(),
    textStyle : TextStyle = PickerDefaults.pickerTextStyle,
    isEnabled : Boolean = true
) {

    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    val itemSize = remember(items) {
        val size = textMeasurer.measure(
            items.maxBy { it.length },
            textStyle
        ).size

        with(density) { size.toSize().toDpSize() }

    }

    val pickerWidth = remember {
        itemSize.width.plus(8.dp)
    }
    val itemHeight = remember(itemSize, pickerItemHeight) {
        pickerItemHeight.coerceAtLeast(itemSize.height)
    }

    Picker(
        modifier = modifier.width(pickerWidth),
        pickerItemHeight = itemHeight,
        color = colors,
        isEnabled = isEnabled,
        pickerState = pickerState
    ) { itemIndex, getFirstItemOffset ->

        // Invokes the TextPickerItem composable to represent each item in the TextPicker.
        TextPickerItem(
            itemHeight = itemHeight,
            text = items[itemIndex],
            color = colors.contentColor(enabled = isEnabled),
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            getAnimationFloatValue = getFirstItemOffset
        )
    }
}