package logic.list;

import logic.Cell;
import logic.Point;

/**
 * List class to represent a path and to hide the details of the
 * implementation.
 *
 * @author fme, ite105705
 */
public class PathList {

    /**
     * Entry point to the list. Reference to the first element of it.
     */
    private CellListInterface head;

    /**
     * Constructor that creates an empty list.
     */
    public PathList() {
        head = new CellListEmpty();
    }

    /**
     * Constructor that is solely used for testing. Create a list with the given
     * values of varArgs. When calling this method any number of parameter of
     * the respective type can be stated (e.g. <code>new PathList(cellA,
     * cellB)</code>). To access the given values an array can be used
     * (<code>BoardCell firstParam = values[0]</code>). Use the method
     * <code>insertAtFront()</code> to insert the given values into the list.
     *
     * @param values all values that should be added to the list
     */
    PathList(Cell... values) {
        this();

        if (values != null) {
            for (Cell value : values) {
                this.insertAtFront(value);
            }
        }
    }

    /**
     * Returns the head of the list. Method only used for testing of the path list.
     *
     * @return head (or start) of the list
     */
    CellListInterface getListStart() {
        return this.head;
    }

    /**
     * Determines if the list is empty.
     *
     * @return true, if the list is empty
     */
    public boolean isEmpty() {
        return this.head.isEmpty();
    }

    /**
     * Determines the numbers of cells (nodes) in the path.
     *
     * @return number of cells in the path
     */
    public int cellAmountInPath() {
        return this.head.size();
    }

    /**
     * Adds the given payload to the front of the list.
     *
     * @param payload the payload that should be added to the front of the list
     */
    public void insertAtFront(Cell payload) {
        if (payload != null) {
            this.head = this.head.insertAtFront(payload);
        }
    }

    /**
     * Deletes the first element of the path list.
     */
    public void removeAtFirst() {
        this.head = this.head.removeFirst();
    }

    /**
     * Returns the payload of the first element.
     *
     * @return payload of the first element
     */
    public Cell getFirstElement() {
        return this.getListStart().getPayload();
    }

    /**
     * Determines if the list contains a given payload.
     *
     * @param payload payload to be checked for
     * @return true, if the payload is contained in the list
     */
    public boolean contains(Cell payload) {
        return this.head.contains(payload);
    }

    /**
     * Appends the given payload to the end of the list.
     *
     * @param payload the payload that should be appended to the list
     */
    public void append(Cell payload) {
        if (payload != null) {
            this.head = this.head.append(payload);
        }
    }

    /**
     * Converts the path list into an array of positions.
     *
     * @return Array of positions
     */
    public Point[] toPositionArray() {
        int size = this.head.size();
        Point[] positions = new Point[size];
        PathList duplicate = this.copy();
        int i = 0;
        while (duplicate.getListStart() instanceof CellListElement) {
            positions[i] = duplicate.getListStart().getPayload().getPosition();
            duplicate.head = duplicate.getListStart().getNext();
            i++;
        }

        return positions;
    }

    @Override
    public String toString() {
        return head.toString();
    }

    public int pathCosts() {
        int totalCost = 0;
        CellListInterface duplicate = this.head;
        while (duplicate instanceof CellListElement) {
            totalCost += duplicate.pathCost();
            duplicate = duplicate.getNext();
        }

        return totalCost;
    }

    /**
     * Removes the cell from the list.
     *
     * @param cell the given cell
     */
    public void remove(Cell cell) {
        this.head = this.head.remove(cell);
    }

    /**
     * Creates a new copy of path list with all the elements of the current one
     *
     * @return the new path list
     */
    private PathList copy() {
        PathList newPathList = new PathList();
        CellListInterface currentElement = this.head;
        while (currentElement instanceof CellListElement) {
            newPathList.append(currentElement.getPayload());
            currentElement = currentElement.getNext();
        }

        return newPathList;
    }

    /**
     * Retrieves the value at the given index. Results in null for the empty list.
     *
     * @param index The 0 based index
     * @return The value at the index or null, if the list is empty
     */
    public Cell getAt(int index) {
        return this.head.getAt(index);
    }

    /**
     * Determines the size of the list. 0 for the empty list.
     *
     * @return size of the list
     */
    public int size() {
        return this.head.size();
    }
}
