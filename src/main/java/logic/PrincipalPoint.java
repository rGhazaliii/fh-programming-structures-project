package logic;

/**
 * Logic of the principal point which can be used to represent
 * players and cells positions. Principal point is the point
 * where the player can move without any extra movement in regard
 * to its current speed.
 *
 * @author ite105705
 */
public class PrincipalPoint extends Point {

    /**
     * Constructor to create a new principal point by the input x and
     * y values.
     *
     * @param x x-axis value of the point
     * @param y y-axis value of the point
     */
    public PrincipalPoint(int x, int y) {
        super(x, y);
    }
}
