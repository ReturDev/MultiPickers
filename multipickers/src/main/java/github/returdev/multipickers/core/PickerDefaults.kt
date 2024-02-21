package github.returdev.multipickers.core

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Default values for the Picker composable, including minimum width, and default icon size.
 */
object PickerDefaults {

    internal val minPickerWidth = 48.dp

    /**
     * Creates a [PickerItemColors] instance with the specified color values.
     *
     * @param containerColor The color for the item container.
     * @param disabledContainerColor The color for the item container when disabled.
     * @param contentColor The color for the item content.
     * @param selectedContentColor The color for the selected item content.
     * @param disabledContentColor The color for the item content when disabled.
     * @return A [PickerItemColors] instance.
     */
    @Composable
    fun pickerItemColors(
        containerColor: Color = Color.Transparent,
        disabledContainerColor: Color = Color.Transparent,
        contentColor: Color = LocalContentColor.current,
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
        selectedContentColor: Color = contentColor,
        disabledSelectedContentColor : Color = selectedContentColor.copy(alpha = 0.5f)
    ) = PickerItemColors(
        containerColor = containerColor,
        disabledContainerColor = disabledContainerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        selectedContentColor = selectedContentColor,
        disabledSelectedContentColor = disabledSelectedContentColor
    )

}

/**
 * An immutable class that represents the colors of a PickerItem.
 *
 * @property containerColor The color for the item container.
 * @property disabledContainerColor The color for the item container when disabled.
 * @property contentColor The color for the item content.
 * @property selectedContentColor The color for the selected item content.
 * @property disabledContentColor The color for the item content when disabled.
 */
@Immutable
class PickerItemColors internal constructor(
    private val containerColor: Color,
    private val disabledContainerColor: Color,
    private val contentColor: Color,
    private val disabledContentColor: Color,
    private val selectedContentColor: Color,
    private val disabledSelectedContentColor : Color
) {

    /**
     * Returns the container color based on whether the PickerItem is enabled or not.
     *
     * @param enabled A boolean indicating whether the PickerItem is enabled or not.
     * @return A State<Color> representing the container color.
     */
    @Composable
    internal fun containerColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) containerColor else disabledContainerColor
        )
    }

    /**
     * Returns the content color based on whether the PickerItem is enabled or selected.
     *
     * @param enabled A boolean indicating whether the PickerItem is enabled or not.
     * @param selected A boolean indicating whether the PickerItem is selected or not.
     * @return A Color representing the content color.
     */
    internal fun contentColor(enabled: Boolean, selected: Boolean): Color {
        return if (enabled) {
            if (selected) selectedContentColor else contentColor
        } else {
            if (selected) disabledSelectedContentColor else disabledContentColor
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PickerItemColors

        if (containerColor != other.containerColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (contentColor != other.contentColor) return false
        if (selectedContentColor != other.selectedContentColor) return false
        return disabledContentColor == other.disabledContentColor
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + selectedContentColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }

}