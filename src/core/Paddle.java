// ID: 584698174

package core;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import levels.GameLevel;

import java.awt.Color;

/**
 * Represents a user-controlled Paddle that can be moved by the keyboard.
 * @author David Dinkevich
 */
public class Paddle implements Sprite, Collidable {

    /** The underlying, geometric shape of the Paddle. */
    private Rectangle shape;
    /** Allows reading of the keyboard. */
    private KeyboardSensor sensor;
    /** The speed at which the Paddle moves. */
    private double speed;
    /** The color of the Paddle. */
    private Color color;
    /** The minimum x value that the Paddle can reach. */
    private double minX;
    /** The maximum x value that the Paddle can reach. */
    private double maxX;

    /**
     * Instantiate a new Paddle.
     * @param sensor a KeyboardSensor that allows for reading keyboard input
     * @param shape the geometric shape of the Paddle
     * @param color the color of the Paddle
     * @param speed the speed of the Paddle
     * @param maxRange the maximum distance from the initial center of the paddle
     *                 that the paddle can move
     */
    public Paddle(KeyboardSensor sensor, Rectangle shape, Color color, double speed,
                  double maxRange) {
        this.shape = shape;
        this.sensor = sensor;
        this.speed = speed;
        this.color = color;
        // DETERMINE BOUNDARIES OF THE PADDLE
        // centerX = leftX + width/2
        double centerX = shape.getUpperLeft().getX() + shape.getWidth() / 2;
        // minX = center - maxRange. maxX = center + maxRange
        minX = centerX - maxRange;
        maxX = centerX + maxRange;

    }

    /**
     * Get the collision rectangle of this Paddle.
     * @return the collision rectangle of this Paddle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return shape;
    }

    /**
     * Handles cases where an object collides with this Paddle. The paddle is divided
     * into 5 sections labeled (-2, -1, 0, 1, 2). If the object hits section 0, it will
     * have its y velocity negated. If it hits +-1, +-2, then it will be deflected at
     * an angle of 30 * the section. For example, if it hits section -2, it will be
     * deflected at 30 * -2 = -60 degrees.
     * @param hitter the Ball hitting the Paddle
     * @param collisionPoint the point of collision with the object
     * @param currentVelocity the velocity of the object that collided with this Paddle.
     * @return the new post-collision velocity of the object that collided with this Paddle.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // X-coordinate of the collision relative to the paddle
        double relativeX = collisionPoint.getX() - shape.getUpperLeft().getX();
        // Ranges from 0 to 1
        double percentageOfPaddle = relativeX / shape.getWidth();
        // Divide the paddle into 5 chunks, from -2 to 2 (inclusive). 0 is the center.
        double numChunksFromCenter = Math.round((percentageOfPaddle - 0.5) * 4);

        // Center of paddle
        if (numChunksFromCenter == 0) {
            // Evenly deflect the object
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        } else {
            // Send the ball at an angle of +-30 or +-60 depending on how far away
            // from the center of the Paddle it is
            double dx = currentVelocity.getDx();
            double dy = currentVelocity.getDy();
            double velocMag = Math.sqrt(dx * dx + dy * dy);
            double basisAngle = 30;
            return Velocity.fromAngleAndSpeed(basisAngle * numChunksFromCenter, velocMag);
        }
    }

    /**
     * Updates the status of this Paddle. If the user pressed 'a', 'A', or the left
     * arrow, the paddle will move to the left. If the user pressed 'd', 'D' or the right arrow,
     * the Paddle will move to the right.
     */
    @Override
    public void timePassed() {
        // Will only move left if possible
        moveLeft();
        // Will only move right if possible
        moveRight();
    }

    /**
     * Draws this Paddle on the given DrawSurface.
     * @param d the DrawSurface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw the body of the Paddle
        d.setColor(color);
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperRight().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
    }

    /**
     * Adds this Paddle to the Game as a Sprite and a Collidable.
     * @param g the Game to add this Paddle to.
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Move the paddle to the left (by <code>speed</code> pixels). Stays within
     * the movement boundaries of the paddle.
     */
    private void moveLeft() {
        // If a, A, or the left arrow are pressed
        boolean leftKeyPressed = sensor.isPressed("a") || sensor.isPressed("A")
                || sensor.isPressed(KeyboardSensor.LEFT_KEY);
        // Update location (stay within boundaries!)
        if (leftKeyPressed) {
            shape.setUpperLeftX(Math.max(minX, shape.getUpperLeft().getX() - speed));
        }
    }

    /**
     * Move the paddle to the right (by <code>speed</code> pixels). Stays within
     * the movement boundaries of the paddle.
     */
    private void moveRight() {
        // If d, D, or the right arrow are pressed
        boolean rightKeyPressed = sensor.isPressed("d") || sensor.isPressed("D")
                || sensor.isPressed(KeyboardSensor.RIGHT_KEY);
        // Update location (stay within boundaries!)
        if (rightKeyPressed) {
            double newX = Math.min(maxX, shape.getUpperRight().getX() + speed);
            shape.setUpperLeftX(newX - shape.getWidth());
        }
    }
}
