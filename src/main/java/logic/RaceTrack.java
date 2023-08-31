package logic;

import com.google.gson.*;
import gui.JavaFxGUI;
import logic.list.PathList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Logic of the game Racetrack
 *
 * @author ite105705
 */
public class RaceTrack {

    /**
     * Reference to the board of the game.
     */
    private final Board board;

    /**
     * Players on the track.
     */
    private Player[] players;

    /**
     * Connection to the gui
     */
    private final GUIConnector gui;

    /**
     * Sets whether the game has started or not.
     */
    private boolean gameStarted;

    /**
     * If the game has ended - if it has, nothing may change
     */
    private boolean gameEnded;

    /**
     * If all the players are set on the track or not.
     */
    private boolean playersSetOnTrack;

    /**
     * Index of the current player which has the move.
     */
    private int currentPlayerIndex;

    /**
     * Flag to check whether one player has crossed the
     * start/finish line two times.
     */
    private boolean firstPlayerWon;

    /**
     * Holds the index of the players which crossed
     * the line in order.
     */
    private List<Integer> winners;

    /**
     * Maximum number of the game.
     */
    private static final int MAX_PLAYERS_NUMBER = 4;

    /**
     * Speed of the animation.
     */
    private static int speed = 1;

    /**
     * Minimum value for the animation speed.
     */
    private static final double MIN_SPEED_LEVEL = 1;

    /**
     * Maximum value for the animation speed.
     */
    private static final double MAX_SPEED_LEVEL = 3;

    /**
     * Creates a new game of racetrack.
     *
     * @param gui      the gui to display on
     * @param colCount count of columns of field
     * @param rowCount count of rows of field
     */
    public RaceTrack(GUIConnector gui, int colCount, int rowCount) {
        this.gui = gui;
        this.board = new Board(colCount, rowCount);
        this.currentPlayerIndex = 0;
    }

    /**
     * Creates a new game of racetrack.
     *
     * @param gui         the gui to display on
     * @param board       board of the game
     * @param boardUpdate type of the update to the board
     */
    public RaceTrack(GUIConnector gui, Board board, BoardUpdate boardUpdate) {
        this.gui = gui;
        this.currentPlayerIndex = 0;
        this.board = board;

        if (boardUpdate != BoardUpdate.NONE) {
            switch (boardUpdate) {
                case ROW_ADD_TOP -> this.board.addRowTop();
                case ROW_ADD_BOTTOM -> this.board.addRowBottom();
                case COL_ADD_LEFT -> this.board.addColLeft();
                case COL_ADD_RIGHT -> this.board.addColRight();
                case ROW_REMOVE_TOP -> this.board.removeRowTop();
                case ROW_REMOVE_BOTTOM -> this.board.removeRowBottom();
                case COL_REMOVE_LEFT -> this.board.removeColLeft();
                case COL_REMOVE_RIGHT -> this.board.removeColRight();
            }
        }
    }

    /**
     * Creates a new game of racetrack.
     *
     * @param gui   the gui to display on
     * @param board board of the game
     */
    public RaceTrack(GUIConnector gui, Board board, Player[] players) {
        this.gui = gui;
        this.board = board;
        this.currentPlayerIndex = 0;
        this.players = players;
        this.winners = new ArrayList<>();
    }

    /**
     * Increases the animation speed by one.
     */
    public static void increaseSpeed() {
        if (speed < MAX_SPEED_LEVEL) {
            speed++;
        }
    }

    /**
     * Decreases animation speed by one.
     */
    public static void decreaseSpeed() {
        if (speed > MIN_SPEED_LEVEL) {
            speed--;
        }
    }

    /**
     * Getter method of the animation speed.
     *
     * @return replay speed.
     */
    public static int getSpeed() {
        return speed;
    }

