// ID: 584698174

package game;

import biuoop.DrawSurface;

/**
 * Represents an object that can be drawn to a screen.
 * @author David Dinkevich
 */
public interface Sprite {

    /**
     * Draws the sprite on the screen.
     * @param d the DrawSurface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();

    /**
     * Add this Sprite to a Game.
     * @param g the Game to add the sprite to
     */
    void addToGame(Game g);

}
