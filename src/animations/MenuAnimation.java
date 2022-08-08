// ID: 584698174

package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import gameio.FileUtils;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An animated menu screen.
 * @param <T> the object to be returned when an option is selected
 */
public class MenuAnimation<T> implements Menu<T> {

    /** Stores the options of the menu. */
    private Map<String, MenuItem<T>> menuItems;
    /** Reference to gui keyboard. */
    private KeyboardSensor keyboard;
    /** The key of the most recently selected object. */
    private String selection;
    /** Background image of the menu. */
    private Image backgroundImage;

    /**
     * Instantiates a new MenuAnimation object with the given reference to a KeyboardSensor.
     * @param keyboard the reference to a KeyboardSensor
     */
    public MenuAnimation(KeyboardSensor keyboard) {
        this.menuItems = new LinkedHashMap<>();
        this.keyboard = keyboard;
        selection = null;
        backgroundImage = FileUtils.loadImage("resources/main_menu_background.png");
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        menuItems.put(key, new MenuItem<T>(message, returnVal));
    }

    @Override
    public T getStatus() {
        // Get the return value of the selected key
        T returnVal = menuItems.get(selection).returnVal;
        // Refresh the selection
        selection = null;
        return returnVal;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawImage(0, 0, backgroundImage);

        // For each key, check if it's selected
        for (Map.Entry<String, MenuItem<T>> entry : menuItems.entrySet()) {
            if (keyboard.isPressed(entry.getKey())) {
                selection = entry.getKey();
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return selection != null;
    }

    /**
     * Stores a menu option (its text and return value).
     * @param <T> the return value type of the option
     */
    private static class MenuItem<T> {
        private String name;
        private T returnVal;

        /**
         * Instantiates a new MenuItem object.
         * @param name the name of the option
         * @param returnVal the return value of the option
         */
        public MenuItem(String name, T returnVal) {
            this.name = name;
            this.returnVal = returnVal;
        }
    }
}
