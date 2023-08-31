package logic;

import logic.list.PathList;
import logic.pathfinding.Dijkstra;
import logic.pathfinding.PathFinder;

/**
 * Logic of the board of the game
 *
 * @author ite105705
 */
public class Board {

    /**
     * Cells of the board. It has to be a rectangle.
     */
    private Cell[][] cells;

    /**
     * Represents the starting/ending line of the game.
     */
    private TrackLine trackLine;

    /**
     * Number of the neighbouring cells of a cell.
     */
    private static final int NEIGHBOURS_NUMBER = 8;

    /**
     * PathFinder class which find the path on the board.
     */
    private final static PathFinder pathFinder = new Dijkstra();

    /**
     * Represents the track of the game as a path list.
     */
    private PathList track;

    /**
     * Constructor used for testing, gets given a template for a board (see also
     * Board.START_BOARD). It is public, so it can be used for the tests of the
     * path finding as well.
     *
     * @param initBoard template of the board
     */
    public Board(CellState[][] initBoard, LineDirection lineDirection) {
        this.trackLine = new TrackLine(lineDirection);
        this.cells = createBoard(initBoard);
    }

    /**
     * Creates a board based on the given column count and
     * row count.
     *
     * @param colCount the given column count
     * @param rowCount the given row count
     */
    public Board(int colCount, int rowCount) {
        this.cells = new Cell[colCount][rowCount];
        this.trackLine = new TrackLine();
        this.initCells();
    }

    /**
     * Constructor only for testing.
     *
     * @param cells cells of the game field
     */
    public Board(String cells, LineDirection direction) {
        this.trackLine = new TrackLine(direction);
        this.trackLine.setIsDrawn(true);

        //split in lines, determine size of cell-array
        String[] lines = cells.split("\\r?\\n");
        int rowCount = lines.length;
        int colCount = lines[0].split(" ").length;
        this.cells = new Cell[colCount][rowCount];

        //walk the lines
        for (int row = 0; row < rowCount; row++) {
            String[] singleCells = lines[row].split(" ");

            //each line must have the same count of columns
            if (colCount != singleCells.length) throw new AssertionError();

            //walk the columns of each line
            for (int col = 0; col < colCount; col++) {
                if (singleCells[col].startsWith("0")) {
                    this.cells[col][row] = new Cell(new Point(col, row), CellState.GRAVEL);
                } else if (singleCells[col].startsWith("1")) {
                    this.cells[col][row] = new Cell(new Point(col, row), CellState.TRACK);
                } else if (singleCells[col].startsWith("2")) {
                    this.cells[col][row] = new Cell(new Point(col, row), CellState.LINE);
                }
            }
        }
    }

    /**
     * Creates a board with given cell types.
     *
     * @param boardCells array of cell types for the new board; if the array is
     *                   empty or non-existent an IllegalArgumentException is
     *                   thrown
     * @return newly created board
     */
    private Cell[][] createBoard(CellState[][] boardCells) {
        if (boardCells == null || boardCells.length == 0) {
            throw new IllegalArgumentException("Array is empty or non-existent!");
        }

        Cell[][] newBoard = new Cell[boardCells.length][boardCells[0].length];
        for (int i = 0; i < boardCells.length; i++) {
            for (int j = 0; j < boardCells[0].length; j++) {
                Board.setFieldCellOnBoard(newBoard, new Point(i, j), boardCells[j][i]);
            }
        }

        return newBoard;
    }

    /**
     * Creates an instance of a cell depending on the given cell type and stores
     * this instances on the given playing field at the given coordinates.
     *
     * @param board playing field in which the new cell should be stored
     * @param point point coordinates of the position
     * @param type  type of the cell
     */
    private static void setFieldCellOnBoard(Cell[][] board, Point point, CellState type) {
        Cell cell;
        if (type == CellState.GRAVEL) {
            cell = new Cell(point, CellState.GRAVEL);
        } else if (type == CellState.TRACK) {
            cell = new Cell(point, CellState.TRACK);
        } else {
            cell = new Cell(point, CellState.LINE);
        }

        board[point.x()][point.y()] = cell;
    }

