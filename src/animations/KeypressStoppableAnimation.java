// ID: 584698174

package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * An animation that wraps an internal animation (which it displays) and
 * is terminated by the keyboard.
 * @author David Dinkevich
 */
public class KeypressStoppableAnimation implements Animation {
    /** Keyboard sensor to detect key presses. */
    private KeyboardSensor keyboardSensor;
    /** The key that will terminate the animation. */
    private String key;
    /** The internal animation to display. */
    private Animation animation;
    /** Whether not to terminate the animation. */
    private boolean stop;
    /** Whether or not a key was pressed BEFORE this animation was created. */
    private boolean keyAlreadyPressed;

    /**
     * Instantiates a new KeypressStoppableAnimation with the given keyboard,
     * terminating key, and animation.
     * @param keyboardSensor the keyboard sensor
     * @param key the key that will terminate this animation
     * @param animation the animation to run
     */
    public KeypressStoppableAnimation(KeyboardSensor keyboardSensor, String key,
                                      Animation animation) {
        this.keyboardSensor = keyboardSensor;
        this.key = key;
        this.animation = animation;
        stop = false;
        keyAlreadyPressed = true;
    }


    @Override
    public void doOneFrame(DrawSurface d) {
        // If a key is pressed, only stop if the key was pressed after this
        // animation was created
        if (keyboardSensor.isPressed(key)) {
            stop = !keyAlreadyPressed;
        } else {
            // If the key was pressed before this animation was created, ignore it
            // and wait for the next time a key is pressed
            keyAlreadyPressed = false;
        }
        // Run the animation
        animation.doOneFrame(d);
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
