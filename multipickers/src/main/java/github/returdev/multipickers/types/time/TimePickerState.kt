package github.returdev.multipickers.types.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import github.returdev.multipickers.core.PickerLength
import github.returdev.multipickers.core.PickerState

/**
 * This class represents the state of a TimePicker.
 * It contains three PickerState instances for hours, minutes, and seconds.
 * It also provides a method to get the total time in seconds.
 */
class TimePickerState(
    initialHour : Int = 0,
    initialMinute : Int = 0,
    initialSecond : Int = 0,
    val pickerLength : PickerLength = PickerLength.SHORT
){

    /**
     * The state of the hour picker.
     */
    internal val hourPickerState = PickerState(
        itemsCount = TimePickerUtil.hoursRange.size,
        initialItemSelectedIndex = TimePickerUtil.hoursRange.indexOf(initialHour).coerceAtLeast(0),
        pickerLength = pickerLength
    )

    /**
     * The state of the minute picker.
     */
    internal val minutePickerState = PickerState(
        itemsCount = TimePickerUtil.secondsAndMinutesRange.size,
        initialItemSelectedIndex = TimePickerUtil.secondsAndMinutesRange.indexOf(initialMinute).coerceAtLeast(0),
        pickerLength = pickerLength
    )

    /**
     * The state of the second picker.
     */
    internal val secondPickerState = PickerState(
        itemsCount = TimePickerUtil.secondsAndMinutesRange.size,
        initialItemSelectedIndex = TimePickerUtil.secondsAndMinutesRange.indexOf(initialSecond).coerceAtLeast(0),
        pickerLength = pickerLength
    )

    /**
     * The currently selected hour.
     */
    val hour: Int
        get() = TimePickerUtil.hoursRange[hourPickerState.itemSelectedIndex]

    /**
     * The currently selected minute.
     */
    val minute: Int
        get() = TimePickerUtil.secondsAndMinutesRange[minutePickerState.itemSelectedIndex]

    /**
     * The currently selected second.
     */
    val second: Int
        get() = TimePickerUtil.secondsAndMinutesRange[secondPickerState.itemSelectedIndex]

    /**
     * Returns the total time in seconds.
     */
    fun getTimeInSeconds() : Int{
        return (hour * 3600) + (minute * 60) + second
    }
}

/**
 * This object is responsible for saving and restoring the state of a TimePickerState instance.
 */
private object TimePickerStateSaver {

    /**
     * The saver is a mapSaver that saves and restores the state of a TimePickerState instance.
     * It saves the state by mapping the properties of a TimePickerState instance to a Map.
     * It restores the state by creating a new TimePickerState instance from a Map.
     */
    val saver = mapSaver(
        save = {
            mapOf(
                "hour" to it.hour,
                "minute" to it.minute,
                "second" to it.second,
                "pickerLength" to it.pickerLength
            )
        },
        restore = {
            TimePickerState(
                initialHour = it["hour"] as Int,
                initialMinute = it["minute"] as Int,
                initialSecond = it["second"] as Int,
                pickerLength = it["pickerLength"] as PickerLength
            )
        }
    )

}

/**
 * This composable function creates and remembers a savable TimePickerState instance.
 * It uses the rememberSaveable function to remember the state of a TimePickerState instance across configuration changes.
 * The state is saved and restored using the saver from the TimePickerStateSaver object.
 *
 * @param initialHour The initial hour to be selected. Defaults to 0.
 * @param initialMinute The initial minute to be selected. Defaults to 0.
 * @param initialSecond The initial second to be selected. Defaults to 0.
 * @param pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 * @return A remembered and savable TimePickerState instance.
 */
@Composable
fun rememberSaveableTimePickerState(
    initialHour : Int = 0,
    initialMinute : Int = 0,
    initialSecond : Int = 0,
    pickerLength : PickerLength = PickerLength.SHORT
) = rememberSaveable(
    saver = TimePickerStateSaver.saver
) {
    TimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        initialSecond = initialSecond,
        pickerLength = pickerLength
    )
}

/**
 * This composable function creates and remembers a TimePickerState instance.
 * It uses the remember function to remember the state of a TimePickerState instance.
 *
 * @param initialHour The initial hour to be selected. Defaults to 0.
 * @param initialMinute The initial minute to be selected. Defaults to 0.
 * @param initialSecond The initial second to be selected. Defaults to 0.
 * @param pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 * @return A remembered TimePickerState instance.
 */
@Composable
fun rememberTimePickerState(
    initialHour : Int = 0,
    initialMinute : Int = 0,
    initialSecond : Int = 0,
    pickerLength : PickerLength = PickerLength.SHORT
) = remember {
    TimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        initialSecond = initialSecond,
        pickerLength = pickerLength
    )
}