    /**
     * Getter method of cells of the board.
     *
     * @return cells of the board as a 2d array
     */
    public Cell[][] getCells() {
        return cells;
    }

    public PathList getTrack() {
        return track;
    }

    /**
     * Getter method of startAndFinishLine field.
     *
     * @return startAndFinishLine
     */
    public TrackLine getStartAndFinishLine() {
        return trackLine;
    }

    /**
     * Setter method of startAndFinishLine field.
     *
     * @param trackLine the given line input
     */
    public void setStartAndFinishLine(TrackLine trackLine) {
        this.trackLine = trackLine;
    }

    /**
     * Initializes all the track cells.
     */
    private void initCells() {
        for (int row = 0; row < this.cells[0].length; row++) {
            for (int col = 0; col < this.cells.length; col++) {
                this.cells[col][row] = new Cell(new Point(col, row));
            }
        }
    }

    /**
     * Checks if the coordinate is valid for the current array of cells.
     * Visibility is package private, so we can test is properly.
     *
     * @return true, if the coordinate is valid
     */
    public boolean areValidCoords(Point point) {
        return (point != null) &&
                (point.x() >= 0 && point.x() < this.cells.length) &&
                (point.y() >= 0 && point.y() < this.cells[point.x()].length);
    }

    /**
     * Returns the cell at the specified position of the board.
     *
     * @param point position at which the requested cell is located
     * @return cell at the requested position or null if the position is not
     * valid
     */
    public Cell getCellAtPosition(Point point) {
        return this.areValidCoords(point) ? this.cells[point.x()][point.y()] : null;
    }

