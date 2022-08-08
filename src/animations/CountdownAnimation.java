// ID: 584698174

package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import core.SpriteCollection;

import java.awt.Color;

/**
 * An animation that represents a countdown timer.
 * @author David Dinkevich
 */
public class CountdownAnimation implements Animation {

    /** Total number of seconds to that the animation will last. */
    private double numOfSeconds;
    /** The number to count down from. */
    private int countFrom;
    /** The current number to count down from. */
    private int currTime;
    /** A list of sprites to render under the countdown timer. */
    private SpriteCollection gameScreen;

    /**
     * Instantiates a new CountdownAnimation that will last for the given number
     * of seconds, will start from the given number, and display the given
     * SpriteCollection.
     * @param numOfSeconds the amount of time that the countdown will last
     * @param countFrom the number that the timer will count down from
     * @param gameScreen the SpriteCollection that will be displayed underneath
     *                   the timer
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        currTime = countFrom;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw all of the sprites
        gameScreen.drawAllOn(d);
        // Draw the count
        d.setColor(Color.MAGENTA);
        d.drawText(d.getWidth() / 2 - 10, (int) (d.getHeight() * 0.7), "" + currTime, 40);
        // Only sleep after first iteration
        if (currTime != countFrom) {
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor((int) ((numOfSeconds / countFrom) * 1000));
        }
        // Decrease the counter
        --currTime;
    }

    @Override
    public boolean shouldStop() {
        return currTime < 0;
    }

}