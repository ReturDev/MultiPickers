package github.returdev.multipickers.core

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


/**
 * A stable class that represents the state of a Picker.
 *
 * @property itemsCount The total number of items in the Picker.
 * @param startItemSelectedIndex The initially selected item index. Defaults to 0.
 * @property pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 */
@Stable
class PickerState(
    internal val itemsCount: Int,
    startItemSelectedIndex: Int = 0,
    val pickerLength: PickerLength = PickerLength.SHORT,
) {

    // The maximum number of items that can be displayed in the picker.
    internal val pickerItemsCount = Int.MAX_VALUE

    // The index of the currently selected item.
    val itemSelectedIndex
        get() = _selectedIndex % itemsCount


    // The index of the picker selected item.
    private var _selectedIndex by mutableStateOf(
        calculateSelectedItemListIndex(startItemSelectedIndex)
    )
//    private var _selectedIndex = calculateSelectedItemListIndex(startItemSelectedIndex)
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
 * Creates and remembers a [PickerState] composable.
 *
 * @param itemsCount The total number of items in the Picker.
 * @param itemSelectedIndex The initially selected item index. Defaults to 0.
 * @param pickerLength The length of the visible picker items, either [PickerLength.SHORT] or [PickerLength.LONG]. Defaults to [PickerLength.SHORT].
 * @return A remembered [PickerState] composable.
 */
@Composable
fun rememberPickerState(
    itemsCount: Int,
    itemSelectedIndex: Int = 0,
    pickerLength: PickerLength = PickerLength.SHORT
) = remember { PickerState(itemsCount, itemSelectedIndex, pickerLength) }

