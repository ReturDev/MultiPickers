package github.returdev.multipickers.core

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


/**
 * A stable class that represents the state of a Picker.
 *
 * @property itemsCount The total number of items in the Picker.
 * @param initialItemSelectedIndex The initially selected item index. Defaults to 0.
 * @property pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 */
@Stable
class PickerState(
    internal val itemsCount: Int,
    initialItemSelectedIndex: Int = 0,
    val pickerLength: PickerLength = PickerLength.SHORT,
) {

    // The maximum number of items that can be displayed in the picker.
    internal val pickerItemsCount = Int.MAX_VALUE

    // The index of the currently selected item.
    val itemSelectedIndex
        get() = _selectedIndex % itemsCount


    // The index of the picker selected item.
    private var _selectedIndex by mutableStateOf(
        calculateSelectedItemListIndex(initialItemSelectedIndex)
    )
    internal val selectedIndex
        get() = _selectedIndex


    // The state of the lazy list that represents the Picker.
    val listState: LazyListState = LazyListState(
        firstVisibleItemIndex = selectedIndex - pickerLength.value / 2
    )

    // The index of the first visible item in the Picker.
    private var _firstVisibleIndex = listState.firstVisibleItemIndex
    internal val firstVisibleIndex : Int
        get() = _firstVisibleIndex

    // The index of the last visible item in the Picker.
    private var _lastVisibleIndex = firstVisibleIndex + pickerLength.value - 1

    internal val lastVisibleIndex : Int
        get() = _lastVisibleIndex


    internal fun updateIndexes(firstVisibleIndex : Int){
        _firstVisibleIndex = firstVisibleIndex
        _selectedIndex = firstVisibleIndex + pickerLength.value / 2
        _lastVisibleIndex = firstVisibleIndex + (pickerLength.value - 1)
    }

    /**
     * Calculates the index of the selected item in the list.
     *
     * @param itemSelectedIndex The index of the selected item.
     * @return The calculated index of the selected item in the list.
     */
    private fun calculateSelectedItemListIndex(itemSelectedIndex: Int) : Int {
        val itemsTimesRepeated = pickerItemsCount / itemsCount
        val startListIndex = (itemsTimesRepeated / 2) * itemsCount
        return startListIndex + itemSelectedIndex
    }



}

/**
 * This object is responsible for saving and restoring the state of a PickerState instance.
 * It uses a mapSaver to save the state of a PickerState instance into a Map and restore it from a Map.
 */
private object PickerStateSaver {

    /**
     * The saver is a mapSaver that saves and restores the state of a PickerState instance.
     * It saves the state by mapping the properties of a PickerState instance to a Map.
     * It restores the state by creating a new PickerState instance from a Map.
     */
    val saver = mapSaver(
        save = { pickerState : PickerState ->
            mapOf(
                "itemsCount" to pickerState.itemsCount,
                "initialSelectedIndex" to pickerState.itemSelectedIndex,
                "pickerLength" to pickerState.pickerLength
            )
        },
        restore = { map ->
            PickerState(
                itemsCount = map["itemsCount"] as Int,
                initialItemSelectedIndex = map["initialSelectedIndex"] as Int,
                pickerLength = map["pickerLength"] as PickerLength
            )
        }
    )
}

/**
 * This composable function creates and remembers a saveable PickerState instance.
 *
 * @param itemsCount The total number of items in the Picker.
 * @param initialItemSelectedIndex The initially selected item index. Defaults to 0.
 * @param pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 * @return A remembered and savable PickerState instance.
 */
@Composable
fun rememberSaveablePickerState(
    itemsCount: Int,
    initialItemSelectedIndex: Int = 0,
    pickerLength: PickerLength = PickerLength.SHORT
) = rememberSaveable(
    inputs = arrayOf(itemsCount),
    saver = PickerStateSaver.saver
) {
    PickerState(
        itemsCount = itemsCount,
        initialItemSelectedIndex = initialItemSelectedIndex,
        pickerLength = pickerLength
    )
}

/**
 * Creates and remembers a [PickerState] composable.
 *
 * @param itemsCount The total number of items in the Picker.
 * @param initialItemSelectedIndex The initially selected item index. Defaults to 0.
 * @param pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 * @return A remembered [PickerState] composable.
 */
@Composable
fun rememberPickerState(
    itemsCount: Int,
    initialItemSelectedIndex: Int = 0,
    pickerLength: PickerLength = PickerLength.SHORT
) = remember(itemsCount) { PickerState(itemsCount, initialItemSelectedIndex, pickerLength) }

