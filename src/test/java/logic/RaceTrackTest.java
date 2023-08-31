package logic;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RaceTrackTest {

    @Test
    public void test_4x4RaceTrack() {
        String field = """
                0 0 0 0
                0 0 0 0
                0 0 0 0
                0 0 0 0""";
        RaceTrack game = new RaceTrack(new FakeGUI(), 4, 4);

        assertEquals(field, game.getBoard().toString());
    }

    @Test
    public void testHandleClickedTrack_4x4RaceTrack_leftClick() {
        String field = """
                1 0 0 0
                0 0 0 0
                0 0 0 0
                0 0 0 0""";
        RaceTrack game = new RaceTrack(new FakeGUI(), 4, 4);
        MouseEvent leftClick = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
//        game.handleClickedOnTrack(0, 0, leftClick);

        assertEquals(field, game.getBoard().toString());
    }

    @Test
    public void testHandleClickedTrack_4x4RaceTrack_rightClick() {
        String field = """
                1 0 0 0
                0 0 0 0
                0 0 0 0
                0 0 0 0""";
        RaceTrack game = new RaceTrack(new FakeGUI(), 4, 4);
        MouseEvent leftClick = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
        MouseEvent rightClick = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.SECONDARY, 1, true, true, true, true, true, true, true, true, true, true, null);
//        game.handleClickedOnTrack(0, 0, leftClick);

        assertEquals(field, game.getBoard().toString());

//        game.handleClickedOnTrack(0, 0, rightClick);
        field = """
                0 0 0 0
                0 0 0 0
                0 0 0 0
                0 0 0 0""";

        assertEquals(field, game.getBoard().toString());
    }

    @Test
    public void testHandleClickedTrack_4x4RaceTrack_middleClick() {
        RaceTrack game = new RaceTrack(new FakeGUI(), 4, 4);
        MouseEvent middleClick1 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 1, 0, 0, 0, MouseButton.MIDDLE, 1, true, true, true, true, true, true, true, true, true, true, null);
        MouseEvent middleClick2 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 3, 3, 0, 0, MouseButton.MIDDLE, 1, true, true, true, true, true, true, true, true, true, true, null);
//        game.handleClickedOnTrack((int) middleClick1.getX(), (int) middleClick1.getY(), middleClick1);
//        game.handleClickedOnTrack((int) middleClick2.getX(), (int) middleClick2.getY(), middleClick2);
        String field = """
                0 1 0 0
                0 0 0 0
                0 0 0 0
                0 0 0 2""";

        assertEquals(field, game.getBoard().toString());
    }

    @Test
    public void testLoad_4x4RaceTrack() {
        RaceTrack game = new RaceTrack(new FakeGUI(), 4, 4);


    }

    @Test
    public void testSave_4x4RaceTrack() {
        RaceTrack game = new RaceTrack(new FakeGUI(), 4, 4);


    }
}
