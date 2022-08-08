// ID: 584698174

package core;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import levels.GameLevel;
import communication.HitListener;
import communication.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a displayable, collidable block in the game.
 * @author David Dinkevich
 */
public class Block implements Collidable, Sprite, HitNotifier {
    /** The underlying, geometric shape of the Block. */
    private Rectangle shape;
    /** The fill color of the Block. */
    private Color fill;
    /** The stroke color of the Block. */
    private Color stroke;
    /** A list of HitListeners--are updated when collisions occur. */
    private List<HitListener> hitListeners;

    /**
     * Instantiates a new Block with the given shape and color.
     * @param shape the geometric shape of the Block
     * @param fill the fill color of the block
     * @param stroke the stroke color of the block
     */
    public Block(Rectangle shape, Color fill, Color stroke) {
        this.shape = shape;
        this.fill = fill;
        this.stroke = stroke;
        hitListeners = new ArrayList<>();
    }

    /**
     * Update the Block's listeners that it has been hit by a Ball.
     * @param hitter the Ball that hit this Block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them
        List<HitListener> listeners = new ArrayList<>(hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Draws this Block on the given DrawSurface.
     * @param d the DrawSurface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw the block
        d.setColor(fill);
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperRight().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
        // Draw the border of the block
        d.setColor(stroke);
        d.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperRight().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
    }

    /**
     * Updates the status of the Block. As Blocks are static, this method
     * does nothing.
     */
    @Override
    public void timePassed() {
        // Do nothing, a Block is static
    }

    /**
     * Adds this Block to the given Game as a Sprite and a Collidable.
     * @param g the Game to add this Brick to
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Removes this Block from the given Game.
     * @param g the Game to remove this Block from
     */
    public void removeFromGame(GameLevel g) {
        // Remove from sprite collection
        g.removeSprite(this);
        // Remove from collidables
        g.removeCollidable(this);
    }

    /**
     * Returns the collision rectangle of this Block.
     * @return the collision rectangle of this Block
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return shape;
    }

    /**
     * Handles cases where an object collides with this Block.
     * @param hitter the Ball colliding with this Block
     * @param collisionPoint the point of collision with the object
     * @param currentVelocity the velocity of the object that hit us
     * @return the new post-collision velocity of the object that collided
     * with this Block.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Notify everyone that the Block was hit
        notifyHit(hitter);
        // Find which side was hit
        for (Line line : shape.getBorderLines()) {
            if (line.isOnLine(collisionPoint)) {
                boolean lineIsVertical = line.start().getX() == line.end().getX();
                // If the line is vertical, our side was hit
                if (lineIsVertical) {
                    // Negate the x value of the velocity
                    return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
                } else { // The top or bottom was hit
                    // Negate the y value of the velocity
                    return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                }
            }
        }
        // The collision point does not intersect with this block, error
        throw new RuntimeException("Given collision point is not valid");
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Get the fill color of this block.
     * @return the fill color of this block
     */
    public Color getFill() {
        return fill;
    }

    /**
     * Get the stroke color of this block.
     * @return the stroke color of this block
     */
    public Color getStroke() {
        return stroke;
    }
}
