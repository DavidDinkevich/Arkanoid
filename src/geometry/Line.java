// ID: 584698174

package geometry;

import java.util.List;

/**
 * Represents a geometric line segment and contains useful operations.
 * @author David Dinkevich
 */
public class Line {
    /** The first endpoint of the segment. */
    private Point start;
    /** The second endpoint of the segment. */
    private Point end;

    /**
     * Creates a new line segment with the given endpoints.
     * @param start the first endpoint
     * @param end the second endpoint
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Creates a new line segment with the given endpoints.
     * @param x1 the x coordinate of the first endpoint
     * @param y1 the y coordinate of the first endpoint
     * @param x2 the x coordinate of the second endpoint
     * @param y2 the y coordinate of the second endpoint
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Get the slope of this line segment.
     * @return the slope of this line segment
     */
    public double getSlope() {
        double m1x = end.getX() - start.getX();
        double m1y = end.getY() - start.getY();
        return m1y / m1x;
    }

    /**
     * Get the y-intercept of this segment <i>if it were extended into a line</i>.
     * @return the y-intercept of the line that this segment lies on
     */
    public double getYIntercept() {
        // b = y - mx
        return start.getY() - getSlope() * start.getX();
    }

    /**
     * Get whether the given point lies on this line segment.
     * @param point the point to check
     * @return true if the given point is on this line segment, false otherwise
     */
    public boolean isOnLine(Point point) {
        double epsilon = Math.pow(10, -10); // To prevent rounding errors
        return Math.abs(start.distance(point) + end.distance(point) - length()) <= epsilon;
    }

    /**
     * Get whether the given line shares exactly one point with this line,
     * and that that shared point is an endpoint of both lines.
     * @param other the other line to check with
     * @return the point of intersection if and only if it is an endpoint
     * of both lines, and is the only point of intersection between the two
     * lines. Null otherwise.
     */
    private Point lineTouchesAtOneEndpoint(Line other) {
        // Determine the longer and shorter segment (to check for overlapping)
        Line longer = length() > other.length() ? this : other;
        Line shorter = longer == other ? this : other;
        // Get whether each endpoint of the shorter segment lies on the longer segment
        boolean shorterStartOnLongerLine = longer.isOnLine(shorter.start());
        boolean shorterEndOnLongerLine = longer.isOnLine(shorter.end());

        // When start is the ONLY shared point.
        if ((longer.start().equals(shorter.start()) && !shorterEndOnLongerLine)
            || (longer.start().equals(shorter.end()) && !shorterStartOnLongerLine)) {
            return start;
        }
        // When end is the ONLY shared point.
        if ((longer.end().equals(shorter.start()) && !shorterEndOnLongerLine)
            || (longer.end().equals(shorter.end()) && !shorterStartOnLongerLine)) {
            return end;
        }
        // No common endpoints
        return null;
    }

    /**
     * Returns the point of intersection between this and the given segment
     * if one exists. null otherwise.
     * @param other the line to check for an intersection
     * @return the point of intersection (if exists), null otherwise
     */
    public Point intersectionWith(Line other) {
        // IF ONE OR BOTH OF THE LINES IS A POINT (length = 0)
        boolean firstLineIsPoint = length() == 0;
        boolean secondLineIsPoint = other.length() == 0;
        // If both lines are points, this will still work
        if (firstLineIsPoint || secondLineIsPoint) {
            Line linePoint = firstLineIsPoint ? this : other;
            Line second = firstLineIsPoint ? other : this;
            // Check if the line contains the point, if so, return the point
            return second.isOnLine(linePoint.start()) ? linePoint.start() : null;
        }

        // IF BOTH LINES ARE VERTICAL
        boolean line1Vertical = end.getX() == start.getX();
        boolean line2Vertical = other.end().getX() == other.start().getX();
        if (line1Vertical && line2Vertical) {
            return lineTouchesAtOneEndpoint(other);
        }

        // IF ONLY ONE OF THE LINES IS VERTICAL
        if (line1Vertical || line2Vertical) {
            Line vertical = line1Vertical ? this : other;
            Line notVertical = vertical == this ? other : this;
            // Point of intersection (poi)
            double poix = vertical.start().getX();
            double poiy = poix * notVertical.getSlope() + notVertical.getYIntercept();
            Point poi = new Point(poix, poiy);
            // Make sure point is on both lines
            return isOnLine(poi) && other.isOnLine(poi) ? poi : null;
        } else { // NEITHER ARE VERTICAL
            // If parallel
            if (getSlope() == other.getSlope()) {
                // Parallel but different y intercepts
                if (getYIntercept() != other.getYIntercept()) {
                    return null;
                }
                // Parallel on same line
                return lineTouchesAtOneEndpoint(other);
            } else { // NOT PARALLEL OR VERTICAL
                double m1 = getSlope();
                double m2 = other.getSlope();
                double b1 = getYIntercept();
                double b2 = other.getYIntercept();
                // Hypothetical point of intersection: m1x + b1 = m2x + b2 -> (b1-b2)/(m2-m1)
                double poix = (b1 - b2) / (m2 - m1); // Already checked: m1 != m2
                Point poi = new Point(poix, poix * m1 + b1);
                // Make sure point is on both lines
                return isOnLine(poi) && other.isOnLine(poi) ? poi : null;
            }
        }
    }

    /**
     * Get whether this line intersects with the given line.
     * @param other the line to check for an intersection
     * @return whether or not this line intersects with the given line
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * If this line intersects with the the given Rectangle, returns the
     * closest point of intersection with it. Otherwise, returns null.
     * @param rect the Rectangle to check for intersections
     * @return the closest point of intersection with the given Rectangle,
     * or null if none such point exists
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Get list of points at which this line intersects with the
        // borders of the rect (max 2 points)
        List<Point> intPoints = rect.intersectionPoints(this);
        if (intPoints.isEmpty()) {
            return null;
        }
        // Only one intersection point (corner)
        if (intPoints.size() == 1) {
            return intPoints.get(0);
        } else {
            // Return min
            double d1 = start.distance(intPoints.get(0));
            double d2 = start.distance(intPoints.get(1));
            return d1 < d2 ? intPoints.get(0) : intPoints.get(1);
        }
    }

    /**
     * Get whether this line is equal to the other line (endpoints are equal).
     * @param other the line to check for equality
     * @return whether or not this line is the same as the other line
     */
    public boolean equals(Line other) {
        return (start.equals(other.start) && end.equals(other.end))
                || (start.equals(other.end) && end.equals(other.start));
    }

    /**
     * Get a string representation of this Line.
     * @return a string representation of this Line
     */
    @Override
    public String toString() {
        return "[ " + start + ", " + end + " ]";
    }

    /**
     * Get the length of the segment (distance between endpoints).
     * @return the length of this segment
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Get the midpoint of this segment.
     * @return the midpoint of this segment
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2,
                (start.getY() + end.getY()) / 2);
    }

    /**
     * Get the first endpoint of this segment.
     * @return the first endpoint of this segment
     */
    public Point start() {
        return start;
    }

    /**
     * Get the second endpoint of this segment.
     * @return the second endpoint of this segment
     */
    public Point end() {
        return end;
    }
}
