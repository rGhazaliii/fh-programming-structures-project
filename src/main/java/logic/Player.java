package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic of the players of the game.
 *
 * @author ite105705
 */
public class Player {

    /**
     * String which represents the name of the player.
     */
    private final String name;

    /**
     * Enum which represents the status of the player.
     */
    private final PlayerStatus status;

    /**
     * Point which represents the position of the player.
     */
    private Point position;

    /**
     * Point which represents the last position of the player.
     */
    private Point lastPosition;

    /**
     * Point which represents the speed of the player.
     */
    private Point speed;

    /**
     * The number of times that the player has crossed
     * the line of the game.
     */
    private int crossedLine;

    /**
     * Index of the player between other players of the
     * game.
     */
    private int index;

    /**
     * Whether the player's car is placed on the board
     * yet or not.
     */
    private boolean isPlaced;

    /**
     * An array which holds the track of all the moves of
     * the player by keeping the points of the moves.
     */
    private final ArrayList<Point> moves;

    /**
     * The flag which shows whether the player has
     * won the game or not.
     */
    private boolean isWinner;

    private MovementDirection movementDirection;

    /**
     * Creates a new player.
     *
     * @param name   the given name
     * @param status the status of the player
     * @param index  the index of the player
     */
    public Player(String name, PlayerStatus status, int index) {
        this.name = name;
        this.status = status;
        this.index = index;
        this.moves = new ArrayList<>();
        this.speed = new Point(0, 0);
    }

    /**
     * Creates a new player. The default value of
     * the player's name is empty string and the
     * default value of the player's status is AI.
     *
     * @param speed the speed vector of the player
     */
    Player(Point speed) {
        this.name = "";
        this.status = PlayerStatus.AI;
        this.moves = new ArrayList<>();
        this.speed = speed;
    }

    /**
     * Getter of the player's name.
     *
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the player's status.
     *
     * @return status of the player
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * Getter of the player's position.
     *
     * @return position of the player
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Setter of the player's position.
     *
     * @param point input point
     */
    public void setPosition(Point point) {
        if (point != null) {
            this.position = point;
            this.isPlaced = true;
        }
    }

    /**
     * Getter of the player's last position.
     *
     * @return last position of the player
     */
    public Point getLastPosition() {
        return this.lastPosition;
    }

    /**
     * Setter of the player's position.
     *
     * @param point input point
     */
    public void setLastPosition(Point point) {
        if (point != null) {
            this.lastPosition = point;
        }
    }

    /**
     * Getter of the player's speed.
     *
     * @return speed of the player
     */
    public Point getSpeed() {
        return this.speed;
    }

    /**
     * Getter of the player's number of crossing of the line.
     *
     * @return number of times the player crossed
     * the line
     */
    public int getCrossedLine() {
        return this.crossedLine;
    }

    /**
     * Setter of the player's number of crossing of the line.
     *
     * @param crossedLine the given input
     */
    public void setCrossedLine(int crossedLine) {
        this.crossedLine = crossedLine;
    }

    /**
     * Increments the number of the times the player
     * has crossed the start/finish line.
     */
    public void incrementCrossedLine() {
        this.crossedLine++;
    }

    /**
     * Returns whether the player is placed on the
     * track or not.
     *
     * @return whether the player is placed on the
     * track or not
     */
    public boolean isPlaced() {
        return this.isPlaced;
    }

    /**
     * Getter method of the index player's index.
     *
     * @return index of the player
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Getter method of the player's moves.
     *
     * @return player's moves
     */
    public ArrayList<Point> getMoves() {
        return this.moves;
    }

    /**
     * Adds a move to the list of the player's moves.
     *
     * @param point input point
     */
    public void addMove(Point point) {
        this.moves.add(point);
    }

    /**
     * Returns the possible destinations of the player
     * based on its current position and speed.
     *
     * @return possible destinations of the player
     */
    public Point[] newPossibleDestinations() {
        int newX = this.position.x() + this.speed.x();
        int newY = this.position.y() + this.speed.y();

        return new Point[]{
                new PrincipalPoint(newX, newY),
                new Point(newX, newY - 1),
                new Point(newX + 1, newY - 1),
                new Point(newX + 1, newY),
                new Point(newX + 1, newY + 1),
                new Point(newX, newY + 1),
                new Point(newX - 1, newY + 1),
                new Point(newX - 1, newY),
                new Point(newX - 1, newY - 1)
        };
    }

    /**
     * Filters the points that move the player at least
     * forward and not back from the given points.
     *
     * @param destinations the given points
     * @return filtered points that move the player at
     * least not backward
     */
    public Point[] findForwardingPoints(Point[] destinations) {
        List<Point> pointsList = new ArrayList<>();
        for (Point destination : destinations) {
            int xDiff = destination.x() - this.position.x();
            int yDiff = destination.y() - this.position.y();
            if ((this.movementDirection == MovementDirection.RIGHT_UP && xDiff >= 0) ||
                (this.movementDirection == MovementDirection.RIGHT && xDiff >= 0) ||
                (this.movementDirection == MovementDirection.RIGHT_DOWN && xDiff >= 0) ||
                (this.movementDirection == MovementDirection.DOWN && yDiff >= 0) ||
                (this.movementDirection == MovementDirection.LEFT_DOWN && yDiff >= 0) ||
                (this.movementDirection == MovementDirection.LEFT && xDiff <= 0) ||
                (this.movementDirection == MovementDirection.LEFT_UP && xDiff <= 0) ||
                (this.movementDirection == MovementDirection.UP && yDiff <= 0)) {
                pointsList.add(destination);
            }
        }

        Point[] points = new Point[pointsList.size()];
        for (int i = 0; i < points.length; i++) {
            points[i] = pointsList.get(i);
        }

        return points;
    }

