// ID: 584698174

package geometry;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a geometric Rectangle.
 * @author David Dinkevich
 */
public class Rectangle {

    /** The upper-left point of the rectangle. */
    private Point upperLeft;
    /** The width of the rectangle. */
    private double width;
    /** The height of the rectangle. */
    private double height;

    /**
     * Instantiate a new Rectangle object.
     * @param upperLeft the upper-left point of the Rectangle
     * @param width the width of the Rectangle
     * @param height the height of the Rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Instantiate a new Rectangle.
     * @param upperLeftX the upper-left x-coordinate of the Rectangle
     * @param upperLeftY the upper-left y-coordinate of the Rectangle
     * @param width the width of the Rectangle
     * @param height the height of the Rectangle
     */
    public Rectangle(double upperLeftX, double upperLeftY, double width, double height) {
        this(new Point(upperLeftX, upperLeftY), width, height);
    }

    /**
     * Returns a list of the points at which the given line intersects the borders
     * of this Rectangle.
     * @param line the line to check for intersections
     * @return the list of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();
        // For each side
        for (Line side : getBorderLines()) {
            Point poi = line.intersectionWith(side); // Check for point of intersection
            if (poi != null) {
                intersectionPoints.add(poi);
                // If the line intersected one of our corners, then it intersected with TWO
                // border lines. Thus, we have to break now to prevent adding a duplicate point.
                if (side.start().equals(poi) || side.end().equals(poi)) {
                    break;
                }
            }
        }
        return intersectionPoints;
    }

    /**
     * Get an array of lines representing the borders of this Rectangle.
     * @return an array of lines representing the borders of this Rectangle
     */
    public Line[] getBorderLines() {
        return new Line[] {
                // Left line
                new Line(getUpperLeft(), getBottomLeft()),
                // Right line
                new Line(getUpperRight(), getBottomRight()),
                // Top line
                new Line(upperLeft, getUpperRight()),
                // Bottom line
                new Line(getBottomLeft(), getBottomRight())
        };
    }

    /**
     * Set the upper-left x-coordinate of this Rectangle. This will
     * shift the Rectangle.
     * @param x the new x-coordinate
     */
    public void setUpperLeftX(double x) {
        upperLeft.setX(x);
    }

    /**
     * Set the upper-left y-coordinate of this Rectangle. This will
     * shift the Rectangle.
     * @param y the new y-coordinate
     */
    public void setUpperLeftY(double y) {
        upperLeft.setY(y);
    }

    /**
     * Get the upper-right corner of this Rectangle.
     * @return the upper-right corner of this Rectangle
     */
    public Point getUpperRight() {
        return new Point(upperLeft.getX() + width, upperLeft.getY());
    }

    /**
     * Get the bottom-right corner of this Rectangle.
     * @return the bottom-right corner of this Rectangle
     */
    public Point getBottomRight() {
        return new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Get the upper-left corner of this Rectangle.
     * @return the upper-left corner of this Rectangle
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Get the bottom-left corner of this Rectangle.
     * @return the bottom-left corner of this Rectangle
     */
    public Point getBottomLeft() {
        return new Point(upperLeft.getX(), getUpperLeft().getY() + height);
    }

    /**
     * Get the width of this Rectangle.
     * @return the width of this Rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height of this Rectangle.
     * @return the height of this Rectangle
     */
    public double getHeight() {
        return height;
    }
}
