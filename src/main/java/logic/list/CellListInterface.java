package logic.list;

import logic.Cell;

/**
 * Linked list of Board Cells. This interface has to be implemented by the
 * element with the payload and the empty element. Recursive structure, when
 * possible the methods need to be implemented recursively.
 *
 * @author fme, ite105705
 */
public interface CellListInterface {
    /**
     * Returns the payload of the current element. If this method is called for
     * the empty list, an AssertionError is thrown, because the empty list does
     * not contain any elements.
     *
     * @return the payload of the current element
     */
    Cell getPayload();

    /**
     * Returns the next element of the list. If this method is called for the
     * empty list, an AssertionError is thrown, because the empty list does not
     * have a succeeding element.
     *
     * @return the next element of the list
     */
    CellListInterface getNext();

    /**
     * Determines if the list is empty.
     *
     * @return true, if the list is empty
     */
    boolean isEmpty();

    /**
     * Determines the size of the list. 0 for the empty list.
     *
     * @return size of the list
     */
    int size();

    /**
     * Determines the total cost of a path (represented by this list of cells).
     * New method for assignment 7.
     *
     * @return Kosten des Pfades
     */
    int pathCost();

    /**
     * Adds the given payload to the front of the list.
     *
     * @param payload the payload that should be added to the front of the list
     * @return a new list with the given payload and this as successor
     */
    CellListInterface insertAtFront(Cell payload);


    /**
     * Deletes the first element of the list
     *
     * @return this element
     */
    CellListInterface removeFirst();

    /**
     * Checks if the given payload is a contained in the list.
     *
     * @param payload the given payload
     * @return true, if the given payload is contained in the list
     */
    boolean contains(Cell payload);

    /**
     * Appends the given payload to the end of the list.
     *
     * @param payload die gegebene Nutzlast
     * @return eine neue Liste mit der angehängten Nutzlast.
     */
    CellListInterface append(Cell payload);

    /**
     * Removes the given payload from the list. The empty list remains
     * unchanged. If the payload is not part of the list, the list will not be
     * changed. New method for assignment 7.
     *
     * @param payload payload to be removed from the list
     * @return a list without the given payload
     */
    CellListInterface remove(Cell payload);

    /**
     * Returns the last element of the list. This element if the list is empty.
     *
     * @return last element of the list, this element if the list is empty
     */
    CellListInterface getLastElement();

    /**
     * Determines the cell of the list, that is easiest to reach from the start
     * position (has the least accumulated cost of all the cells in this list).
     * New method for assignment 7.
     *
     * @param currentMinAccumulatedCosts the current smallest accumulated costs
     *                                   (necessary for recursion)
     * @param currentCell                Cell with the current smallest
     *                                   accumulated cost (necessary for
     *                                   recursion)
     * @return Cell that can be reached easiest from the start position (least
     * accumulatd costs)
     */
    Cell findMinimumAccumulatedCosts(int currentMinAccumulatedCosts, Cell currentCell);

    /**
     * Concatenates all the position of the cells in the list seperated through
     * ` -> `. For example, it creates the String `2/2 -> 3/2 -> 3/3`. After the
     * last position of the list there should be no additional characters. If
     * the list is empty, the method returns "EMPTY".
     *
     * @return position of the payload & all successor seperated through  ` -> `
     */
    @Override
    String toString();

    /**
     * Retrieves the value at the given index. Results in null for the empty list.
     *
     * @param index The 0 based index
     * @return The value at the index or null, if the list is empty
     */
    Cell getAt(int index);
}
