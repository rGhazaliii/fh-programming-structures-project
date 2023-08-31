package logic.list;

import logic.Cell;

/**
 * Represents the cell list element which has a payload as data and the reference to
 * the next element of the list.
 *
 * @author ite105705
 */
public class CellListElement extends CellListEmpty implements CellListInterface {

    private final Cell payload;
    private CellListEmpty next;

    public CellListElement(Cell payload, CellListEmpty next) {
        this.payload = payload;
        this.next = next;
    }

    @Override
    public Cell getPayload() {
        return this.payload;
    }

    @Override
    public CellListEmpty getNext() {
        return this.next;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return (1 + this.getNext().size());
    }

    @Override
    public CellListEmpty removeFirst() {
        return this.getNext();
    }

    @Override
    public boolean contains(Cell payload) {
        return getPayload().equals(payload) || this.getNext().contains(payload);
    }

    @Override
    public CellListEmpty append(Cell payload) {
        if (payload != null) {
            this.next = this.next.append(payload);
        }

        return this;
    }

    @Override
    public CellListEmpty getLastElement() {
        return this.getNext().isEmpty() ? this : this.getNext().getLastElement();
    }

    @Override
    public String toString() {
        return this.getNext().isEmpty() ? this.getPayload().getPosition().toString() : (this.getPayload().getPosition()
                                                                                        + " -> "
                                                                                        + this.getNext().toString());
    }

    @Override
    public CellListInterface remove(Cell payload) {
        return null;
    }

    @Override
    public Cell findMinimumAccumulatedCosts(int currentMinAccumulatedCosts, Cell currentCell) {
        return null;
    }

    @Override
    public Cell getAt(int index) {
        if (index == 0) {
            return this.payload;
        } else if (index > 0) {
            return this.next.getAt(--index);
        }

        return null;
    }
}
