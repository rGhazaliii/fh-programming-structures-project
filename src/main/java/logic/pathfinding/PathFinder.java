package logic.pathfinding;


import logic.Board;
import logic.Cell;
import logic.Point;
import logic.list.PathList;

import java.security.InvalidParameterException;

/**
 * Class used for determining the paths on a playing field leading from one
 * point to another. In the following "node" refers to a position on the board
 * and parent refers to the node that precedes a respective node (in a path).
 *
 * @author fme, ite105705
 */
public abstract class PathFinder {

    /**
     * Returns a path from the start position to the end position on the given
     * board. Uses the flood fill algorithm (described below). Do not use the
     * same names as in the description of the algorithm, instead use
     * descriptive names. Copy the following direction as in line comments into
     * your code (you may want to split them up):
     * <p>
     * 1.1 Create an empty list as a queue Q and add the start to it. The queue
     * contains the positions that still need to be checked/visited. We start
     * with the start node: The parent node of the start node is null.
     * <p>
     * 1.2 Create a list as queue C for already checked/visited nodes and add
     * the start node to it (otherwise we might accidentally create a loop).
     * <p>
     * 2. As long as Q is not empty and no path has been found yet continue:
     * <p>
     * 3. Select the first node from Q as the node to checked (N) and remove it
     * from Q.
     * <p>
     * 4. Check all neighbours (neighbours are only vertically/horizontally
     * neighboured, never diagonally).
     * <p>
     * 4.1 If a neighbour node (NB) has not yet been checked, add it to C and
     * determine if it can be passed.
     * <p>
     * 5. If NB can be passed, then 6. set the parent node of NB to N, add NB to
     * Q and check if NB is the end of the path. If yes, a path has been found.
     * <p>
     * 6. If a path has been found: Track the respective parent node from the
     * end node back to the start node and add all nodes found along this path
     * to a list (start should be the first node in the list, and end the last
     * one)
     * <p>
     * 7. Return the list containing the path.
     *
     * @param start start position of the path on the board
     * @param end   end position of the path on the board
     * @param board playing field with cell on which a path should be
     *              determined
     * @return a path from the start to the end position
     * @throws InvalidParameterException if start or end is not on board
     */
    public abstract PathList getPathFromPosToPos(Point start, Point end, Board board);

    /**
     * Determines the path and returns it as a list (step 6 of the flood fill
     * algorithm).
     *
     * @param finalNode last/end node
     * @return path leading to the end node
     */
    public abstract PathList resolvePath(Cell finalNode);
}
