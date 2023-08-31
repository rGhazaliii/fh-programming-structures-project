package logic;

import javafx.scene.control.Label;

/**
 * Connection of logic to gui for Racetrack program.
 *
 * @author ite105705
 */
public interface GUIConnector {

    /**
     * Setter method of the animation label.
     *
     * @param lblAnimationCurrentSpeed the given label
     */
    void setLblAnimationCurrentSpeed(Label lblAnimationCurrentSpeed);

    /**
     * Setter method of the replay label.
     *
     * @param lblReplayCurrentSpeed the given label
     */

    void setLblReplayCurrentSpeed(Label lblReplayCurrentSpeed);

    /**
     * Updates replay label to the given value.
     *
     * @param value the given value
     */

    void updateReplayCurrentSpeedLabel(int value);

    /**
     * Updates animation label to the given value.
     *
     * @param value the given value
     */
    void updateAnimationCurrentSpeedLabel(int value);

    /**
     * Marks cell at the specified coordinate as gravel.
     *
     * @param point point of the cell to turn into track
     */
    void turnIntoGravel(Point point);

    /**
     * Marks cell at the specified coordinate as track.
     *
     * @param point point of the cell to turn into track
     */
    void turnIntoTrack(Point point);

    /**
     * Marks cell at the specified coordinate as line.
     *
     * @param point     point of the cell to turn into track
     * @param direction direction of the line
     */
    void turnIntoLine(Point point, LineDirection direction);

    /**
     * Draws a line based on the given line.
     *
     * @param trackLine the given line
     */
    void drawLine(TrackLine trackLine);

    /**
     * Removes the current line.
     *
     * @param trackLine the given line
     */
    void removeLine(TrackLine trackLine);

    /**
     * Reverses the line direction.
     *
     * @param trackLine the given line
     */
    void reverseLine(TrackLine trackLine);

    /**
     * Turns the current line on the track field into normal
     * track cells instead of line cells.
     */
    void turnLineIntoTrack(TrackLine trackLine);

    /**
     * Reverses the direction of the cell.
     *
     * @param point            point of the cell to turn into track
     * @param currentDirection current direction of the line
     */
    void reverseDirection(Point point, LineDirection currentDirection);

    /**
     * Shows a dialog message.
     *
     * @param title   the given title
     * @param message the given message
     */
    void showMessage(String title, String message);

    /**
     * Shows a confirm dialog message.
     *
     * @param title   the given title
     * @param message the given message
     */
    boolean confirmMessage(String title, String message);

    /**
     * Sets a player on the track and updates its
     * car cell.
     *
     * @param point input point
     * @param index the given index of the player
     */
    void setPlayerOnPosition(Point point, int index);

    /**
     * Updates the players information during the game.
     *
     * @param players       input players of the game
     * @param currentPlayer index of the current player
     */
    void updatePlayersInfoLabel(Player[] players, int currentPlayer);

    /**
     * Shows possible finishing positions for the given player
     * based on the given points.
     *
     * @param player the given input player
     * @param points the given points to change their
     *               corresponding cells
     */
    void showFinishingPosition(Player player, Point[] points);

    /**
     * Moves a player to the given position.
     *
     * @param player the given player
     * @param point  the given new position
     */
    void move(Player player, Point point);

    /**
     * Reverts the shown finished moves of the previous
     * player to the state before showing the highlighted
     * cells.
     *
     * @param currentPlayer the input current player
     * @param players       input players of the game
     * @param line          start/finish line of the track
     */
    void turnIntoBeforeShowingFinishingStates(Player currentPlayer, Player[] players, TrackLine line);

    /**
     * Sets the status of the replay menu item.
     *
     * @param status the given status
     */
    void replayMenuItemDisabled(boolean status);

    /**
     * Displays the final message of the game. After
     * first player ended the game and the remaining
     * players made their turns.
     *
     * @param players the input players of the game
     */
    void displayFinalStatus(Player[] players);

    /**
     * Turns the given point into the given cell state,
     * and also checks if the cell is part of the line
     * for its direction.
     *
     * @param point         the given point
     * @param cellState     the given cell state
     * @param lineDirection the given line direction
     */

    void turnIntoState(Point point, CellState cellState, LineDirection lineDirection);
}
