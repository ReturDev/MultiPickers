package github.returdev.multipickers.types.time

/**
 * This object provides utility functions and values for the TimePicker.
 */
internal object TimePickerUtil {

    /**
     * The format used to convert the hours, seconds, and minutes to strings.
     * It ensures that the values are always represented with two digits.
     */
    private const val TIME_STRING_FORMAT = "%02d"

    /**
     * The range of possible hours for the TimePicker.
     */
    val hoursRange = (0..23).toList()

    /**
     * The range of possible seconds and minutes for the TimePicker.
     */
    val secondsAndMinutesRange = (0..59).toList()

    /**
     * The string representations of the hours range.
     */
    val hourStringRange = hoursRange.map { TIME_STRING_FORMAT.format(it) }

    /**
     * The string representations of the seconds and minutes range.
     */
    val secondsAndMinutesStringRange = secondsAndMinutesRange.map { TIME_STRING_FORMAT.format(it) }

}