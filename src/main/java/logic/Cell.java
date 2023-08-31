package logic;

/**
 * Logic of the cells of the Racetrack board.
 *
 * @author ite105705
 */
public class Cell {

    /**
     * Holds the current state of the cell.
     */
    private CellState currentState;

    /**
     * Cell, that is in the path the predecessor of this cell. Important for the
     * pathfinder.
     */
    private Cell parent;

    /**
     * Point of the cell on the field
     */
    private Point point;

    /**
     * Creates a cell with the default state of gravel.
     */
    public Cell() {
        this.currentState = CellState.GRAVEL;
    }

    /**
     * Creates a cell with its position.
     */
    public Cell(Point point) {
        this();
        this.point = point;
    }

    /**
     * Creates a cell with the two arguments.
     *
     * @param point     the given position
     * @param cellState the given state
     */
    Cell(Point point, CellState cellState) {
        this.point = point;
        this.currentState = cellState;
    }

    /**
     * Getter method of the currentState of the cell.
     *
     * @return the currentState of the cell
     */
    public CellState getCurrentState() {
        return this.currentState;
    }

    /**
     * Returns the parent cell. The parent cell is the cell that is in the path
     * located before this cell.
     *
     * @return cell, that is in a path before this cell
     */
    public Cell getParentCell() {
        return this.parent;
    }

    /**
     * Sets the parent cell. The parent cell is the cell that is located in the
     * path right before this cell (the predecessor).
     *
     * @param parent predecessor of this cell in the path
     */
    public void setParentCell(Cell parent) {
        this.parent = parent;
    }

    /**
     * Returns the position of the cell on the playing field.
     *
     * @return position of the cell on the playing field.
     */
    public Point getPosition() {
        return this.point;
    }

    /**
     * Checks if the cell is gravel or not.
     *
     * @return if the cell is gravel or not
     */
    public boolean isGravel() {
        return this.currentState == CellState.GRAVEL;
    }

    /**
     * Checks if the cell is a track cell or not
     *
     * @return if the cell is a track cell or not
     */
    public boolean isTrack() {
        return this.currentState == CellState.TRACK;
    }

    /**
     * Checks if the cell is a part of the starting or finishing line
     *
     * @return if the cell is a part of the starting or finishing line
     */
    public boolean isLine() {
        return this.currentState == CellState.LINE;
    }

    /**
     * Checks if the cell is a car or not
     *
     * @return if the cell is a car cell or not
     */
    public boolean isCar() {
        return this.currentState == CellState.CAR;
    }

    /**
     * Changes the current state of the cell to gravel.
     */
    public void turnIntoGravel() {
        this.currentState = CellState.GRAVEL;
    }

    /**
     * Changes the current state of the cell to track.
     */
    public void turnIntoTrack() {
        this.currentState = CellState.TRACK;
    }

    /**
     * Changes the current state of the cell to part of starting
     * or finishing line.
     */
    public void turnIntoLine() {
        this.currentState = CellState.LINE;
    }

    /**
     * Changes the current state of the cell to car cell.
     */
    public void turnIntoCar() {
        this.currentState = CellState.CAR;
    }

    /**
     * Changes the state for the cell (from gravel to track and the other way around)
     */
    public void toggleBetweenGravelAndTrack() {
        switch (this.currentState) {
            case GRAVEL -> this.currentState = CellState.TRACK;
            case TRACK -> this.currentState = CellState.GRAVEL;
        }
    }

    /**
     * Converts the cell to its string representation based
     * on its current state.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return switch (this.currentState) {
            case GRAVEL -> "0";
            case TRACK -> "1";
            case LINE -> "2";
            case CAR -> "3";
        };
    }

    /**
     * Returns the cell state based on its given string
     * value.
     *
     * @param cell the given cell state
     * @return the cell state based on its given string
     * value
     */
    public static CellState toCellState(String cell) {
        return switch (cell) {
            case "0" -> CellState.GRAVEL;
            case "1", "3" -> CellState.TRACK;
            case "2" -> CellState.LINE;
            default -> throw new IllegalStateException("Unexpected value: " + cell);
        };
    }
}
