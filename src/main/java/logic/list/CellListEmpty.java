package logic.list;

import logic.Cell;

/**
 * Represents the empty list (or the end of the list). Is inherited by the class
 * CellListElement..
 *
 * @author fme
 */
public class CellListEmpty implements CellListInterface {

    /**
     * Returns the payload of the current element. If this method is called for
     * the empty list, an AssertionError is thrown, because the empty list does
     * not contain any elements.
     *
     * @return the payload of the current element
     */
    public Cell getPayload() {
        throw new AssertionError("there is no payload in empty list");
    }

    /**
     * Returns the next element of the list. If this method is called for the
     * empty list, an AssertionError is thrown, because the empty list does not
     * have a succeding element.
     *
     * @return the next element of the list
     */
    public CellListEmpty getNext() {
        throw new AssertionError("there is no next element in empty list");
    }

    /**
     * Determines if the list is empty.
     *
     * @return true, if the list is empty
     */
    public boolean isEmpty() {
        return true;
    }

    /**
     * Determines the size of the list. 0 for the empty list.
     *
     * @return size of the list
     */
    public int size() {
        return 0;
    }

    @Override
    public int pathCost() {
        return 0;
    }

    /**
     * Adds the given payload to the front of the list.
     *
     * @param payload the payload that should be added to the front of the list
     * @return a new list with the given payload and this as successor
     */
    public CellListEmpty insertAtFront(Cell payload) {
        return new CellListElement(payload, this);
    }

    /**
     * Deletes the first element of the list
     *
     * @return this element
     */
    public CellListEmpty removeFirst() {
        return this;
    }

    /**
     * Checks if the given payload is a contained in the list.
     *
     * @param payload the given payload
     * @return true, if the given payload is contained in the list
     */
    public boolean contains(Cell payload) {
        return false;
    }

    /**
     * Appends the given payload to the end of the list.
     *
     * @param payload die gegebene Nutzlast
     * @return eine neue Liste mit der angehängten Nutzlast.
     */
    public CellListEmpty append(Cell payload) {
        return new CellListElement(payload, this);
    }

    @Override
    public CellListInterface remove(Cell payload) {
        return null;
    }

    /**
     * Returns the last element of the list. This element if the list is empty.
     *
     * @return last element of the list, this element if the list is empty
     */
    public CellListEmpty getLastElement() {
        return this;
    }

    @Override
    public Cell findMinimumAccumulatedCosts(int currentMinAccumulatedCosts, Cell currentCell) {
        return null;
    }

    @Override
    public String toString() {
        return "EMPTY";
    }

    @Override
    public Cell getAt(int index) {
        return null;
    }
}
