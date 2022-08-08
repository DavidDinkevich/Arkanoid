// ID: 584698174

package core;

import java.awt.Color;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
import levels.GameLevel;

/**
 * Represents a dynamic ball that has a position and velocity and can be
 * drawn on a screen.
 * @author David Dinkevich
 */
public class Ball implements Sprite {
    /** The game environment. */
    private GameEnvironment env;
    /** The center of the ball. */
    private Point center;
    /** The velocity of the ball. */
    private Velocity velocity;
    /** The radius of the ball. */
    private int radius;
    /** The color of the ball. */
    private Color color;

    /**
     * Creates a new ball with the given center, radius, and color.
     * @param env the game environment
     * @param center the center of the ball
     * @param radius the radius of the ball
     * @param color the color of the ball
     */
    public Ball(GameEnvironment env, Point center, int radius, Color color) {
        this.env = env;
        this.center = center;
        this.radius = radius;
        this.color = color;
        // Initial velocity is 0
        velocity = new Velocity();
    }

    /**
     * Creates a new ball with the given center, radius, and color.
     * @param env the game environment
     * @param x the x coordinate of the center of the ball
     * @param y the y coordinate of the center of the ball
     * @param radius the radius of the ball
     * @param color the color of the ball
     */
    public Ball(GameEnvironment env, int x, int y, int radius, Color color) {
        this(env, new Point(x, y), radius, color);
    }

    /**
     * Draws the ball on the given DrawSurface.
     * @param surface the surface to draw the ball on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle(getX(), getY(), getSize());
    }

    /**
     * Updates the location of the ball according to its velocity,
     * handles collisions with other objects.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Adds this Ball to the given Game as a Sprite.
     * @param g the Game to add this Ball to
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Removes this Ball from the given Game.
     * @param g the Game to remove this Ball from
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * Adds the velocity of the ball to the location of the ball. Handles
     * collisions using the GameEnvironment.
     */
    public void moveOneStep() {
        // Segment starting from center of ball to future ball location
        Line traj = new Line(center, velocity.applyToPoint(center));
        CollisionInfo info = env.getClosestCollision(traj);
        // No imminent collision
        if (info == null) {
            // Move ball along trajectory
            center = traj.end();
        } else {
            // Get the general direction we're moving in
            double dirX = velocity.getDx() < 0 ? -1 : velocity.getDx() == 0 ? 0 : 1;
            double dirY = velocity.getDy() < 0 ? -1 : velocity.getDy() == 0 ? 0 : 1;
            // Put the ball right before the collision point
            center.setX(info.collisionPoint().getX() - dirX);
            center.setY(info.collisionPoint().getY() - dirY);
            // Update velocity
            velocity = info.collisionObject().hit(this, info.collisionPoint(), velocity);
        }
    }

    /**
     * Sets the velocity of the ball.
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        setVelocity(v.getDx(), v.getDy());
    }

    /**
     * Sets the velocity of the ball.
     * @param dx the new x velocity of the ball
     * @param dy the new y velocity of the ball
     */
    public void setVelocity(double dx, double dy) {
        velocity.setDx(dx);
        velocity.setDy(dy);
    }

    /**
     * Get the velocity of the ball.
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return velocity;
    }

    /**
     * Get the color of the ball.
     * @return the color of the ball
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the radius of the ball.
     * @return the radius of the ball
     */
    public int getSize() {
        return radius;
    }

    /**
     * Get the x location of the ball.
     * @return the x location of the ball
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Get the y location of the ball.
     * @return the y location of the ball
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Get the GameEnvironment of this Ball.
     * @return the GameEnvironment of this Ball
     */
    public GameEnvironment getGameEnvironment() {
        return env;
    }
}
