package github.returdev.multipickers.core

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Default values for the Picker composable, including minimum width, and default icon size.
 */
object PickerDefaults {

    internal val minPickerWidth = 48.dp
    internal val minPickerHeight = 32.dp

    val pickerTextStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    /**
     * This function returns the default colors for the PickerItem.
     *
     * @param contentColor The color for the item content.
     * @param disabledContentColor The color for the item content when disabled.
     * @param selectedContentColor The color for the selected item content.
     * @param disabledSelectedContentColor The color for the selected item content when disabled.
     * @return A PickerItemColors object representing the default colors for the PickerItem.
     */
    @Composable
    fun pickerItemColors(
        contentColor: Color = LocalContentColor.current,
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
        selectedContentColor: Color = contentColor,
        disabledSelectedContentColor : Color = selectedContentColor.copy(alpha = 0.5f)
    ) = PickerItemColors(

        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        selectedContentColor = selectedContentColor,
        disabledSelectedContentColor = disabledSelectedContentColor
    )

}

/**
 * A data class that holds the colors for the PickerItem.
 *
 * @param contentColor The color for the item content.
 * @param disabledContentColor The color for the item content when disabled.
 * @param selectedContentColor The color for the selected item content.
 * @param disabledSelectedContentColor The color for the selected item content when disabled.
 */
@Immutable
class PickerItemColors internal constructor(
    private val contentColor: Color,
    private val disabledContentColor: Color,
    private val selectedContentColor: Color,
    private val disabledSelectedContentColor : Color
) {

    /**
     * This function returns the content color based on the enabled state.
     *
     * @param enabled A boolean indicating whether the PickerItem is enabled or not.
     * @return A State<Color> representing the content color.
     */
    @Composable
    internal fun contentColor(enabled : Boolean) : State<Color> {
        return rememberUpdatedState(
            if (enabled) contentColor else disabledContentColor
        )
    }

    /**
     * This function returns the selected content color based on the enabled state.
     *
     * @param enabled A boolean indicating whether the PickerItem is enabled or not.
     * @return A State<Color> representing the selected content color.
     */
    @Composable
    internal fun selectedContentColor(enabled : Boolean) : State<Color> {
        return rememberUpdatedState(
            if (enabled) selectedContentColor else disabledSelectedContentColor
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PickerItemColors

        if (contentColor != other.contentColor) return false
        if (selectedContentColor != other.selectedContentColor) return false
        return disabledContentColor == other.disabledContentColor
    }

    override fun hashCode(): Int {
        var result = contentColor.hashCode()
        result = 31 * result + selectedContentColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }

}