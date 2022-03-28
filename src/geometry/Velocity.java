// ID: 584698174

package geometry;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 * @author David Dinkevich
 */
public class Velocity {
    /** The x velocity. */
    private double dx;
    /** The y velocity. */
    private double dy;

    /**
     * Creates a new Velocity object with the given x and y velocities.
     * @param dx the x velocity
     * @param dy the y velocity
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a new Velocity object with initial x and y velocities of 0.
     */
    public Velocity() {
    }

    /**
     * Creates a new Velocity object whose x and y values are derived
     * by the given angle and speed.
     * @param angle the angle of the velocity vector (in degrees)
     * @param speed the magnitude of the velocity vector
     * @return a Velocity object encapsulating this data
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = -speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }

    /**
     * Adds this velocity to the given point. Takes point (x, y)
     * and returns (x + dx, y + dy).
     * @param p the point to which this velocity will be added
     * @return the new point
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Set the x velocity.
     * @param newDx the new x velocity
     */
    public void setDx(double newDx) {
        dx = newDx;
    }

    /**
     * Get the x velocity.
     * @return the x velocity
     */
    public double getDx() {
        return dx;
    }

    /**
     * Set the y velocity.
     * @param newDy the new y velocity
     */
    public void setDy(double newDy) {
        this.dy = newDy;
    }

    /**
     * Get the y velocity.
     * @return the y velocity
     */
    public double getDy() {
        return dy;
    }
}
