// ID: 584698174

package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Displays animations on GUIs and updates them at regular intervals.
 * @author David Dinkevich
 */
public class AnimationRunner {

    /** The GUI to draw the animation on. */
    private GUI gui;
    /** The frame rate at which the animation will be rendered. */
    private int framesPerSecond;
    /** Applies frame rate. */
    private Sleeper sleeper;

    /**
     * Instantiate a new AnimationRunner object with the given GUI
     * and frame rate.
     * @param gui the GUI to render the animation to
     * @param framesPerSecond the frame rate at which the animation will
     *                        be rendered
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        sleeper = new Sleeper();
    }

    /**
     * Displays the given animation on the AnimationRunner's window at
     * the AnimationRunner's frame rate. Continues until Animation.shouldStop()
     * returns true.
     * @param animation the animation to display
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        // Run until the animation tells us to stop
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            // Execute frame
            animation.doOneFrame(d);
            gui.show(d);
            // Timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
