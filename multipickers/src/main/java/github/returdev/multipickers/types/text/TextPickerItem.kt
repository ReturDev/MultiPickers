package github.returdev.multipickers.types.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp


/**
 * This is a TextPickerItem Composable function that displays a text item in the Picker.
 *
 * @param modifier Modifier to be applied to the TextPickerItem.
 * @param itemHeight The height of the TextPickerItem.
 * @param text The text to be displayed in the TextPickerItem.
 * @param color The color of the text in the TextPickerItem.
 * @param textStyle The style of the text in the TextPickerItem.
 * @param getAnimationFloatValue A function that returns a float value for implementing scrolling animations in the Picker.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
internal fun TextPickerItem(
    modifier: Modifier = Modifier,
    itemHeight : Dp,
    text: String,
    color : State<Color>,
    textStyle: TextStyle,
    getAnimationFloatValue : () -> Float,
) {

    // Remember a TextMeasurer to measure the text
    val measurer = rememberTextMeasurer()

    // Remember a TextStyle for the normal (non-selected) text
    val normalTextStyle = remember(textStyle) {
        textStyle.copy(
            fontSize = textStyle.fontSize.times(0.8f),
            fontWeight = FontWeight.Normal
        )
    }

    // Create a Box to contain the text
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .drawWithCache {

                // Calculate the final TextStyle based on the offset of the first item
                val finalTextStyle = lerp(
                    normalTextStyle,
                    textStyle,
                    getAnimationFloatValue()
                )

                // Measure the text with the final TextStyle
                val textLayoutResult = measurer.measure(
                    text,
                    finalTextStyle,
                )

                // Draw the text behind the Box
                onDrawBehind {

                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            x = size.width / 2 - textLayoutResult.size.width / 2,
                            y = size.height / 2 - textLayoutResult.size.height / 2
                        ),
                        color = color.value
                    )
                }
            }
            .then(modifier),
    )
}