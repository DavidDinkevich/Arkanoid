// ID: 584698174

package animations;

/**
 * Describes the behavior of an animated menu screen.
 * @param <T> the object to be returned when an option is selected
 */
public interface Menu<T> extends Animation {
    /**
     * Add a new option to the menu.
     * @param key the key belonging to this option
     * @param message the text describing this option
     * @param returnVal the value to be returned when this object is selected
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Get the return value of the most recently selected option. If no object was selected,
     * or if this method has already been called and no other option has been selected,
     * will return null.
     * @return the return value of the most recently selected option. If no object was selected,
     * or if this method has already been called and no other option has been selected,
     * will return null
     */
    T getStatus();
}