    /**
     * Moves the player to the new given point position
     * and updates its speed based on its current position
     * and last position.
     *
     * @param point input point
     */
    public void move(Point point) {
        this.lastPosition = this.position;
        this.position = point;
        this.moves.add(this.position);

        int newSpeedX = this.position.x() - this.lastPosition.x();
        int newSpeedY = this.position.y() - this.lastPosition.y();
        int newSpeedYDiff = newSpeedY - this.speed.y();
        int newSpeedXDiff = newSpeedX - this.speed.x();

        this.speed = new Point(this.speed.x() + newSpeedXDiff, this.speed.y() + newSpeedYDiff);
        this.updateDirection();
    }

    /**
     * Updates the direction of the player based on
     * the new position.
     */
    private void updateDirection() {
        int yDiff = this.getPosition().y() - this.getLastPosition().y();
        int xDiff = this.getPosition().x() - this.getLastPosition().x();

        if (xDiff > 0 && yDiff < 0) {
            this.setMovementDirection(MovementDirection.RIGHT_UP);
        } else if (xDiff > 0 && yDiff == 0) {
            this.setMovementDirection(MovementDirection.RIGHT);
        } else if (xDiff > 0) {
            this.setMovementDirection(MovementDirection.RIGHT_DOWN);
        } else if (xDiff == 0 && yDiff > 0) {
            this.setMovementDirection(MovementDirection.DOWN);
        } else if (xDiff < 0 && yDiff > 0) {
            this.setMovementDirection(MovementDirection.LEFT_DOWN);
        } else if (xDiff < 0 && yDiff == 0) {
            this.setMovementDirection(MovementDirection.LEFT);
        } else if (xDiff < 0) {
            this.setMovementDirection(MovementDirection.LEFT_UP);
        } else if (yDiff < 0) {
            this.setMovementDirection(MovementDirection.UP);
        }
    }

    /**
     * Sets the last position of the player before
     * the gravel as the current position and sets
     * player's speed to zero.
     */
    public void updatePositionOnCrash() {
        int lastMoveIndex = this.moves.size() - 1;
        this.position = this.moves.get(lastMoveIndex);
        this.lastPosition = this.position;
        this.speed = new Point(0, 0);
    }

    /**
     * Checks whether the player is active or not.
     * An active player is a player who has not
     * crossed the start/finish line more than one
     * time.
     *
     * @return whether the player has not crossed
     * the start/finish line more than one time
     */
    public boolean isActive() {
        return this.crossedLine < 1;
    }

    /**
     * Setter of the player's isWinner flag.
     *
     * @param isWinner flag value
     */
    public void setIsWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    /**
     * Getter of the player's isWinner flag.
     *
     * @return whether the player is winner or not
     */
    public boolean isWinner() {
        return this.isWinner;
    }

    /**
     * Returns the {@link PlayerStatus} enum based on the
     * given values. If the player is only active it should
     * be a USER. If it is active and AI it is an AI, otherwise
     * it is an INACTIVE player.
     *
     * @param isActive if the player is active
     * @param isAI     if the player is ai
     * @return the {@link PlayerStatus} enum based on the
     * given values
     */
    public static PlayerStatus toPlayerStatus(boolean isActive, boolean isAI) {
        if (isActive && isAI) {
            return PlayerStatus.AI;
        } else if (isActive) {
            return PlayerStatus.USER;
        }

        return PlayerStatus.INACTIVE;
    }

    /**
     * Sets the player direction based on the track
     * direction.
     *
     * @param lineDirection the given track direction
     */
    public void setPlayerStartingDirection(LineDirection lineDirection) {
        switch (lineDirection) {
            case LEFT_RIGHT -> this.setMovementDirection(MovementDirection.RIGHT);
            case RIGHT_LEFT -> this.setMovementDirection(MovementDirection.LEFT);
            case TOP_DOWN -> this.setMovementDirection(MovementDirection.DOWN);
            case DOWN_TOP -> this.setMovementDirection(MovementDirection.UP);
        }
    }

    /**
     * Getter method of the movement direction of the
     * player.
     *
     * @return movement direction of the player
     */
    public MovementDirection getMovementDirection() {
        return this.movementDirection;
    }

    /**
     * Setter method of the movement direction of the
     * player.
     *
     * @param movementDirection the given movement direction
     */
    public void setMovementDirection(MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }

    /**
     * Checks whether the player is an AI player or not.
     *
     * @return whether the player is an AI player or not
     */
    public boolean isAI() {
        return this.status == PlayerStatus.AI;
    }
}
