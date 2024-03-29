// ID: 584698174

package core;

import biuoop.DrawSurface;
import levels.GameLevel;

import java.awt.Color;

/**
 * A Sprite that displays the current score of the player.
 * @author David Dinkevich
 */
public class ScoreIndicator implements Sprite {
    /** Counter to keep track of the score. */
    private Counter score;
    /** The X coordinate of the indicator. */
    private int x;
    /** The Y coordinate of the indicator. */
    private int y;
    /** The size of the font of the indicator. */
    private int fontSize;

    /**
     * Instantiates a new ScoreIndicator object.
     * @param x the x coordinate of the score indicator
     * @param y the y coordinate of the score indicator
     * @param fontSize the font size of the score indicator
     * @param score a Counter that contains the score
     */
    public ScoreIndicator(int x, int y, int fontSize, Counter score) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.drawText(x, y, "Score: " + score.getValue(), fontSize);
    }

    @Override
    public void timePassed() {
        // Do nothing
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
