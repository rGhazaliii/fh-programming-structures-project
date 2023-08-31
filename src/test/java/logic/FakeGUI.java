package logic;

import javafx.scene.control.Label;

/**
 * Used for testing. As we are not testing the gui, nothing happens in the methods.
 *
 * @author ite105705
 */
public class FakeGUI implements GUIConnector {

    @Override
    public void setLblAnimationCurrentSpeed(Label lblAnimationCurrentSpeed) {

    }

    @Override
    public void setLblReplayCurrentSpeed(Label lblReplayCurrentSpeed) {

    }

    @Override
    public void updateReplayCurrentSpeedLabel(int value) {

    }

    @Override
    public void updateAnimationCurrentSpeedLabel(int value) {

    }

    @Override
    public void turnIntoGravel(Point point) {

    }

    @Override
    public void turnIntoTrack(Point point) {

    }

    @Override
    public void turnIntoLine(Point point, LineDirection direction) {

    }

    @Override
    public void drawLine(TrackLine trackLine) {

    }

    @Override
    public void removeLine(TrackLine trackLine) {

    }

    @Override
    public void reverseLine(TrackLine trackLine) {

    }

    @Override
    public void turnLineIntoTrack(TrackLine trackLine) {

    }

    @Override
    public void reverseDirection(Point point, LineDirection currentDirection) {

    }

    @Override
    public void showMessage(String title, String message) {

    }

    @Override
    public boolean confirmMessage(String title, String message) {
        return false;
    }

    @Override
    public void setPlayerOnPosition(Point point, int index) {

    }

    @Override
    public void updatePlayersInfoLabel(Player[] players, int currentPlayer) {

    }

    @Override
    public void showFinishingPosition(Player player, Point[] points) {

    }

    @Override
    public void move(Player player, Point point) {

    }

    @Override
    public void turnIntoBeforeShowingFinishingStates(Player currentPlayer, Player[] players, TrackLine line) {

    }

    @Override
    public void replayMenuItemDisabled(boolean status) {

    }

    @Override
    public void displayFinalStatus(Player[] players) {

    }

    @Override
    public void turnIntoState(Point point, CellState cellState, LineDirection lineDirection) {
        
    }
}
