package logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testIsValid() {
        String cells = """
                0 0 0 0 0 0 0 0 0 0
                0 0 1 1 1 1 1 1 0 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 2 2 2 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 0 1 1 1 1 1 1 0 0
                0 0 0 0 0 0 0 0 0 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(6, 10));
        board.getStartAndFinishLine().setEndPoint(new Point(8, 10));

        assertTrue(board.hasValidPath());
    }

    @Test
    public void testIsValid_lineOnTheBorders() {
        String cells = """
                0 0 0 0 0 0 0 0 0 0
                0 0 2 2 2 1 1 1 0 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 0 1 1 1 1 1 1 0 0
                0 0 0 0 0 0 0 0 0 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(2, 1));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 1));

        assertFalse(board.hasValidPath());

        cells = """
                0 0 0 0 0 0 0 0 0 0
                0 0 1 1 1 1 1 1 0 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 1 1 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 1 1 1 0 0 1 1 1 0
                0 2 1 1 0 0 1 1 1 0
                0 2 1 1 1 1 1 1 1 0
                0 2 1 1 1 1 1 1 1 0
                0 0 1 1 1 1 1 1 0 0
                0 0 0 0 0 0 0 0 0 0""";
        board = new Board(cells, LineDirection.LEFT_RIGHT);
        board.getStartAndFinishLine().setStartPoint(new Point(1, 15));
        board.getStartAndFinishLine().setEndPoint(new Point(1, 17));

        assertFalse(board.hasValidPath());
    }

    @Test
    public void testIsValid_5x5() {
        String cells = """
                0 1 1 1 0
                1 1 0 1 1
                2 2 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(0, 2));
        board.getStartAndFinishLine().setEndPoint(new Point(1, 2));

        assertTrue(board.hasValidPath());
    }

    @Test
    public void testIsValid_5x5_oneCellLine_invalid() {
        String cells = """
                0 1 1 1 0
                1 1 0 1 1
                1 2 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(1, 2));
        board.getStartAndFinishLine().setEndPoint(new Point(1, 2));

        assertFalse(board.hasValidPath());
    }

    @Test
    public void testIsValid_5x5_oneCellLine_valid() {
        String cells = """
                0 0 0 0 0
                0 1 1 1 0
                0 1 0 2 0
                0 1 1 1 0
                0 0 0 0 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(3, 2));
        board.getStartAndFinishLine().setEndPoint(new Point(3, 2));

        assertTrue(board.hasValidPath());

        cells = """
                1 1 1 1 1
                1 0 0 0 1
                1 0 0 0 2
                1 0 0 0 1
                1 1 1 1 1""";
        board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(4, 2));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 2));

        assertTrue(board.hasValidPath());
    }

    @Test
    public void testIsValid_10x10_oneCellLine_valid() {
        String cells = """
                0 0 0 0 0 0 0 0 0 0
                0 0 0 0 0 0 0 0 0 0
                0 0 0 0 0 0 0 0 0 0
                0 0 0 0 0 0 0 0 0 0
                0 0 0 0 0 0 0 0 0 0
                0 0 0 0 0 1 1 1 1 1
                0 0 0 0 0 1 0 0 0 1
                0 0 0 0 0 1 0 0 0 2
                0 0 0 0 0 1 0 0 0 1
                0 0 0 0 0 1 1 1 1 1""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(9, 7));
        board.getStartAndFinishLine().setEndPoint(new Point(9, 7));

        assertTrue(board.hasValidPath());
    }

    @Test
    public void testTurnLineIntoTrack_5x5_verticalLine_onBorder() {
        String cells = """
                0 1 1 1 0
                1 1 0 1 1
                2 2 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(0, 2));
        board.getStartAndFinishLine().setEndPoint(new Point(1, 2));

        board.turnLineIntoTrack();
        String expected = """
                0 1 1 1 0
                1 1 0 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testTurnLineIntoTrack_5x5_horizontalLine_onBorder() {
        String cells = """
                0 1 1 1 0
                1 1 0 1 2
                1 1 0 1 2
                1 1 0 1 2
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.LEFT_RIGHT);
        board.getStartAndFinishLine().setStartPoint(new Point(4, 1));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 3));

        board.turnLineIntoTrack();
        String expected = """
                0 1 1 1 0
                1 1 0 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testTurnLineIntoTrack_5x5_horizontalLine() {
        String cells = """
                0 1 1 1 0
                1 2 2 2 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(1, 1));
        board.getStartAndFinishLine().setEndPoint(new Point(3, 1));

        board.turnLineIntoTrack();
        String expected = """
                0 1 1 1 0
                1 1 1 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testTurnLineIntoTrack_5x5_verticalLine() {
        String cells = """
                0 1 1 1 0
                1 1 0 2 1
                1 1 0 2 1
                1 1 0 2 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.LEFT_RIGHT);
        board.getStartAndFinishLine().setStartPoint(new Point(3, 1));
        board.getStartAndFinishLine().setEndPoint(new Point(3, 3));

        board.turnLineIntoTrack();
        String expected = """
                0 1 1 1 0
                1 1 0 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testTurnLineIntoTrack_5x5_verticalLine1() {
        String cells = """
                1 1 1 1 2
                0 1 0 1 2
                0 1 0 1 2
                0 1 0 1 2
                0 1 1 1 2""";
        Board board = new Board(cells, LineDirection.RIGHT_LEFT);
        board.getStartAndFinishLine().setStartPoint(new Point(4, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 4));

        board.turnLineIntoTrack();
        String expected = """
                1 1 1 1 1
                0 1 0 1 1
                0 1 0 1 1
                0 1 0 1 1
                0 1 1 1 1""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testRotateLine_5x5_topDown() {
        String cells = """
                0 2 2 2 0
                1 1 0 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(1, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(3, 0));

        board.rotateLine(new Point(1, 0));
        String expected = """
                0 2 1 1 0
                1 2 0 1 1
                1 2 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testRotateLine_5x5_downTop() {
        String cells = """
                0 2 2 2 0
                1 1 0 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.DOWN_TOP);
        board.getStartAndFinishLine().setStartPoint(new Point(1, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(3, 0));

        board.rotateLine(new Point(1, 0));
        String expected = """
                0 2 1 1 0
                1 2 0 1 1
                1 2 0 1 1
                1 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testRotateLine_5x5_topDown_shrinking() {
        String cells = """
                0 1 1 1 0
                1 1 0 1 1
                2 2 0 1 1
                0 1 0 1 1
                0 1 1 1 0""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(0, 2));
        board.getStartAndFinishLine().setEndPoint(new Point(1, 2));

        board.rotateLine(new Point(0, 2));
        String expected = """
                0 1 1 1 0
                1 1 0 1 1
                2 1 0 1 1
                0 1 0 1 1
                0 1 1 1 0""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testRotateLine_5x5_rightLeft() {
        String cells = """
                1 1 1 1 2
                0 1 0 1 2
                0 1 0 1 2
                0 1 0 1 2
                0 1 1 1 2""";
        Board board = new Board(cells, LineDirection.RIGHT_LEFT);
        board.getStartAndFinishLine().setStartPoint(new Point(4, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 4));

        board.rotateLine(new Point(4, 0));
        String expected = """
                2 2 2 2 2
                0 1 0 1 1
                0 1 0 1 1
                0 1 0 1 1
                0 1 1 1 1""";

        assertEquals(expected, board.toString());
    }

    @Test
    public void testRotateLine_5x5_leftRight() {
        String cells = """
                2 1 1 1 1
                2 1 0 1 0
                2 1 0 1 0
                2 1 0 1 0
                2 1 1 1 0""";
        Board board = new Board(cells, LineDirection.LEFT_RIGHT);
        board.getStartAndFinishLine().setStartPoint(new Point(0, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(0, 4));

        board.rotateLine(new Point(0, 0));
        String expected = """
                2 1 1 1 1
                1 1 0 1 0
                1 1 0 1 0
                1 1 0 1 0
                1 1 1 1 0""";

        assertEquals(expected, board.toString());
        assertEquals(LineDirection.TOP_DOWN, board.getStartAndFinishLine().getDirection());
    }

    @Test
    public void testRotateLine_5x5_leftRight_2() {
        String cells = """
                0 1 1 1 2
                1 1 0 1 2
                1 1 0 1 2
                1 1 0 1 2
                0 1 1 1 2""";
        Board board = new Board(cells, LineDirection.LEFT_RIGHT);
        board.getStartAndFinishLine().setStartPoint(new Point(4, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 4));

        board.rotateLine(new Point(4, 4));
        String expected = """
                0 1 1 1 1
                1 1 0 1 1
                1 1 0 1 1
                1 1 0 1 1
                0 1 1 1 2""";

        assertEquals(expected, board.toString());
        assertEquals(LineDirection.TOP_DOWN, board.getStartAndFinishLine().getDirection());
    }

    @Test
    public void testRotateLine_5x5_leftRight_3() {
        String cells = """
                2 2 2 2 2
                1 1 1 1 1
                1 1 1 1 1
                1 0 0 0 1
                1 1 1 1 1""";
        Board board = new Board(cells, LineDirection.TOP_DOWN);
        board.getStartAndFinishLine().setStartPoint(new Point(0, 0));
        board.getStartAndFinishLine().setEndPoint(new Point(4, 0));

        board.rotateLine(new Point(3, 0));
        String expected = """
                1 1 1 2 1
                1 1 1 2 1
                1 1 1 1 1
                1 0 0 0 1
                1 1 1 1 1""";

        assertEquals(expected, board.toString());
        assertEquals(LineDirection.RIGHT_LEFT, board.getStartAndFinishLine().getDirection());
    }
}
