package github.returdev.multipickers.types.time

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerItemColors
import github.returdev.multipickers.types.text.TextPicker


/**
 * This composable function creates a TimePicker.
 *
 * @param modifier The modifier to be applied to the TimePicker. Defaults to [Modifier].
 * @param pickerItemHeight The height of each item in the picker.
 * @param pickerState The state of the picker.
 * @param colors The colors to be used for the picker items. Defaults to [PickerDefaults.pickerItemColors()].
 * @param textStyle The text style to be used for the picker items. Defaults to a bold headline medium style from the current MaterialTheme.
 * @param isEnabled Whether the picker is enabled or not. Defaults to true.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimePicker(
    modifier : Modifier = Modifier,
    pickerItemHeight : Dp,
    pickerState : TimePickerState,
    colors : PickerItemColors = PickerDefaults.pickerItemColors(),
    textStyle : TextStyle = PickerDefaults.pickerTextStyle,
    isEnabled : Boolean = true
) {

    /**
     * The height of the picker.
     * It is calculated by coercing the pickerItemHeight to be at least the minimum picker height.
     */
    val pickerHeight = remember(pickerItemHeight) {
        pickerItemHeight.coerceAtLeast(PickerDefaults.minPickerHeight)
    }

    /**
     * The layout of the TimePicker.
     * It is a row with a width of four times the minimum picker width.
     * The items in the row are spaced by 8.dp and aligned vertically in the center.
     */
    Row(
        modifier = modifier.width(PickerDefaults.minPickerWidth * 4),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /**
         * The hour picker.
         * It is a TextPicker with the hours range as items.
         */
        TextPicker(
            modifier = Modifier.weight(1f),
            items = TimePickerUtil.hourStringRange,
            pickerItemHeight = pickerHeight,
            pickerState = pickerState.hourPickerState,
            colors = colors,
            textStyle = textStyle,
            isEnabled = isEnabled
        )
        /**
         * The separator between the hour picker and the minute picker.
         */
        Text(
            text = ":",
            style = textStyle
        )
        /**
         * The minute picker.
         * It is a TextPicker with the seconds and minutes range as items.
         */
        TextPicker(
            modifier = Modifier.weight(1f),
            items = TimePickerUtil.secondsAndMinutesStringRange,
            pickerItemHeight = pickerHeight,
            pickerState = pickerState.minutePickerState,
            colors = colors,
            textStyle = textStyle,
            isEnabled = isEnabled
        )
        /**
         * The separator between the minute picker and the second picker.
         */
        Text(
            text = ":",
            style = textStyle
        )
        /**
         * The second picker.
         * It is a TextPicker with the seconds and minutes range as items.
         */
        TextPicker(
            modifier = Modifier.weight(1f),
            items = TimePickerUtil.secondsAndMinutesStringRange,
            pickerItemHeight = pickerHeight,
            pickerState = pickerState.secondPickerState,
            colors = colors,
            textStyle = textStyle,
            isEnabled = isEnabled
        )

    }

}