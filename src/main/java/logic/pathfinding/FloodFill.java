package logic.pathfinding;

import logic.Board;
import logic.Cell;
import logic.CellState;
import logic.Point;
import logic.list.PathList;

import java.security.InvalidParameterException;

/**
 * Class used for determining the paths on a playing field leading from one
 * point to another by the use of the flood fill algorithm.
 *
 * @author fme, ite105705
 */
public class FloodFill extends PathFinder {

    @Override
    public PathList getPathFromPosToPos(Point start, Point end, Board board) {
        // Checks the validity of the position start and end
        if (!board.areValidCoords(start) || !board.areValidCoords(end)) {
            throw new InvalidParameterException("Invalid start or end position");
        }

        /* 1.1
        Creates an empty list as a queue needToBeVisited and adds the start to it. The queue
        contains the positions that still need to be checked/visited. We start
        with the start node: The parent node of the start node is null.
         */
        Cell startCell = board.getCellAtPosition(start);
        startCell.setParentCell(null);
        PathList needToBeVisited = new PathList();
        needToBeVisited.insertAtFront(startCell);

        /* 1.2
        Creates a list as queue alreadyVisited for already checked/visited nodes and adds
        the start node to it (otherwise we might accidentally create a loop).
         */
        PathList alreadyVisited = new PathList();
        alreadyVisited.insertAtFront(startCell);

        // List to hold the found path
        PathList foundPath = new PathList();
        boolean pathFound = false;

        // 2. As long as needToBeVisited is not empty and no path has been found yet continues
        while (!needToBeVisited.isEmpty() && !pathFound) {
            /* 3.
            Selects the first node from needToBeVisited as the node to checked (cellToBeChecked)
            and removes it from needToBeVisited
             */
            Cell cellToBeChecked = needToBeVisited.getFirstElement();
            needToBeVisited.removeAtFirst();

            if (cellToBeChecked.isTrack()) { // We cannot move from CANYON cells
                // Gets the neighbours of cellToBeChecked
                Cell[] neighbours = board.getNeighbours(cellToBeChecked.getPosition());

                /* 4.
                Checks all neighbours (neighbours are only vertically/horizontally
                neighboured, never diagonally).
                 */
                for (Cell currentNeighbour : neighbours) {
                    /* 4.1
                    If a neighbour node (currentNeighbour) has not yet been checked, adds it to alreadyVisited
                    and determine if it can be passed.
                     */
                    if (!alreadyVisited.contains(currentNeighbour)) {
                        alreadyVisited.append(currentNeighbour);

                        /* 5.
                        If currentNeighbour can be passed, then 6. Sets the parent node of currentNeighbour
                        to cellToBeChecked, adds currentNeighbour to needToBeVisited and check if currentNeighbour
                        is the end of the path. If yes, a path has been found.
                         */
                        currentNeighbour.setParentCell(cellToBeChecked);
                        needToBeVisited.append(currentNeighbour);

                        // 6
                        // If currentNeighbour can be passed and a path has been found
                        // currentNeighbour is the end of the path
                        if (currentNeighbour.isTrack() && currentNeighbour.getPosition().equals(end)) {
                            foundPath = this.resolvePath(currentNeighbour);
                            pathFound = true;
                        }
                    }
                }
            }
        }

        // In case of path to one element itself
        PathList list = this.checkPositions(start, end, board);
        foundPath.append(list.isEmpty() ? null : list.getFirstElement());

        return foundPath;
    }

    public PathList resolvePath(Cell finalNode) {
        PathList result = new PathList();

        Cell currentNode = finalNode;

        while (currentNode != null) {
            result.insertAtFront(currentNode);
            currentNode = currentNode.getParentCell();
        }

        return result;
    }

    /**
     * Checks if the start and end position are equal, in case of two swamp return one
     * path list containing one swamp cell, otherwise returns empty path list
     *
     * @param start the given start position
     * @param end   the given end position
     * @param board the given board
     * @return PathList
     */
    private PathList checkPositions(Point start, Point end, Board board) {
        PathList pathList = new PathList();
        if (board.areValidCoords(start) && board.areValidCoords(end) && start.equals(end)
            && board.getCellAtPosition(start).getCurrentState() == CellState.TRACK) {
            pathList.insertAtFront(board.getCellAtPosition(start));

            return pathList;
        }

        return pathList;
    }
}
