package logic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellTest {

    @Test
    public void testCell() {
        Cell cell = new Cell();

        assertEquals(CellState.GRAVEL, cell.getCurrentState());
    }

    @Test
    public void testToggleBetweenGravelAndTrack() {
        Cell cell = new Cell();

        assertEquals(CellState.GRAVEL, cell.getCurrentState());
        cell.toggleBetweenGravelAndTrack();
        assertEquals(CellState.TRACK, cell.getCurrentState());
    }

    @Test
    public void testToString() {
        Cell cell = new Cell();

        assertEquals("0", cell.toString());
        cell.toggleBetweenGravelAndTrack();
        assertEquals("1", cell.toString());
        cell.turnIntoLine();
        assertEquals("2", cell.toString());
    }
}
