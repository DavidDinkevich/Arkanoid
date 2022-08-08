// ID: 584698174

package animations;

import biuoop.DrawSurface;

/**
 * Represents an animation that can be rendered on a DrawSurface.
 * @author David Dinkevich
 */
public interface Animation {

    /**
     * Render the animation on the given DrawSurface.
     * @param d the DrawSurface to render on
     */
    void doOneFrame(DrawSurface d);

    /**
     * Determines whether the animation should be rendered.
     * @return whether or not the animation should be rendered
     */
    boolean shouldStop();

}
