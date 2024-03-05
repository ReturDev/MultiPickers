package github.returdev.multipickers.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged


/**
 * This is a Picker Composable function that displays a list of items and allows the user to select one.
 *
 * @param modifier Modifier to be applied to the Picker.
 * @param pickerState The state of the Picker.
 * @param pickerItemHeight The height of each item in the Picker.
 * @param color The color scheme for the Picker items.
 * @param isEnabled Boolean value indicating whether the Picker is enabled or not.
 * @param pickerItem A Composable function that describes how to display each item in the Picker.
 */
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@Composable
internal fun Picker(
    modifier: Modifier,
    pickerState: PickerState,
    pickerItemHeight: Dp,
    color : PickerItemColors,
    isEnabled: Boolean,
    pickerItem: @Composable (Int, () -> Float) -> Unit,
) {

    // Get the current density of the screen
    val density = LocalDensity.current
    // Convert the picker item height from Dp to pixels
    val pickerItemPxHeight = remember(pickerItemHeight) {
        with(density) { pickerItemHeight.toPx() }
    }

    // Get the content color and selected content color from the color scheme
    val contentColor by color.contentColor(enabled = isEnabled)
    val selectedContentColor by color.selectedContentColor(enabled = isEnabled)

    // Remember the first visible item's scroll offset
    val offset = remember {
        derivedStateOf { pickerState.listState.firstVisibleItemScrollOffset }
    }
    // Calculate the animation value per offset
    val animationValuePerOffset = remember(pickerItemPxHeight, pickerState) {
        (1f / pickerItemPxHeight) / (pickerState.pickerLength.value / 2)
    }

    // Launch an effect to update the first and last visible indexes in the picker state
    LaunchedEffect(key1 = pickerState) {
        combine(
            snapshotFlow { pickerState.listState.layoutInfo.visibleItemsInfo}.distinctUntilChanged(),
            snapshotFlow { pickerState.listState.firstVisibleItemScrollOffset }.distinctUntilChanged(),
            ::Pair
        ).collect {(visibleItems, offset) ->
            if (visibleItems.isEmpty()) return@collect
            val firstItemIndex = visibleItems.first().index
            val lastItemIndex = visibleItems.last().index
            if (firstItemIndex < pickerState.firstVisibleIndex && offset < (pickerItemPxHeight / 2)) {
                pickerState.updateIndexes(firstItemIndex)
            } else if (lastItemIndex > pickerState.lastVisibleIndex && offset > (pickerItemPxHeight / 2)) {
                pickerState.updateIndexes(firstItemIndex + 1)
            }
        }

    }

    // Create a LazyColumn to display the picker items
    LazyColumn(
        modifier = Modifier
            .height(pickerItemHeight * pickerState.pickerLength.value)
            .defaultMinSize(minWidth = PickerDefaults.minPickerWidth)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.5f to contentColor,
                    1f to Color.Transparent
                )
                val brush2 = Brush.verticalGradient(
                    0.3f to Color.Transparent,
                    0.4f to selectedContentColor,
                    0.6f to selectedContentColor,
                    0.7f to Color.Transparent
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = brush2,
                        topLeft = Offset(
                            x = 0f,
                            y = size.height / 2 - pickerItemPxHeight / 2
                        ),
                        size = Size(
                            width = size.width,
                            height = pickerItemPxHeight
                        ),
                        blendMode = BlendMode.SrcIn
                    )
                    drawRect(brush = brush, blendMode = BlendMode.DstIn)

                }
            }
            .then(modifier),
        state = pickerState.listState,
        userScrollEnabled = isEnabled,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = pickerState.listState)
    ) {
        // Display the picker items
        items(count = pickerState.pickerItemsCount, key = {it}) {
            pickerItem(
                it % pickerState.itemsCount
            ) {
                calculateAnimationFloatValue(
                    index = it,
                    pickerItemPxHeight = pickerItemPxHeight,
                    pickerState = pickerState,
                    offset = offset.value,
                    valuePerOffset = animationValuePerOffset
                )
            }
        }
    }
}

/**
 * This function calculates the animation float value for a given index, picker item height, picker state, offset, and value per offset.
 *
 * @param index The index of the current item.
 * @param pickerItemPxHeight The height of the picker item in pixels.
 * @param pickerState The state of the picker.
 * @param offset The offset of the first visible item in the list.
 * @param valuePerOffset The animation value per offset.
 * @return The calculated animation float value.
 */
private fun calculateAnimationFloatValue(
    index : Int,
    pickerItemPxHeight : Float,
    pickerState : PickerState,
    offset : Int,
    valuePerOffset : Float
): Float {

    // If the offset is less than or equal to half of the picker item height
    return if (offset <= pickerItemPxHeight / 2){
        when {
            index == pickerState.firstVisibleIndex -> 0f
            index < pickerState.selectedIndex -> 0.5f - (valuePerOffset * offset)
            index == pickerState.selectedIndex -> 1f - (valuePerOffset * offset)
            index == pickerState.lastVisibleIndex -> valuePerOffset * offset
            index > pickerState.lastVisibleIndex -> 0f
            else -> 0.5f + (valuePerOffset * offset)
        }
    } else {
        // If the offset is greater than half of the picker item height
        when {
            index < pickerState.firstVisibleIndex -> 0f
            index == pickerState.firstVisibleIndex -> valuePerOffset *  (pickerItemPxHeight - offset)
            index < pickerState.selectedIndex -> 0.5f + (valuePerOffset * (pickerItemPxHeight - offset))
            index == pickerState.selectedIndex -> 1f - (valuePerOffset * (pickerItemPxHeight - offset))
            index == pickerState.lastVisibleIndex -> 0f
            else -> 0.5f - (valuePerOffset * (pickerItemPxHeight - offset))
        }
    }
}