    /**
     * Getter method of the racetrack board.
     *
     * @return board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Getter method of the board players.
     *
     * @return players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the current player of the game.
     *
     * @return the current player
     */
    private Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
    }

    /**
     * Setter method of the racetrack current player
     * index.
     *
     * @param index the given index as input
     */
    public void setCurrentPlayerIndex(int index) {
        this.currentPlayerIndex = index;
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a track cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoTrack(Point point) {
        if (!this.gameEnded) {
            this.board.turnIntoTrack(point);
            if (this.board.isTrack(point)) {
                this.gui.turnIntoTrack(point);
            }
        }
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a gravel cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoGravel(Point point) {
        if (!this.gameEnded) {
            this.board.turnIntoGravel(point);
            if (this.board.isGravel(point)) {
                this.gui.turnIntoGravel(point);
            }
        }
    }

    /**
     * Called if the user wants to change a cell turn in to
     * a line cell.
     *
     * @param point point of the cell to turn into track
     */
    public void turnIntoLine(Point point) {
        if (!this.gameEnded) {
            this.board.turnIntoLine(point);
            this.gui.drawLine(this.board.getStartAndFinishLine());
        }
    }

    /**
     * Reverses the line direction.
     */
    public void reverseLine() {
        this.gui.reverseLine(this.board.getStartAndFinishLine());
        this.board.getStartAndFinishLine().toggleDirection();
    }

    /**
     * Returns the json string of the current racetrack.
     *
     * @return string
     */
    public String save() {
        int[][] cells = new int[this.board.getCells().length][this.board.getCells()[0].length];
        for (int row = 0; row < this.board.getCells().length; row++) {
            for (int col = 0; col < this.board.getCells()[row].length; col++) {
                cells[col][row] = Integer.parseInt(this.board.getCells()[row][col].toString());
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Object> data = new HashMap<>();
        data.put("track", cells);
        data.put("direction", Integer.parseInt(this.board.getStartAndFinishLine().toString()));
        JsonObject[] playersData = new JsonObject[MAX_PLAYERS_NUMBER];
        int numOfActivePlayers;
        for (numOfActivePlayers = 0;
             this.players != null && numOfActivePlayers < this.players.length; numOfActivePlayers++
        ) {
            boolean isActive;
            isActive = Objects.requireNonNull(this.players[numOfActivePlayers].getStatus()) == PlayerStatus.INACTIVE;

            boolean isAI;
            isAI = Objects.requireNonNull(this.players[numOfActivePlayers].getStatus()) == PlayerStatus.AI;

            JsonObject result = new JsonObject();
            result.add("active", new JsonPrimitive(isActive));
            result.add("name", new JsonPrimitive(this.players[numOfActivePlayers].getName()));
            result.add("ai", new JsonPrimitive(isAI));

            JsonArray positionArray = new JsonArray(2);
            positionArray.add(this.players[numOfActivePlayers].getPosition().x());
            positionArray.add(this.players[numOfActivePlayers].getPosition().y());
            result.add("current", positionArray);

            JsonArray lastPositionArray = new JsonArray(2);
            lastPositionArray.add(this.players[numOfActivePlayers].getLastPosition().x());
            lastPositionArray.add(this.players[numOfActivePlayers].getLastPosition().y());
            result.add("last", positionArray);
            result.add("lap", new JsonPrimitive(this.players[numOfActivePlayers].getCrossedLine()));

            playersData[numOfActivePlayers] = result;
        }

        while (numOfActivePlayers != MAX_PLAYERS_NUMBER) {
            JsonObject result = new JsonObject();
            result.add("active", new JsonPrimitive(false));
            result.add("name", new JsonPrimitive(""));
            result.add("ai", new JsonPrimitive(false));

            JsonArray positionArray = new JsonArray(2);
            positionArray.add(0);
            positionArray.add(0);
            result.add("current", positionArray);

            JsonArray lastPositionArray = new JsonArray(2);
            lastPositionArray.add(0);
            lastPositionArray.add(0);
            result.add("last", positionArray);
            result.add("lap", new JsonPrimitive(0));

            playersData[numOfActivePlayers] = result;
            numOfActivePlayers++;
        }

        data.put("player", playersData);
        data.put("currentPlayer", this.currentPlayerIndex);

        return gson.toJson(data);
    }

    /**
     * Checks whether the game is already started
     * or not.
     *
     * @return whether the game is already started
     * or not
     */
    public boolean isGameStarted() {
        return this.gameStarted;
    }

    /**
     * Checks whether the game is already ended
     * or not.
     *
     * @return whether the game is already ended
     * or not
     */
    public boolean isGameEnded() {
        return this.gameEnded;
    }

    /**
     * Checks whether the placement of the players
     * is ended or not.
     *
     * @return whether the placement of the players
     * is ended or not
     */
    public boolean isPlacementEnded() {
        return this.playersSetOnTrack;
    }

    /**
     * Sets the started flag to true if there is
     * a valid path on the board and shows an appropriate
     * message, otherwise an error message is shown.
     */
    public void startGame() {
        if (this.board.hasValidPath()) {
            this.gameStarted = true;
            this.gui.showMessage(JavaFxGUI.BOARD_PREPARATION_TITLE, JavaFxGUI.BOARD_CARS_PLACEMENT_MESSAGE);
            this.placeAIPlayerOnBoard();
        } else {
            this.gui.showMessage(JavaFxGUI.BOARD_PREPARATION_TITLE, JavaFxGUI.BOARD_INVALID_MESSAGE);
        }
    }

    /**
     * Places AI player on the board.
     */
    private void placeAIPlayerOnBoard() {
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer.isAI() && !currentPlayer.isPlaced()) {
            Point point = null;
            PathList track = this.board.getTrack();
            int size = track.size();
            for (int i = 0; i < size && point == null; i++) {
                Cell cell = track.getAt(i);

                if (cell.isTrack()) {
                    point = cell.getPosition();
                }
            }

            this.setPlayerPosition(point);
            this.updateInfoOnPlacement();
            this.placeAIPlayerOnBoard();
        }
    }

    /**
     * Places the current player of the game on the given
     * point as input. If the point is on the start/finish
     * line or there is already another player on the point
     * or the selected point is not a track cell an appropriate
     * message would be shown.
     *
     * @param point the given point
     */
    public void placePlayerOnBoard(Point point) {
        if (this.board.getStartAndFinishLine().isOnLine(point)) {
            this.gui.showMessage(JavaFxGUI.BOARD_PREPARATION_TITLE, JavaFxGUI.BOARD_CARS_ON_LINE_MESSAGE);
        } else if (this.board.isCar(point)) {
            this.gui.showMessage(JavaFxGUI.OCCUPIED_BY_CAR_TITLE, JavaFxGUI.OCCUPIED_BY_CAR_MESSAGE);
        } else if (!this.board.isTrack(point)) {
            this.gui.showMessage(JavaFxGUI.BOARD_PREPARATION_TITLE, JavaFxGUI.BOARD_CARS_PLACEMENT_INVALID);
        } else {
            Player currentPlayer = this.getCurrentPlayer();

            if (currentPlayer != null && !currentPlayer.isPlaced()) {
                this.setPlayerPosition(point);
                this.updateInfoOnPlacement();
            }
        }

        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer.isAI()) {
            this.placeAIPlayerOnBoard();
        }
    }

    /**
     * If the last player was placed on the board shows the
     * finishing positions of the first player to move, otherwise
     * increments the players index to next player.
     */
    private void updateInfoOnPlacement() {
        if (this.currentPlayerIndex == this.players.length - 1) {
            this.playersSetOnTrack = true;
            this.currentPlayerIndex = 0;
            Player currentPlayer = this.getCurrentPlayer();

            this.gui.showMessage(JavaFxGUI.GAME_STARTED_TITLE, JavaFxGUI.BOARD_CARS_ARE_SET);
            this.gui.updatePlayersInfoLabel(this.players, this.currentPlayerIndex);

            this.showFinishingPosition(currentPlayer);
            if (currentPlayer.isAI()) {
                this.moveAI();
            }
        } else {
            this.currentPlayerIndex++;
        }
    }

    /**
     * Sets the position of the current player to the
     * given point.
     *
     * @param point the given point
     */
    public void setPlayerPosition(Point point) {
        Player currentPlayer = this.getCurrentPlayer();
        if (currentPlayer.isPlaced()) { // It's a new position
            currentPlayer.setLastPosition(currentPlayer.getPosition());
        } else { // It's the initial placement
            currentPlayer.setLastPosition(point);
        }

        currentPlayer.setPlayerStartingDirection(this.board.getStartAndFinishLine().getDirection());
        currentPlayer.setPosition(point);
        currentPlayer.addMove(point);
        this.board.turnIntoCar(point);

        this.gui.setPlayerOnPosition(point, this.currentPlayerIndex);
    }

    /**
     * Shows possible finishing moves of the given player
     * on the board.
     *
     * @param player the given player
     */
    private void showFinishingPosition(Player player) {
        Point[] points = player.newPossibleDestinations();
        Point[] validPoints = this.getValidPoints(points);
        if (validPoints.length == 0) {
            this.gui.showMessage(JavaFxGUI.NO_POSSIBLE_MOVE_TITLE, JavaFxGUI.NO_POSSIBLE_MOVE_MESSAGE);
            // Shows the next player possible moves
            player.updatePositionOnCrash();
            Player nextPlayer = this.goToNextPlayer();

            if (nextPlayer.isAI() && nextPlayer.isPlaced()) {
                this.moveAI();
            } else {
                this.showFinishingPosition(nextPlayer);
            }
        } else {
            this.gui.showFinishingPosition(player, validPoints);
        }
    }

    /**
     * Separates valid points from an array of input points.
     * Valid points are the points which have valid boundaries
     * and there is no other car on the point already available.
     *
     * @param points the given points
     * @return and array containing only the valid points of
     * the input points
     */
    private Point[] getValidPoints(Point[] points) {
        int count = 0;
        Player currentPlayer = this.getCurrentPlayer();
        for (Point point : points) {
            if ((this.board.areValidCoords(point) &&
                 !(this.board.isCar(point) && !currentPlayer.getPosition().equals(point))) ||
                currentPlayer.getPosition().equals(point)
            ) {
                count++;
            }
        }

        Point[] validPoints = new Point[count];
        for (int i = 0, j = 0; i < points.length; i++) {
            if ((this.board.areValidCoords(points[i]) &&
                 !(this.board.isCar(points[i]) && !currentPlayer.getPosition().equals(points[i]))) ||
                currentPlayer.getPosition().equals(points[i])
            ) {
                validPoints[j] = points[i];
                j++;
            }
        }

        return validPoints;
    }

    /**
     * Moves the current player to the given point
     * as input and updates the logic and the gui.
     *
     * @param point the given input point
     */
    public void move(Point point) {
        Player currentPlayer = this.getCurrentPlayer();
        Point[] possibleDestinations = currentPlayer.newPossibleDestinations();
        boolean isValidMove = this.validMove(possibleDestinations, point);
        if (isValidMove) {
            this.updateGuiOrPlayerAfterMove(point);
            this.checkGameEnded();

            // Shows the next player possible moves
            Player nextPlayer = this.goToNextPlayer();
            this.showFinishingPosition(nextPlayer);

            this.gui.updatePlayersInfoLabel(this.players, this.currentPlayerIndex);

            if (nextPlayer.isAI()) {
                this.moveAI();
            }
        } else {
            this.gui.showMessage(JavaFxGUI.INVALID_MOVE_TITLE, JavaFxGUI.INVALID_MOVE_MESSAGE);
        }
    }

    /**
     * Updates the last position and the current position
     * cells of the player in the gui if the given point
     * is not gravel, otherwise resets the player speed
     * vector to (0, 0).
     *
     * @param point the given point to move into
     */
    private void updateGuiOrPlayerAfterMove(Point point) {
        Player currentPlayer = this.getCurrentPlayer();

        if (!this.board.isGravel(point)) {
            currentPlayer.move(point);
            this.board.turnIntoTrack(currentPlayer.getLastPosition());
            this.board.turnIntoCar(point);
            this.gui.move(currentPlayer, point);
        } else if (this.board.isGravel(point)) {
            // Resets the speed and the positions of the current player
            currentPlayer.updatePositionOnCrash();
        }
    }

    /**
     * Moves the current player to the closest point
     * to the next track cell and updates the logic
     * and the gui.
     */
    private void moveAI() {
        Player currentPlayer = this.getCurrentPlayer();
        Point startPoint = this.findAIStartingPoint();
        Point[] possibleDestinations = currentPlayer.newPossibleDestinations();
        boolean isValidMove = this.validMove(possibleDestinations, startPoint);
        Point newStartPoint;
        if (!isValidMove) {
            newStartPoint = this.findAIOptimalMove(possibleDestinations);

            if (newStartPoint != null) {
                startPoint = newStartPoint;
            }
        }

        this.updateGuiOrPlayerAfterMove(startPoint);
        this.checkGameEnded();

        // Shows the next player possible moves
        Player nextPlayer = this.goToNextPlayer();
        this.showFinishingPosition(nextPlayer);

        this.gui.updatePlayersInfoLabel(this.players, this.currentPlayerIndex);

        if (nextPlayer.isAI()) {
            this.moveAI();
        }
    }

    /**
     * Finds the nearest point to the current postion of the
     * AI player based on the given destinations.
     *
     * @param possibleDestinations the given destinations
     * @return the nearest point
     */
    private Point findAIOptimalMove(Point[] possibleDestinations) {
        double minDistance = Double.MAX_VALUE;
        Player currentPlayer = this.getCurrentPlayer();
        Point currentPosition = currentPlayer.getPosition();
        Point[] forwardPoints = currentPlayer.findForwardingPoints(possibleDestinations);
        Point newStartPoint = null;
        for (Point possibleDestination : forwardPoints) {
            double dist = Point.calculateDistance(currentPosition, possibleDestination);
            boolean isValidMove = this.validMove(possibleDestinations, possibleDestination);
            if (!currentPosition.equals(possibleDestination) && dist < minDistance && isValidMove) {
                minDistance = dist;
                newStartPoint = possibleDestination;
            }
        }

        return newStartPoint;
    }

    /**
     * Finds the starting point of the AI player based on the
     * track line direction.
     *
     * @return the starting point
     */
    private Point findAIStartingPoint() {
        double minDistance = Double.MAX_VALUE;
        int trackSize = this.board.getTrack().size();
        Player currentPlayer = this.getCurrentPlayer();
        Point playerCurrentPosition = currentPlayer.getPosition();
        Point startPoint = null;
        for (int i = 0; i < trackSize; i++) {
            Point currentPointToCheck = this.board.getTrack().getAt(i).getPosition();
            int xDiff = currentPointToCheck.x() - playerCurrentPosition.x();
            int yDiff = currentPointToCheck.y() - playerCurrentPosition.y();
            if ((currentPlayer.getMovementDirection() == MovementDirection.RIGHT_UP && xDiff >= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.RIGHT && xDiff >= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.RIGHT_DOWN && xDiff >= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.DOWN && yDiff >= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.LEFT_DOWN && yDiff >= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.LEFT && xDiff <= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.LEFT_UP && xDiff <= 0) ||
                (currentPlayer.getMovementDirection() == MovementDirection.UP && yDiff <= 0)) {

                double dist = Point.calculateDistance(playerCurrentPosition, currentPointToCheck);
                if (!playerCurrentPosition.equals(currentPointToCheck) && dist < minDistance) {
                    minDistance = dist;
                    startPoint = currentPointToCheck;
                }
            }
        }

        return startPoint;
    }

    /**
     * Checks whether the game is ended and if yes,
     * shows the appropriate message. Game is ended
     * when one player passes the line and all the next
     * players in the round also finish their turn.
     */
    private void checkGameEnded() {
        Player currentPlayer = this.getCurrentPlayer();

        if (this.lineIsCrossed() && !currentPlayer.isActive()) {
            if (!this.firstPlayerWon) {
                this.firstPlayerWon = true;
            }

            this.winners.add(currentPlayer.getIndex());
        }
        if (this.firstPlayerWon && (this.currentPlayerIndex == this.players.length - 1)) {
            this.gameEnded = true;
            this.updateFinalWinners();
            this.gui.replayMenuItemDisabled(false);
            this.gui.displayFinalStatus(this.players);
        }
    }

    /**
     * Checks all the winners' position to the start/finish
     * line and sets the winner flag of that player to true.
     */
    private void updateFinalWinners() {
        int minDistance = 0;
        for (Integer winner : this.winners) {
            Point currentPlayerPosition = this.players[winner].getPosition();
            int currentDistance = this.board.getStartAndFinishLine().getDistanceFromPoint(currentPlayerPosition);
            if (currentDistance <= minDistance) {
                minDistance = currentDistance;
            }
        }

        for (Integer winner : this.winners) {
            Point currentPlayerPosition = this.players[winner].getPosition();
            int currentDistance = this.board.getStartAndFinishLine().getDistanceFromPoint(currentPlayerPosition);
            if (currentDistance == minDistance) {
                this.players[winner].setIsWinner(true);
            }
        }
    }

    /**
     * Checks whether a point is a valid move based on
     * the given possible destinations given as another
     * input array.
     *
     * @param possibleDestinations input array containing possible moves
     *                             from the current source
     * @param point                the given input point
     * @return whether the given point is valid or not
     */
    private boolean validMove(Point[] possibleDestinations, Point point) {
        boolean isValid = false;
        Point[] validPoints = this.getValidPoints(possibleDestinations);
        if (point != null) {
            for (Point validPoint : validPoints) {
                if (point.equals(validPoint)) {
                    isValid = true;
                    break;
                }
            }
        }

        return isValid;
    }

    /**
     * Removes the currently shown highlighted cells on the board
     * and changes the current player to the next one.
     *
     * @return the new current player
     */
    private Player goToNextPlayer() {
        Player currentPlayer = this.getCurrentPlayer();
        this.gui.turnIntoBeforeShowingFinishingStates(currentPlayer, this.players, this.board.getStartAndFinishLine());
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.length;

        return this.getCurrentPlayer();
    }

    /**
     * Returns whether the current player has
     * crossed the start/finish line or not, and
     * increments the number of its crossings.
     *
     * @return whether the current player has
     * crossed the start/finish line
     */
    private boolean lineIsCrossed() {
        Player currentPlayer = this.getCurrentPlayer();
        TrackLine line = this.board.getStartAndFinishLine();
        boolean isCrossed = false;

        switch (line.getDirection()) {
            case LEFT_RIGHT -> {
                if (currentPlayer.getLastPosition().x() <= line.getStartPoint().x() &&
                    currentPlayer.getPosition().x() > line.getStartPoint().x()) {
                    isCrossed = true;
                }
            }
            case RIGHT_LEFT -> {
                if (currentPlayer.getLastPosition().x() >= line.getStartPoint().x() &&
                    currentPlayer.getPosition().x() < line.getStartPoint().x()) {
                    isCrossed = true;
                }
            }
            case TOP_DOWN -> {
                if (currentPlayer.getLastPosition().y() <= line.getStartPoint().y() &&
                    currentPlayer.getPosition().y() > line.getStartPoint().y()) {
                    isCrossed = true;
                }
            }
            case DOWN_TOP -> {
                if (currentPlayer.getLastPosition().y() >= line.getStartPoint().y() &&
                    currentPlayer.getPosition().y() > line.getStartPoint().y()) {
                    isCrossed = true;
                }
            }
        }

        boolean crossedInCorrectDirection = currentPlayer.getMoves().size() >= this.board.getTrack().size();
        if (crossedInCorrectDirection && isCrossed) {
            currentPlayer.incrementCrossedLine();
        }

        return crossedInCorrectDirection && isCrossed;
    }
}
