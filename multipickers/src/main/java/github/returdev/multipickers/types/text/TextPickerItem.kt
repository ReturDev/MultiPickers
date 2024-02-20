package github.returdev.multipickers.types.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import github.returdev.multipickers.core.PickerItemColors
import github.returdev.multipickers.core.PickerItemType

/**
 * A private composable function that creates a TextPickerItem.
 *
 * @param modifier The modifier to be applied to the TextPickerItem.
 * @param text The text to be displayed in the TextPickerItem.
 * @param pickerSelectedItemCurvaturePadding The padding for the selected item in the TextPickerItem.
 * @param colors The colors to be used for the TextPickerItem.
 * @param textStyle The style to be applied to the text in the TextPickerItem.
 * @param getPickerItemType A function that returns the type of the PickerItem.
 * @param isEnabled A boolean indicating whether the TextPickerItem is enabled or not.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
internal fun TextPickerItem(
    modifier: Modifier,
    text: String,
    pickerSelectedItemCurvaturePadding : Dp,
    colors: PickerItemColors,
    textStyle: TextStyle,
    getPickerItemType : () -> PickerItemType,
    isEnabled: Boolean = true,
) {

    // A TextMeasurer is remembered to measure the text.
    val measurer = rememberTextMeasurer()

    // A Box layout is used to contain the TextPickerItem.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent {
                drawContent()
                getBrush(getPickerItemType())?.let {
                    drawRect(brush = it, blendMode = BlendMode.DstIn)
                }
            }
            .drawWithCache {
                // The type of the PickerItem is retrieved.
                val itemType = getPickerItemType()

                // The text is measured with the textStyle modified based on whether the PickerItem is selected or not.
                val textLayoutResult = measurer.measure(
                    text,
                    textStyle.copy(
                        fontWeight = if (itemType == PickerItemType.SELECTED) FontWeight.Bold else FontWeight.Normal
                    ),
                )
                // The padding for the TextPickerItem is retrieved.
                val textPadding =
                    getTextPickerItemPadding(pickerSelectedItemCurvaturePadding, getPickerItemType)

                // The text is drawn behind the Box with the color determined by whether the PickerItem is enabled and selected.
                onDrawBehind {

                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            x = (size.width - textPadding.toPx()) - textLayoutResult.size.width,
                            y = size.height / 2 - textLayoutResult.size.height / 2
                        ),
                        color = colors.contentColor(
                            enabled = isEnabled,
                            selected = itemType == PickerItemType.SELECTED
                        ),
                    )

                }
            }
            .then(modifier),
    )
}

/**
 * A function that returns the padding for a TextPickerItem based on its type.
 *
 * @param pickerSelectedItemCurvaturePadding The padding for the selected item in the TextPickerItem.
 * @param getPickerItemType A function that returns the type of the PickerItem.
 * @return The padding for the TextPickerItem.
 */
private fun getTextPickerItemPadding(
    pickerSelectedItemCurvaturePadding: Dp,
    getPickerItemType: () -> PickerItemType
): Dp = when (getPickerItemType()) {
    PickerItemType.SELECTED -> pickerSelectedItemCurvaturePadding
    PickerItemType.FIRST -> 0.dp
    PickerItemType.LAST -> 0.dp
    PickerItemType.NORMAL -> pickerSelectedItemCurvaturePadding / 2
}

/**
 * A function that returns a Brush based on the type of the PickerItem.
 *
 * @param pickerItemType The type of the PickerItem.
 * @return A Brush for the PickerItem. Returns null if the PickerItem type is SELECTED or NORMAL.
 * For the FIRST type, it returns a vertical gradient from transparent at the top (0f) to black at 70% (0.7f) of the height.
 * For the LAST type, it returns a vertical gradient from black at 30% (0.3f) of the height to transparent at the bottom (1f).
 */
private fun getBrush(
    pickerItemType: PickerItemType
): Brush? = when (pickerItemType) {
    PickerItemType.SELECTED -> null
    PickerItemType.FIRST -> Brush.verticalGradient(
        0f to Color.Transparent,
        0.7f to Color.Black,
    )

    PickerItemType.LAST -> Brush.verticalGradient(
        0.3f to Color.Black,
        1f to Color.Transparent,
    )

    PickerItemType.NORMAL -> null
}