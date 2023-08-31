package logic.pathfinding;

import logic.Board;
import logic.CellState;
import logic.LineDirection;
import logic.Point;
import logic.list.PathList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PubDijkstraTest {

    private final static PathFinder pathfinder = new Dijkstra();


    /* --- resolvePath() Tests ---- */

    @Test
    public void testGetPathFromPosToPos_tour() {
        Board board = new Board(new CellState[][]{
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL}, // x = 0
                {CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL}, // x = 1
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL}
        }, LineDirection.TOP_DOWN);
        PathList result = pathfinder.getPathFromPosToPos(new Point(9, 7), new Point(11, 7), board);

        assertNotNull(result);
        assertEquals(27, result.cellAmountInPath());
    }

    @Test
    public void testGetPathFromPosToPos_10x10_tour() {
        Board board = new Board(new CellState[][]{
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL}, // x = 0
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL}, // x = 1
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.LINE},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK},
                {CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.GRAVEL, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK, CellState.TRACK},
        }, LineDirection.TOP_DOWN);
        PathList result = pathfinder.getPathFromPosToPos(new Point(9, 6), new Point(9, 8), board);

        assertNotNull(result);
        assertEquals(11, result.cellAmountInPath());
    }
}
