package logic;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrackLineTest {

    private Cell[][] createCells(int cols, int rows) {
        Cell[][] cells = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new Cell();
            }
        }

        return cells;
    }

    @Test
    public void testTrackLine() {
        TrackLine trackLine = new TrackLine();

        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
    }

    @Test
    public void testCalculate_4x4Cells_oneCellLine_defaultDirection() {
        Cell[][] cells = this.createCells(4, 4);
        TrackLine trackLine = new TrackLine();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        assertEquals(0, trackLine.getStartPoint().x());
        assertEquals(0, trackLine.getStartPoint().y());
        assertEquals(0, trackLine.getEndPoint().x());
        assertEquals(0, trackLine.getEndPoint().y());
    }

    @Test
    public void testCalculate_4x4Cells_oneCellLine_defaultDirection_previousCellUnchanged() {
        Cell[][] cells = this.createCells(4, 4);
        TrackLine trackLine = new TrackLine();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        assertEquals(0, trackLine.getStartPoint().x());
        assertEquals(0, trackLine.getStartPoint().y());
        assertEquals(0, trackLine.getEndPoint().x());
        assertEquals(0, trackLine.getEndPoint().y());

        trackLine.calculate(cells, new Point(2, 2));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        assertEquals(2, trackLine.getStartPoint().x());
        assertEquals(2, trackLine.getStartPoint().y());
        assertEquals(2, trackLine.getEndPoint().x());
        assertEquals(2, trackLine.getEndPoint().y());

        assertTrue(cells[0][0].isGravel());
    }

    @Test
    public void testCalculate_10x10Cells_oneCellLine_defaultDirection() {
        Cell[][] cells = this.createCells(10, 10);
        TrackLine trackLine = new TrackLine();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        assertEquals(0, trackLine.getStartPoint().x());
        assertEquals(0, trackLine.getStartPoint().y());
        assertEquals(0, trackLine.getEndPoint().x());
        assertEquals(0, trackLine.getEndPoint().y());

        trackLine.calculate(cells, new Point(9, 9));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        assertEquals(9, trackLine.getStartPoint().x());
        assertEquals(9, trackLine.getStartPoint().y());
        assertEquals(9, trackLine.getEndPoint().x());
        assertEquals(9, trackLine.getEndPoint().y());
    }

    @Test
    public void testCalculate_4x4Cells_verticalLine_defaultDirection() {
        TrackLine trackLine = new TrackLine();
        Cell[][] cells = this.createCells(4, 4);
        cells[0][0].turnIntoTrack();
        cells[0][1].turnIntoTrack();
        cells[0][2].turnIntoTrack();
        cells[0][3].turnIntoTrack();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        assertEquals(0, trackLine.getStartPoint().x());
        assertEquals(0, trackLine.getStartPoint().y());
        assertEquals(0, trackLine.getEndPoint().x());
        assertEquals(3, trackLine.getEndPoint().y());
    }

    @Test
    public void testCalculate_4x4Cells_horizontalLine_defaultDirection() {
        TrackLine trackLine = new TrackLine();
        Cell[][] cells = this.createCells(4, 4);
        cells[0][0].turnIntoTrack();
        cells[1][0].turnIntoTrack();
        cells[2][0].turnIntoTrack();
        cells[3][0].turnIntoTrack();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.TOP_DOWN, trackLine.getDirection());
        assertEquals(0, trackLine.getStartPoint().x());
        assertEquals(0, trackLine.getStartPoint().y());
        assertEquals(3, trackLine.getEndPoint().x());
        assertEquals(0, trackLine.getEndPoint().y());
    }

    @Test
    public void testToggleDirection_4x4Cells_vertical() {
        Cell[][] cells = this.createCells(4, 4);
        cells[0][0].turnIntoTrack();
        cells[0][1].turnIntoTrack();
        TrackLine trackLine = new TrackLine();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        trackLine.toggleDirection();
        assertEquals(LineDirection.RIGHT_LEFT, trackLine.getDirection());
        trackLine.toggleDirection();
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
    }

    @Test
    public void testToggleDirection_4x4Cells_horizontal() {
        Cell[][] cells = this.createCells(4, 4);
        cells[0][0].turnIntoTrack();
        cells[1][0].turnIntoTrack();
        TrackLine trackLine = new TrackLine();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.TOP_DOWN, trackLine.getDirection());
        trackLine.toggleDirection();
        assertEquals(LineDirection.DOWN_TOP, trackLine.getDirection());
        trackLine.toggleDirection();
        assertEquals(LineDirection.TOP_DOWN, trackLine.getDirection());
    }

    @Test
    public void testRotateDirection_4x4Cells_oneCellLine() {
        Cell[][] cells = this.createCells(4, 4);
        TrackLine trackLine = new TrackLine();

        trackLine.calculate(cells, new Point(0, 0));
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
        trackLine.rotateDirection();
        assertEquals(LineDirection.TOP_DOWN, trackLine.getDirection());
        trackLine.rotateDirection();
        assertEquals(LineDirection.RIGHT_LEFT, trackLine.getDirection());
        trackLine.rotateDirection();
        assertEquals(LineDirection.DOWN_TOP, trackLine.getDirection());
        trackLine.rotateDirection();
        assertEquals(LineDirection.LEFT_RIGHT, trackLine.getDirection());
    }

    @Test
    public void testIsOnLine_xAxisLine() {
        TrackLine line = new TrackLine(new Point(0, 0), new Point(5, 0));

        assertTrue(line.isOnLine(new Point(2, 0)));
    }

    @Test
    public void testIsOnLine_yAxisLine() {
        TrackLine line = new TrackLine(new Point(0, 0), new Point(0, 5));

        assertTrue(line.isOnLine(new Point(0, 2)));
    }

    @Test
    public void testIsOnLine_fromOrigin() {
        TrackLine line = new TrackLine(new Point(0, 0), new Point(5, 5));

        assertTrue(line.isOnLine(new Point(2, 2)));
    }

    @Test
    public void testIsOnLine_fromOrigin_pointOutsideLine() {
        TrackLine line = new TrackLine(new Point(0, 0), new Point(5, 5));

        assertFalse(line.isOnLine(new Point(6, 6)));
    }

    @Test
    public void testIsOnLine_onCell() {
        TrackLine line = new TrackLine(new Point(9, 7), new Point(9, 7));

        assertFalse(line.isOnLine(new Point(9, 6)));
    }
}
