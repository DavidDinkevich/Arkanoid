// ID: 584698174

package game;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a collection of Sprites.
 * @author David Dinkevich
 */
public class SpriteCollection {

    /* The list of Sprites. */
    private List<Sprite> sprites;

    /**
     * Instantiate a new SpriteCollection object.
     */
    public SpriteCollection() {
        sprites = new ArrayList<>();
    }

    /**
     * Add the given Sprite to the Sprite collection.
     * @param s the Sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Removes a Sprite from the SpriteCollection.
     * @param s the Sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /**
     * Calls timePassed() on all of the Sprites.
     */
    public void notifyAllTimePassed() {
        // Copy Sprites array to avoid ConcurrentModificationException
        for (Sprite s : new ArrayList<>(sprites)) {
            s.timePassed();
        }
    }

    /**
     * Calls drawOn() on all of the Sprites.
     * @param d the DrawSurface to draw the Sprites on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }

}
