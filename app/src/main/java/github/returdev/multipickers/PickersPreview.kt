package github.returdev.multipickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.returdev.multipickers.core.PickerDefaults
import github.returdev.multipickers.core.PickerLength
import github.returdev.multipickers.core.rememberPickerState
import github.returdev.multipickers.core.rememberSaveablePickerState
import github.returdev.multipickers.types.number.NumberPicker
import github.returdev.multipickers.types.text.TextPicker
import github.returdev.multipickers.types.time.TimePicker
import github.returdev.multipickers.types.time.rememberSaveableTimePickerState
import github.returdev.multipickers.types.time.rememberTimePickerState

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showBackground = true,
)
@Composable
fun TextPickerPreview() {
    val textList = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten")
    val shortTextPickerState = rememberSaveablePickerState(
        itemsCount = textList.size,
        initialItemSelectedIndex = 0,
        pickerLength = PickerLength.SHORT
    )
    val longTextPickerState = rememberPickerState(
        itemsCount = textList.size,
        initialItemSelectedIndex = 0,
        pickerLength = PickerLength.LONG
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextPicker(items = textList, pickerState = shortTextPickerState)
        TextPicker(items = textList, pickerState = longTextPickerState)
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showBackground = true
)
@Composable
fun NumberPickerPreview() {
    val numberList = (0..100).toList()
    val shortNumberPickerState = rememberSaveablePickerState(
        itemsCount = numberList.size,
        initialItemSelectedIndex = 0,
        pickerLength = PickerLength.SHORT
    )
    val longNumberPickerState = rememberPickerState(
        itemsCount = numberList.size,
        initialItemSelectedIndex = 0,
        pickerLength = PickerLength.LONG
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NumberPicker(items = numberList, pickerState = shortNumberPickerState)
        NumberPicker(items = numberList, pickerState = longNumberPickerState)
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showBackground = true
)
@Composable
fun TimePickerPreview() {

    val shortTimePickerState = rememberSaveableTimePickerState(
        initialHour = 12,
        initialMinute = 50,
        initialSecond = 0,
        pickerLength = PickerLength.SHORT
    )
    val longTimePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 50,
        initialSecond = 0,
        pickerLength = PickerLength.LONG
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TimePicker(pickerState = shortTimePickerState)
        Spacer(modifier = Modifier
            .width(4.dp)
            .height(200.dp)
            .background(Color.Red))
        TimePicker(pickerState = longTimePickerState)
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    showBackground = true
)
@Composable
fun PickerColorPossibilityPreview() {

    val timePickerState = rememberSaveableTimePickerState(
        initialHour = 14,
        initialMinute = 0,
        initialSecond = 0,
        pickerLength = PickerLength.LONG
    )

    val itemHeight = 40.dp

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.Red)

        )
        TimePicker(
            pickerState = timePickerState,
            pickerItemHeight = itemHeight,
            colors = PickerDefaults.pickerItemColors(
                selectedContentColor = Color.White,
            )

        )
    }

}