    /**
     * Determines the neighbours for a specified position and returns them. Only
     * valid positions are returned as neighbours. The order in which the
     * neighbours are returned is x-1, x+1, y-1, y+1.
     *
     * @param currPos position for which neighbours should be determined
     * @return neighbours of the cell in an array with suitable length (at most
     * 4)
     */
    public Cell[] getNeighbours(Point currPos) {
        Point[] pointsToBeChecked = (currPos == null) ? new Point[]{} : new Point[]{new Point(
                currPos.x() - 1, currPos.y()), new Point(currPos.x() + 1, currPos.y()), new Point(currPos.x(),
                currPos.y() - 1), new Point(currPos.x(), currPos.y() + 1), new Point(
                currPos.x() - 1, currPos.y() - 1), new Point(currPos.x() + 1, currPos.y() - 1), new Point(
                currPos.x() + 1, currPos.y() + 1), new Point(currPos.x() - 1, currPos.y() + 1),};
        boolean[] pointIsValid = new boolean[NEIGHBOURS_NUMBER];
        for (int i = 0; i < pointsToBeChecked.length; i++) {
            pointIsValid[i] = this.areValidCoords(pointsToBeChecked[i]);
        }

        // Counts the number of valid neighbour positions
        int validPointNumber = 0;
        for (boolean b : pointIsValid) {
            if (b) {
                validPointNumber++;
            }
        }

        // Creates the neighbours based on the number of valid positions
        Cell[] neighbours = new Cell[validPointNumber];
        int j = 0;
        for (int i = 0; i < pointIsValid.length; i++) {
            if (pointIsValid[i]) {
                neighbours[j] = this.getCellAtPosition(pointsToBeChecked[i]);
                j++;
            }
        }

        return neighbours;
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a track cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoTrack(Point point) {
        if (this.areValidCoords(point)) {
            this.cells[point.x()][point.y()].turnIntoTrack();
        }
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a gravel cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoGravel(Point point) {
        if (this.areValidCoords(point)) {
            this.cells[point.x()][point.y()].turnIntoGravel();
        }
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a line cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoLine(Point point) {
        if (this.areValidCoords(point)) {
            this.trackLine.calculate(this.cells, point);
            this.drawLine();
            this.trackLine.setIsDrawn(true);
        }
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a car cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoCar(Point point) {
        if (this.areValidCoords(point)) {
            this.cells[point.x()][point.y()].turnIntoCar();
        }
    }

    /**
     * Checks if the cell is gravel or not.
     *
     * @param point point of the cell to turn into track
     * @return if the cell is gravel or not
     */
    public boolean isGravel(Point point) {
        return point != null && this.cells[point.x()][point.y()].isGravel();
    }

    /**
     * Checks if the cell is a track cell or not
     *
     * @param point the given point
     * @return if the cell is a track cell or not
     */
    public boolean isTrack(Point point) {
        return point != null && this.cells[point.x()][point.y()].isTrack();
    }

    /**
     * Checks if the cell is a part of the starting or finishing line
     *
     * @return if the cell is a part of the starting or finishing line
     */
    public boolean isLine(Point point) {
        return point != null && this.cells[point.x()][point.y()].isLine();
    }

    /**
     * Checks if the cell is car or not.
     *
     * @param point point of the cell
     * @return if the cell is car or not
     */
    public boolean isCar(Point point) {
        return point != null && this.cells[point.x()][point.y()].isCar();
    }

    /**
     * Draws a line based on the given starting and ending point on
     * the track field.
     */
    private void drawLine() {
        switch (this.trackLine.getDirection()) {
            case LEFT_RIGHT, RIGHT_LEFT -> {
                int endY = this.trackLine.getEndPoint().y();
                int lineX = this.trackLine.getEndPoint().x();
                for (int row = this.trackLine.getStartPoint().y(); row <= endY; row++) {
                    this.cells[lineX][row].turnIntoLine();
                }
            }
            case TOP_DOWN, DOWN_TOP -> {
                int endX = this.trackLine.getEndPoint().x();
                int lineY = this.trackLine.getEndPoint().y();
                for (int col = this.trackLine.getStartPoint().x(); col <= endX; col++) {
                    this.cells[col][lineY].turnIntoLine();
                }
            }
        }
    }

    /**
     * Turns the current line on the track field into normal
     * track cells instead of line cells.
     */
    public void turnLineIntoTrack() {
        switch (this.trackLine.getDirection()) {
            case LEFT_RIGHT, RIGHT_LEFT -> {
                int endY = this.trackLine.getEndPoint().y();
                int lineX = this.trackLine.getEndPoint().x();
                for (int row = this.trackLine.getStartPoint().y(); row <= endY; row++) {
                    this.turnIntoTrack(new Point(lineX, row));
                }
            }
            case TOP_DOWN, DOWN_TOP -> {
                int endX = this.trackLine.getEndPoint().x();
                int lineY = this.trackLine.getEndPoint().y();
                for (int col = this.trackLine.getStartPoint().x(); col <= endX; col++) {
                    this.turnIntoTrack(new Point(col, lineY));
                }
            }
        }
    }

    /**
     * Rotates the current start/finish line on the track field. If
     * there would be track cells on the right and left of the current vertical
     * line they would turn into line cells based on the size of the line. For
     * horizontal lines the above and the bottom cells of the current cell
     * are counted and based on the size of the line and the number of the
     * above and the bottom track cells they would turn into line cells.
     *
     * @param point point of the cell to turn into track
     */
    public void rotateLine(Point point) {
        int x = point.x();
        int y = point.y();
        LineDirection currentDirection = this.trackLine.getDirection();
        int newStartX = 0, newEndX = 0, newStartY = 0, newEndY = 0;

        this.turnLineIntoTrack();

        int topCells = this.trackLine.countTopCells(this.trackLine.getStartPoint(), y, this.cells.length);
        int bottomCells = this.trackLine.countBottomCells(this.trackLine.getEndPoint(), y, this.cells.length);
        int leftCells = this.trackLine.countLeftCells(this.trackLine.getStartPoint(), x, this.cells.length);
        int rightCells = this.trackLine.countRightCells(this.trackLine.getEndPoint(), x, this.cells.length);
        switch (currentDirection) {
            case RIGHT_LEFT -> {
                newEndX = this.changeLeftCells(x, y, bottomCells);
                newStartX = this.changeRightCells(x, y, topCells);
            }
            case LEFT_RIGHT -> {
                newStartX = this.changeRightCells(x, y, topCells);
                newEndX = this.changeLeftCells(x, y, bottomCells);
            }
            case TOP_DOWN -> {
                newStartY = this.changeTopCells(x, y, leftCells);
                newEndY = this.changeBottomCells(x, y, rightCells);
            }
            case DOWN_TOP -> {
                newEndY = this.changeBottomCells(x, y, rightCells);
                newStartY = this.changeTopCells(x, y, leftCells);
            }
        }

        switch (currentDirection) {
            case RIGHT_LEFT, LEFT_RIGHT -> {
                this.cells[x][y].turnIntoLine();
                if (newStartX < newEndX) {
                    this.trackLine.setStartPoint(new Point(newStartX, y));
                    this.trackLine.setEndPoint(new Point(newEndX, y));
                } else {
                    this.trackLine.setStartPoint(new Point(newEndX, y));
                    this.trackLine.setEndPoint(new Point(newStartX, y));
                }

                this.trackLine.rotateDirection();
            }
            case TOP_DOWN, DOWN_TOP -> {
                this.cells[x][y].turnIntoLine();
                if (newStartY < newEndY) {
                    this.trackLine.setStartPoint(new Point(x, newStartY));
                    this.trackLine.setEndPoint(new Point(x, newEndY));
                } else {
                    this.trackLine.setStartPoint(new Point(x, newEndY));
                    this.trackLine.setEndPoint(new Point(x, newStartY));
                }

                this.trackLine.rotateDirection();
            }
        }
    }

    /**
     * Changes the left cells of the clicked position,
     * and turns them into track cells.
     *
     * @param x            x-axis value of the position
     * @param y            y-axis value of the position
     * @param numOfChanges number of the cells to be changed
     * @return number of changed cells without facing gravel
     * cells
     */
    private int changeLeftCells(int x, int y, int numOfChanges) {
        int newStartX = x;
        for (int currentX = x - 1; (numOfChanges > 0 && currentX >= 0); numOfChanges--, currentX--) {
            if (this.cells[currentX][y].isTrack()) {
                this.cells[currentX][y].turnIntoLine();
                newStartX--;
            } else {
                break;
            }
        }

        return newStartX;
    }

    /**
     * Changes the right cells of the clicked position,
     * and turns them into track cells.
     *
     * @param x            x-axis value of the position
     * @param y            y-axis value of the position
     * @param numOfChanges number of the cells to be changed
     * @return number of changed cells without facing gravel
     * cells
     */
    private int changeRightCells(int x, int y, int numOfChanges) {
        int newEndX = x;
        for (int currentX = x + 1; (numOfChanges > 0 && currentX < this.cells.length); numOfChanges--, currentX++) {
            if (this.cells[currentX][y].isTrack()) {
                this.cells[currentX][y].turnIntoLine();
                newEndX++;
            } else {
                break;
            }
        }

        return newEndX;
    }

    /**
     * Changes the top cells of the clicked position,
     * and turns them into track cells.
     *
     * @param x            x-axis value of the position
     * @param y            y-axis value of the position
     * @param numOfChanges number of the cells to be changed
     * @return number of changed cells without facing gravel
     * cells
     */
    private int changeTopCells(int x, int y, int numOfChanges) {
        int newStartY = y;
        for (int currentY = y - 1; (numOfChanges > 0 && currentY >= 0); numOfChanges--, currentY--) {
            if (this.cells[x][currentY].isTrack()) {
                this.cells[x][currentY].turnIntoLine();
                newStartY--;
            } else {
                break;
            }
        }

        return newStartY;
    }

    /**
     * Changes the bottom cells of the clicked position,
     * and turns them into track cells.
     *
     * @param x            x-axis value of the position
     * @param y            y-axis value of the position
     * @param numOfChanges number of the cells to be changed
     * @return number of changed cells without facing gravel
     * cells
     */
    private int changeBottomCells(int x, int y, int numOfChanges) {
        int newEndY = y;
        for (int currentY = y + 1; (numOfChanges > 0 && currentY < this.cells.length); numOfChanges--, currentY++) {
            if (this.cells[x][currentY].isTrack()) {
                this.cells[x][currentY].turnIntoLine();
                newEndY++;
            } else {
                break;
            }
        }

        return newEndY;
    }

    /**
     * Checks whether the current board contains
     * a valid path or track or not.
     *
     * @return whether the current board contains
     * a valid path or track or not
     */
    public boolean hasValidPath() {
        boolean isValid = false;
        if (this.trackLine.isDrawn()) {
            PathList pathList = null;
            int startX = this.trackLine.getStartPoint().x();
            int startY = this.trackLine.getStartPoint().y();
            int endX = this.trackLine.getEndPoint().x();
            int endY = this.trackLine.getEndPoint().y();
            LineDirection currentDirection = this.trackLine.getDirection();

            switch (currentDirection) {
                case LEFT_RIGHT, RIGHT_LEFT -> { // Line is vertical
                    if ((
                            // Whether the top cell of the start of the line is a valid cell
                            (startY > 0 && this.areValidCoords(new Point(startX, startY - 1))
                                    && this.isGravel(new Point(startX, startY - 1)))
                                    || startY == 0 // Cell is the border itself
                    ) && (
                            // Whether the bottom cell of the end of the line is a valid cell
                            (endY < this.cells.length - 1 && this.areValidCoords(new Point(endX, endY + 1))
                                    && this.isGravel(new Point(endX, endY + 1)))
                                    || endY == this.cells.length - 1 // Cell is the border itself
                    )) {
                        switch (currentDirection) {
                            case RIGHT_LEFT -> pathList = pathFinder.getPathFromPosToPos(
                                    new Point(startX - 1, startY),
                                    new Point(endX + 1, endY),
                                    this
                            );
                            case LEFT_RIGHT -> pathList = pathFinder.getPathFromPosToPos(
                                    new Point(startX + 1, startY),
                                    new Point(endX - 1, endY),
                                    this
                            );
                        }
                    }
                }
                case TOP_DOWN, DOWN_TOP -> { // Line is horizontal
                    if ((
                            // Whether the left cell of the start of the line is a valid cell
                            (startX > 0 && this.areValidCoords(new Point(startX - 1, startY))
                                    && this.isGravel(new Point(startX - 1, startY)))
                                    || startX == 0 // Cell is the border itself
                    ) && (
                            // Whether the right cell of the start of the line is a valid cell
                            (endX < this.cells.length - 1 && this.areValidCoords(new Point(endX + 1, endY))
                                    && this.isGravel(new Point(endX + 1, endY)))
                                    || endX == this.cells.length - 1 // Cell is the border itself
                    )) {
                        switch (currentDirection) {
                            case DOWN_TOP -> pathList = pathFinder.getPathFromPosToPos(
                                    new Point(startX, startY - 1),
                                    new Point(endX, endY + 1),
                                    this
                            );
                            case TOP_DOWN -> pathList = pathFinder.getPathFromPosToPos(
                                    new Point(startX, startY + 1),
                                    new Point(endX, endY - 1),
                                    this
                            );
                        }
                    }
                }
            }

            if (pathList != null && !pathList.isEmpty()) {
                isValid = true;
                pathList.insertAtFront(new Cell(new Point(startX, startY), CellState.LINE));
                this.track = pathList;

                System.out.println(pathList);
            }
        }

        return isValid;
    }

    /**
     * Represents the game field in a matrix.
     *
     * @return the matrix of the game field.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < this.cells[0].length; row++) {
            for (int col = 0; col < this.cells.length; col++) {
                output.append(this.cells[col][row]);
                if (col != this.cells.length - 1) {
                    output.append(" ");
                }
            }

            if (row != this.cells[0].length - 1) {
                output.append("\n");
            }
        }

        return output.toString();
    }

    /**
     * Adds one row on top of the current board.
     */
    public void addRowTop() {
        Cell[][] newCells = new Cell[this.cells.length][this.cells[0].length + 1];
        for (int col = 0; col < this.cells.length; col++) {
            newCells[col][0] = new Cell(new Point(col, 0));
        }
        for (int row = 1; row < newCells[0].length; row++) {
            for (int col = 0; col < newCells.length; col++) {
                newCells[col][row] = this.cells[col][row - 1];
            }
        }

        this.cells = newCells;
    }

    /**
     * Adds one row to bottom of the current board.
     */
    public void addRowBottom() {
        Cell[][] newCells = new Cell[this.cells.length][this.cells[0].length + 1];
        for (int row = 0; row < newCells[0].length - 1; row++) {
            for (int col = 0; col < newCells.length; col++) {
                newCells[col][row] = this.cells[col][row];
            }
        }
        for (int col = 0; col < this.cells.length; col++) {
            newCells[col][newCells[0].length - 1] = new Cell(new Point(col, newCells[0].length - 1));
        }

        this.cells = newCells;
    }

    /**
     * Adds one column to left of the current board.
     */
    public void addColLeft() {
        Cell[][] newCells = new Cell[this.cells.length + 1][this.cells[0].length];
        for (int row = 0; row < this.cells[0].length; row++) {
            newCells[0][row] = new Cell(new Point(0, row));
        }
        for (int row = 0; row < newCells[0].length; row++) {
            for (int col = 1; col < newCells.length; col++) {
                newCells[col][row] = this.cells[col - 1][row];
            }
        }

        this.cells = newCells;
    }

    /**
     * Adds one column to right of the current board.
     */
    public void addColRight() {
        Cell[][] newCells = new Cell[this.cells.length + 1][this.cells[0].length];
        for (int row = 0; row < newCells[0].length; row++) {
            for (int col = 0; col < newCells.length - 1; col++) {
                newCells[col][row] = this.cells[col][row];
            }
        }
        for (int row = 0; row < this.cells[0].length; row++) {
            newCells[newCells.length - 1][row] = new Cell(new Point(newCells.length - 1, row));
        }

        this.cells = newCells;
    }

    /**
     * Removes one row from top of the current board.
     */
    public void removeRowTop() {
        Cell[][] newCells = new Cell[this.cells.length][this.cells[0].length - 1];
        for (int row = 1; row < this.cells[0].length; row++) {
            for (int col = 0; col < newCells.length; col++) {
                newCells[col][row - 1] = this.cells[col][row];
            }
        }

        this.cells = newCells;
    }

    /**
     * Removes one row from bottom of the current board.
     */
    public void removeRowBottom() {
        Cell[][] newCells = new Cell[this.cells.length][this.cells[0].length - 1];
        for (int row = 0; row < this.cells[0].length - 1; row++) {
            for (int col = 0; col < newCells.length; col++) {
                newCells[col][row] = this.cells[col][row];
            }
        }

        this.cells = newCells;
    }

    /**
     * Removes one column from the left of the current board.
     */
    public void removeColLeft() {
        Cell[][] newCells = new Cell[this.cells.length - 1][this.cells[0].length];
        for (int row = 0; row < this.cells[0].length; row++) {
            for (int col = 1; col < this.cells.length; col++) {
                newCells[col - 1][row] = this.cells[col][row];
            }
        }

        this.cells = newCells;
    }

    /**
     * Removes one column from the right of the current board.
     */
    public void removeColRight() {
        Cell[][] newCells = new Cell[this.cells.length - 1][this.cells[0].length];
        for (int row = 0; row < this.cells[0].length; row++) {
            for (int col = 0; col < this.cells.length - 1; col++) {
                newCells[col][row] = this.cells[col][row];
            }
        }

        this.cells = newCells;
    }
}
