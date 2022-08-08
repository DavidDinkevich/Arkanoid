// ID: 584698174

package animations;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * An animation that is displayed when the player loses.
 * @author David Dinkevich
 */
public class LoseScreen extends AbstractMessageScreen {

    /** The score to display. */
    private int score;

    /**
     * Instantiates a new LoseScreen with the given score to display.
     * @param score the score to display
     */
    public LoseScreen(int score) {
        // (backgroundColor, checkerboardColor)
        super(new Color(0.1f, 0.9f, 1), new Color(0.1f, 0.8f, 1));
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        super.doOneFrame(d);
        d.setColor(Color.BLUE);
        d.drawText(d.getWidth() / 2 - 100, d.getHeight() / 2, "Game Over :(", 32);
        d.drawText(d.getWidth() / 2 - 60, d.getHeight() / 2 + 40, "your score is: " + score, 15);
        d.drawText(d.getWidth() / 2 - 85, d.getHeight() / 2 + 80, "(press space to continue)", 15);

    }

}
