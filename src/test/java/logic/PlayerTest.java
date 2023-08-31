package logic;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class PlayerTest {

    @Test
    public void testNewPossibleDestinations_startAt3x1_speed_0x0() {
        Player player = new Player("Reza", PlayerStatus.USER, 0);
        player.setPosition(new Point(3, 1));

        assertArrayEquals(player.newPossibleDestinations(), new Point[]{
                new PrincipalPoint(3, 1),
                new Point(3, 0),
                new Point(4, 0),
                new Point(4, 1),
                new Point(4, 2),
                new Point(3, 2),
                new Point(2, 2),
                new Point(2, 1),
                new Point(2, 0),
        });
    }

    @Test
    public void testNewPossibleDestinations_startAt0x0_speed_1x1() {
        Player player = new Player(new Point(1, 1));
        player.setPosition(new Point(0, 0));

        assertArrayEquals(player.newPossibleDestinations(), new Point[]{
                new PrincipalPoint(1, 1),
                new Point(1, 0),
                new Point(2, 0),
                new Point(2, 1),
                new Point(2, 2),
                new Point(1, 2),
                new Point(0, 2),
                new Point(0, 1),
                new Point(0, 0),
        });
    }

    @Test
    public void testNewPossibleDestinations_startAt5x5_speed_1x1() {
        Player player = new Player(new Point(1, 1));
        player.setPosition(new Point(5, 5));

        assertArrayEquals(player.newPossibleDestinations(), new Point[]{
                new PrincipalPoint(6, 6),
                new Point(6, 5),
                new Point(7, 5),
                new Point(7, 6),
                new Point(7, 7),
                new Point(6, 7),
                new Point(5, 7),
                new Point(5, 6),
                new Point(5, 5),
        });
    }

    @Test
    public void testNewPossibleDestinations_startAt5x5_speed__1x_2() {
        Player player = new Player(new Point(-1, -2));
        player.setPosition(new Point(5, 5));

        assertArrayEquals(player.newPossibleDestinations(), new Point[]{
                new PrincipalPoint(4, 3),
                new Point(4, 2),
                new Point(5, 2),
                new Point(5, 3),
                new Point(5, 4),
                new Point(4, 4),
                new Point(3, 4),
                new Point(3, 3),
                new Point(3, 2),
        });
    }

    @Test
    public void testMove_startAt0x0_speed_0x0() {
        Player player = new Player(new Point(0, 0));
        player.setPosition(new Point(0, 0));
        player.move(new Point(1, 1));

        assertEquals(new Point(1, 1), player.getPosition());
        assertEquals(new Point(1, 1), player.getSpeed());
    }

    @Test
    public void testMove_startAt0x0_speed_1x0() {
        Player player = new Player(new Point(1, 0));
        player.setPosition(new Point(0, 0));
        player.move(new Point(2, 0));

        assertEquals(new Point(2, 0), player.getPosition());
        assertEquals(new Point(2, 0), player.getSpeed());
    }

    @Test
    public void testMove_startAt2x2_speed__2x_1() {
        Player player = new Player(new Point(-2, -1));
        player.setPosition(new Point(2, 2));
        player.move(new Point(0, 0));

        assertEquals(new Point(0, 0), player.getPosition());
        assertEquals(new Point(-2, -2), player.getSpeed());
    }

    @Test
    public void testMove_startAt9x0_speed_0x0() {
        Player player = new Player(new Point(0, 0));
        player.setPosition(new Point(9, 0));
        player.move(new Point(8, 0));

        assertEquals(new Point(8, 0), player.getPosition());
        assertEquals(new Point(-1, 0), player.getSpeed());

        player.setPosition(new Point(8, 0));
        player.move(new Point(7, 0));

        assertEquals(new Point(7, 0), player.getPosition());
        assertEquals(new Point(-1, 0), player.getSpeed());
    }
}
