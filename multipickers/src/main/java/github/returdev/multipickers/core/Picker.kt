package github.returdev.multipickers.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * A composable function that creates a Picker.
 *
 * @param modifier The modifier to be applied to the Picker.
 * @param color The color to be used for the background of the Picker.
 * @param isEnabled A boolean indicating whether the Picker is enabled or not.
 * @param pickerState The state of the Picker.
 * @param pickerItem A composable function that takes an index and a function that returns the type of the PickerItem, and returns Unit.
 *
 * Inside the function, a LaunchedEffect is used to observe the first visible item in the list and update the selected index accordingly.
 * A LazyColumn is used to display the PickerItems. The user's ability to scroll and the fling behavior are determined by the isEnabled parameter and the state of the Picker respectively.
 * The PickerItems are created by invoking the pickerItem function with the current index and a function that returns the type of the PickerItem.
 */
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@Composable
internal fun Picker(
    modifier: Modifier,
    color : Color,
    isEnabled: Boolean,
    pickerState: PickerState,
    pickerItem: @Composable (Int, () -> PickerItemType) -> Unit,
) {

    LaunchedEffect(key1 = pickerState) {
        snapshotFlow { pickerState.listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { pickerState.updateIndexes(it) }
    }

    LazyColumn(
        modifier = Modifier
            .defaultMinSize(minWidth = PickerDefaults.minPickerWidth)
            .background(color = color)
            .then(modifier),
        state = pickerState.listState,
        userScrollEnabled = isEnabled,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = pickerState.listState)
    ) {
        items(count = pickerState.pickerItemsCount) {
            pickerItem(
                it % pickerState.itemsCount
            ) {
                when (it) {
                    pickerState.selectedIndex -> PickerItemType.SELECTED
                    pickerState.firstVisibleIndex -> PickerItemType.FIRST
                    pickerState.lastVisibleIndex -> PickerItemType.LAST
                    else -> PickerItemType.NORMAL
                }
            }
        }
    }
}

/**
 * Enum class that represents the type of a PickerItem.
 *
 * @property SELECTED Represents a PickerItem that is currently selected.
 * @property FIRST Represents the first visible PickerItem in the list.
 * @property LAST Represents the last visible PickerItem in the list.
 * @property NORMAL Represents a PickerItem that is not selected, first or last.
 */
internal enum class PickerItemType{
    SELECTED,
    FIRST,
    LAST,
    NORMAL
}