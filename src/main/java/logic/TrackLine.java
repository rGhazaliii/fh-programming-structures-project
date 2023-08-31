package logic;

/**
 * Logic of the start/finish line of the game on the track.
 *
 * @author ite105705
 */
public class TrackLine {

    /**
     * A flag to show whether the line is draw on the track field or not.
     */
    private boolean isDrawn;

    /**
     * Represents the direction of the cell, which is necessary for
     * line cell and by default is right to left.
     */
    private LineDirection direction;

    /**
     * Represents the start point value of the line.
     */
    private Point startPoint;

    /**
     * Represents the end point value of the line.
     */
    private Point endPoint;

    /**
     * Creates a new line with the default direction of left
     * to right arrows.
     */
    public TrackLine() {
        this.direction = LineDirection.LEFT_RIGHT;
    }

    /**
     * Creates a new line with the default direction of left
     * to right arrows.
     *
     * @param direction the given direction
     */
    public TrackLine(LineDirection direction) {
        this.direction = direction;
    }

    /**
     * Creates a new line with the given direction and
     * starting and ending point.
     *
     * @param startPoint the given starting point
     * @param endPoint   the given ending point
     * @param direction  the given direction
     */
    public TrackLine(Point startPoint, Point endPoint, LineDirection direction) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    /**
     * Creates a new line with the given starting and
     * ending point.
     *
     * @param startPoint the given starting point
     * @param endPoint   the given ending point
     */
    TrackLine(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Returns whether the line is already drawn or not.
     *
     * @return whether the line is already drawn or not.
     */
    public boolean isDrawn() {
        return this.isDrawn;
    }

    /**
     * Setter method of the draw status of the line.
     *
     * @param isDrawn the boolean flag to show whether the line
     *                is drawn or not
     */
    public void setIsDrawn(boolean isDrawn) {
        this.isDrawn = isDrawn;
    }

    /**
     * Getter method for the direction of the line.
     *
     * @return the direction enum value of the line.
     */
    public LineDirection getDirection() {
        return this.direction;
    }

    /**
     * Getter method for the starting point value of the line.
     *
     * @return the start point
     */
    public Point getStartPoint() {
        return this.startPoint;
    }

    /**
     * Setter method for the start point value of the line.
     *
     * @param point the new start point of the line
     */
    public void setStartPoint(Point point) {
        this.startPoint = point;
    }

    /**
     * Getter method for the ending point value of the line.
     *
     * @return the end point
     */
    public Point getEndPoint() {
        return this.endPoint;
    }

    /**
     * Setter method for the start point value of the line.
     *
     * @param point the new start point of the line
     */
    public void setEndPoint(Point point) {
        this.endPoint = point;
    }

    /**
     * Calculates the start and the end point of the line based
     * on the given cells and the given clicked cell by its x
     * and y-axis values, at the end the two point instances
     * of the class which represent the start and end point of
     * the line are set. Default value for the direction of the
     * horizontal lines are TOP_DOWN which represents arrows pointing
     * down, and default value for the direction of the vertical
     * lines are LEFT_RIGHT which represents arrows pointing
     * right.
     *
     * @param cells the given cells of the track field.
     * @param point point of the cell to turn into track
     */
    public void calculate(Cell[][] cells, Point point) {
        boolean verticallyGravelMet = false;
        int x = point.x();
        int y = point.y();
        int verticalTrackCells = 0;
        int verticalStartIndex = y;
        int verticalEndIndex = y;
        for (int row = y + 1; row < cells[x].length && !verticallyGravelMet; row++) {
            if (cells[x][row].isGravel()) {
                verticallyGravelMet = true;
                verticalEndIndex = row - 1;
            } else if (cells[x][row].isTrack()) {
                verticalEndIndex = row;
                verticalTrackCells++;
            }
        }
        verticallyGravelMet = false;
        for (int row = y - 1; row >= 0 && !verticallyGravelMet; row--) {
            if (cells[x][row].isGravel()) {
                verticallyGravelMet = true;
                verticalStartIndex = row + 1;
            } else if (cells[x][row].isTrack()) {
                verticalStartIndex = row;
                verticalTrackCells++;
            }
        }

        boolean horizontallyGravelMet = false;
        int horizontalTrackCells = 0;
        int horizontalStartIndex = x;
        int horizontalEndIndex = x;
        for (int col = x + 1; col < cells.length && !horizontallyGravelMet; col++) {
            if (cells[col][y].isGravel()) {
                horizontallyGravelMet = true;
                horizontalEndIndex = col - 1;
            } else if (cells[col][y].isTrack()) {
                horizontalEndIndex = col;
                horizontalTrackCells++;
            }
        }
        horizontallyGravelMet = false;
        for (int col = x - 1; col >= 0 && !horizontallyGravelMet; col--) {
            if (cells[col][y].isGravel()) {
                horizontallyGravelMet = true;
                horizontalStartIndex = col + 1;
            } else if (cells[col][y].isTrack()) {
                horizontalStartIndex = col;
                horizontalTrackCells++;
            }
        }

        if (verticalTrackCells >= horizontalTrackCells) {
            this.startPoint = new Point(x, verticalStartIndex);
            this.endPoint = new Point(x, verticalEndIndex);
            this.direction = LineDirection.LEFT_RIGHT;
        } else {
            this.startPoint = new Point(horizontalStartIndex, y);
            this.endPoint = new Point(horizontalEndIndex, y);
            this.direction = LineDirection.TOP_DOWN;
        }
    }

    /**
     * Toggles the line direction to the opposite of the
     * current direction.
     */
    public void toggleDirection() {
        switch (this.direction) {
            case LEFT_RIGHT -> this.direction = LineDirection.RIGHT_LEFT;
            case RIGHT_LEFT -> this.direction = LineDirection.LEFT_RIGHT;
            case TOP_DOWN -> this.direction = LineDirection.DOWN_TOP;
            case DOWN_TOP -> this.direction = LineDirection.TOP_DOWN;
        }
    }

    /**
     * Changes the direction based on the current direction
     * of the line clockwise.
     */
    public void rotateDirection() {
        switch (this.direction) {
            case LEFT_RIGHT -> this.direction = LineDirection.TOP_DOWN;
            case TOP_DOWN -> this.direction = LineDirection.RIGHT_LEFT;
            case RIGHT_LEFT -> this.direction = LineDirection.DOWN_TOP;
            case DOWN_TOP -> this.direction = LineDirection.LEFT_RIGHT;
        }
    }

    /**
     * Checks whether a given point is on the line.
     *
     * @param point point to be checked
     * @return whether point is on the line or not
     */
    public boolean isOnLine(Point point) {
        return ((this.startPoint.x() <= point.x() && point.x() <= this.endPoint.x()) || (this.endPoint.x() <= point.x()
                                                                                         && point.x()
                                                                                            <= this.startPoint.x()))
               && ((this.startPoint.y() <= point.y() && point.y() <= this.endPoint.y()) || (
                this.endPoint.y() <= point.y() && point.y() <= this.startPoint.y())) && (
                       Math.abs(this.prepDotProduct(this.startPoint, this.endPoint, point)) < this.getEpsilon());
    }

    /**
     * Calculates the dot product of the given points.
     *
     * @param a the first point
     * @param b the second point
     * @param c the third point
     * @return the dot product of the given points
     */
    private float prepDotProduct(Point a, Point b, Point c) {
        return (a.x() - c.x()) * (b.y() - c.y()) - (a.y() - c.y()) * (b.x() - c.x());
    }

    private double getEpsilon() {
        int dx1 = this.endPoint.x() - this.startPoint.x();
        int dy1 = this.endPoint.y() - this.startPoint.y();

        return 0.003 * (dx1 * dx1 + dy1 * dy1);
    }

    /**
     * Counts the number of the points on the top of a given point.
     * The starting point is given.
     *
     * @param startPoint the given point
     * @param clickedY   the index to begin count from
     * @param maxY       maximum index that is allowed to reach
     * @return the number of the points on the top of a given point
     */
    public int countTopCells(Point startPoint, int clickedY, int maxY) {
        int topCells = 0;
        for (int i = clickedY, counter = 0; counter < maxY; i--, counter++) {
            if (i > startPoint.y()) {
                topCells++;
            }
        }

        return topCells;
    }

    /**
     * Counts the number of the points on the bottom of a given point.
     * The starting point is given.
     *
     * @param endPoint the given point
     * @param clickedY the index to begin count from
     * @param maxY     maximum index that is allowed to reach
     * @return the number of the points on the bottom of a given point
     */
    public int countBottomCells(Point endPoint, int clickedY, int maxY) {
        int bottomCells = 0;
        for (int j = clickedY, counter = 0; counter < maxY; j++, counter++) {
            if (j < endPoint.y()) {
                bottomCells++;
            }
        }

        return bottomCells;
    }

    /**
     * Counts the number of the points on the left of a given point.
     * The starting point is given.
     *
     * @param startPoint the given point
     * @param clickedX   the index to begin count from
     * @param maxX       maximum index that is allowed to reach
     * @return the number of the points on the left of a given point
     */
    public int countLeftCells(Point startPoint, int clickedX, int maxX) {
        int leftCells = 0;
        for (int i = clickedX, counter = 0; counter < maxX; i--, counter++) {
            if (i > startPoint.x()) {
                leftCells++;
            }
        }

        return leftCells;
    }

    /**
     * Counts the number of the points on the right of a given point.
     * The starting point is given.
     *
     * @param endPoint the given point
     * @param clickedX the index to begin count from
     * @param maxX     maximum index that is allowed to reach
     * @return the number of the points on the right of a given point
     */
    public int countRightCells(Point endPoint, int clickedX, int maxX) {
        int rightCells = 0;
        for (int j = clickedX, counter = 0; counter < maxX; j++, counter++) {
            if (j < endPoint.x()) {
                rightCells++;
            }
        }

        return rightCells;
    }

    /**
     * Gets the distance of the start/finish line to
     * the given point.
     *
     * @param point the given input point
     * @return the distance of the start/finish line to
     * the given point
     */
    public int getDistanceFromPoint(Point point) {
        switch (this.direction) {
            case LEFT_RIGHT, RIGHT_LEFT -> {
                return point.x() - this.startPoint.x();
            }
            case TOP_DOWN, DOWN_TOP -> {
                return point.y() - this.startPoint.y();
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Returns the string direction of the line as a
     * string value.
     *
     * @return the string direction of the line as a
     * string value
     */
    public String toString() {
        return switch (this.direction) {
            case DOWN_TOP -> "0";
            case LEFT_RIGHT -> "1";
            case TOP_DOWN -> "2";
            case RIGHT_LEFT -> "3";
        };
    }

    /**
     * Returns the {@link LineDirection} enum based on the given
     * string value as direction.
     *
     * @param direction the given direction as input
     * @return the {@link LineDirection} enum based on the given
     * string value as direction
     */
    public static LineDirection toLineDirection(String direction) {
        return switch (direction) {
            case "0" -> LineDirection.DOWN_TOP;
            case "1" -> LineDirection.LEFT_RIGHT;
            case "2" -> LineDirection.TOP_DOWN;
            case "3" -> LineDirection.RIGHT_LEFT;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}
