// ID: 584698174

package geometry;

/**
 * Represents a 2-dimensional point.
 * @author David Dinkevich
 */
public class Point {
    /** The x-coordinate of the point. */
    private double x;
    /** The y-coordinate of the point. */
    private double y;

    /**
     * Creates a new point with the given x and y coordinates.
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get whether this point equals the given point.
     * @param other the other point to check for equality
     * @return whether or not this point is equal to the given one
     */
    public boolean equals(Point other) {
        return x == other.x && y == other.y;
    }

    /**
     * Get a string representation of this Point.
     * @return a string representation of this Point
     */
    @Override
    public String toString() {
        return "( " + x + ", " + y + " )";
    }

    /**
     * Get the distance between this point and the given point.
     * @param other the other point to check for distance
     * @return the distance between this point and the given point
     */
    public double distance(Point other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Get the x-coordinate of this point.
     * @return the x-coordinate of this point
     */
    public double getX() {
        return x;
    }

    /**
     * Set the x-coordinate of this point.
     * @param newX the new x-coordinate
     */
    public void setX(double newX) {
        x = newX;
    }

    /**
     * Get the y-coordinate of this point.
     * @return the y-coordinate of this point
     */
    public double getY() {
        return y;
    }

    /**
     * Set the y-coordinate of this point.
     * @param newY the new y-coordinate
     */
    public void setY(double newY) {
        y = newY;
    }
}
