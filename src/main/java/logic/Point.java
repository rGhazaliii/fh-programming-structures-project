package logic;

/**
 * Logic of the point which can be used to represent
 * players and cells positions.
 *
 * @author ite105705
 */
public class Point {

    private final int x;

    private final int y;

    private int layoutX;

    private int layoutY;

    /**
     * Constructor to create a new point by the input x and
     * y values.
     *
     * @param x x-axis value of the point
     * @param y y-axis value of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor to create a new point by the input x and
     * y values, and the layout x and layout y positions.
     *
     * @param x       x-axis value of the point
     * @param y       y-axis value of the point
     * @param layoutX layoutX value of the point
     * @param layoutY layoutY value of the point
     */
    public Point(int x, int y, int layoutX, int layoutY) {
        this.x = x;
        this.y = y;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }

    /**
     * Getter of the x value of the point.
     *
     * @return x-axis value of the point
     */
    public int x() {
        return x;
    }

    /**
     * Getter of the y value of the point.
     *
     * @return y-axis value of the point
     */
    public int y() {
        return y;
    }

    /**
     * Getter of the layoutX value of the point.
     *
     * @return layoutX value of the point
     */
    public int getLayoutX() {
        return layoutX;
    }

    /**
     * Getter of the layoutY value of the point.
     *
     * @return layoutY value of the point
     */
    public int getLayoutY() {
        return layoutY;
    }

    /**
     * Represents a point in string format.
     *
     * @return a point in (x,y) notation.
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }

    /**
     * Determines if two positions are equal. Two positions are equal if the
     * respective coordinates are equal.
     *
     * @param obj object to compare with (usually a position)
     * @return true, if both objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        return (obj != null) &&
               (this.getClass().equals(obj.getClass()) || (obj instanceof PrincipalPoint)) &&
               (this.x() == ((Point) obj).x() && this.y() == ((Point) obj).y());
    }

    public static double calculateDistance(Point p1, Point p2) {
        if (p1 == null || p2 == null) {
            return Double.MAX_VALUE;
        }

        double xDiff = p2.x() - p1.x();
        double yDiff = p2.y() - p1.y();

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
