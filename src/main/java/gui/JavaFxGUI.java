package gui;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class is responsible for changing the gui when the logic deems it necessary.
 *
 * @author ite105705
 */
public class JavaFxGUI implements GUIConnector {

    /**
     * The grid of the game.
     */
    private final GridPane gridPane;

    /**
     * The image views representing the cells on the grid.
     */
    private final ImageView[][] imageViews;

    /**
     * The label that displays the information of the players which are
     * participating in the game.
     */
    private Label lblPlayersInfo;

    /**
     * The label that displays current speed of the game animation.
     */
    private Label lblAnimationCurrentSpeed;

    /**
     * The label that displays current speed of the game replay.
     */
    private Label lblReplayCurrentSpeed;

    /**
     * The menu item which replays the game.
     */
    private MenuItem mnItmReplayGame;

    /**
     * Static constant which holds the gravel image.
     */
    public static final Image IMG_GRAVEL = new Image("gui/img/covered.png");

    /**
     * Static constant which holds the track image.
     */
    private static final Image IMG_TRACK = new Image("gui/img/imgEmpty.png");

    /**
     * Static constant which holds the starting line directing to right image.
     */
    private static final Image IMG_ARROW = new Image("gui/img/arrow.png");

    /**
     * Static constant which holds the game icon.
     */
    public static final Image IMG_ICON = new Image("gui/img/icon.png");

    /**
     * Static constant which holds the highlight image.
     */
    public static final Image IMG_HIGHLIGHT = new Image("gui/img/highlight.png");

    /**
     * Static constant which holds the highlight gravel image.
     */
    public static final Image IMG_HIGHLIGHT_GRAVEL = new Image("gui/img/highlightGravel.png");

    /**
     * Array of the car images.
     */
    private static final Image[] IMG_CARS = new Image[]{
            new Image("gui/img/car/black.png"),
            new Image("gui/img/car/blue.png"),
            new Image("gui/img/car/green.png"),
            new Image("gui/img/car/red.png")
    };

    /**
     * Array of the car images to show no movement.
     */
    private static final Image[] IMG_PRINCIPAL_MOVEMENT_CARS = new Image[]{
            new Image("gui/img/car/blackNoMovement.png"),
            new Image("gui/img/car/blueNoMovement.png"),
            new Image("gui/img/car/greenNoMovement.png"),
            new Image("gui/img/car/redNoMovement.png")
    };

    /**
     * Array of the car images to show no movement.
     */
    private static final String[] PLAYERS_CARS_COLOR = new String[]{
            "BLACK",
            "BLUE",
            "GREEN",
            "RED"
    };

    /**
     * Is used to flag that an animation is running at
     * the moment or not.
     */
    private boolean animationInPlay;

    private TrackLine trackLine;

    private Map<Point, Image> beforeMarkingPoints;

    public static final String STATUS_INACTIVE = "Inactive";

    public static final String STATUS_USER = "User";

    public static final String STATUS_AI = "AI";

    public static final String PLAYER_DEFAULT_NAME = "NO-NAME";

    public static final String BOARD_PREPARATION_TITLE = "Board preparation";

    public static final String GAME_STARTED_TITLE = "Game is started";

    public static final String BOARD_INVALID_MESSAGE = "Start/finish line is not available or the board does not contain a path.";

    public static final String BOARD_CARS_PLACEMENT_MESSAGE = "Placement of the cars is started";

    public static final String BOARD_CARS_ON_LINE_MESSAGE = "Player cannot be placed on the line!";

    public static final String BOARD_CARS_ARE_SET = "All cars are set, game is started now.";

    public static final String BOARD_CARS_PLACEMENT_INVALID = "The position is not a track.";

    public static final String NO_POSSIBLE_MOVE_TITLE = "Error on movement";

    public static final String NO_POSSIBLE_MOVE_MESSAGE = "There are no possible moves.";

    public static final String GAME_STARTED_ALREADY_MESSAGE = "The game is already started.";

    public static final String OCCUPIED_BY_CAR_TITLE = "Occupied Cell";

    public static final String OCCUPIED_BY_CAR_MESSAGE = "There is already another car on this position.";

    public static final String NEW_TRACK_TITLE = "New Track";

    public static final String NEW_TRACK_MESSAGE = "There is already an ongoing game, are you sure to start new track?";

    public static final String INVALID_MOVE_TITLE = "Invalid move";

    public static final String INVALID_MOVE_MESSAGE = "The selected position is not a valid move!";

    public static final String GAME_ENDED_TITLE = "Game has ended";

    public static final String INVALID_SAVE_STATE_MESSAGE = "The game in editing state or finished state cannot be saved.";

    public static final String INVALID_SAVE_STATE_TITLE = "Cannot be saved";

    public static final String SETTING_UNCHANGEABLE_TITLE = "Settings change";

    public static final String GAME_NOT_ENDED_TITLE = "Game has not ended yet";

    public static final String REPLAY_CANNOT_BE_PLAYED = "Replay cannot be played";

    public JavaFxGUI(GridPane gridPane, ImageView[][] imageViews) {
        this.gridPane = gridPane;
        this.imageViews = imageViews;

        this.initImageViews();
    }

    public JavaFxGUI(GridPane gridPane, ImageView[][] imageViews, Label lblPlayersInfo, MenuItem mnItmReplayGame) {
        this.gridPane = gridPane;
        this.imageViews = imageViews;
        this.lblPlayersInfo = lblPlayersInfo;
        this.mnItmReplayGame = mnItmReplayGame;
        this.beforeMarkingPoints = new HashMap<>();

        this.initImageViews();
    }

    public JavaFxGUI(GridPane gridPane, ImageView[][] imageViews, Label lblPlayersInfo, MenuItem mnItmReplayGame, boolean updateImageViews) {
        this.gridPane = gridPane;
        this.imageViews = imageViews;
        this.lblPlayersInfo = lblPlayersInfo;
        this.mnItmReplayGame = mnItmReplayGame;
        this.beforeMarkingPoints = new HashMap<>();

        if (updateImageViews) {
            this.updateImageViews();
        }
    }

    @Override
    public void setLblAnimationCurrentSpeed(Label lblAnimationCurrentSpeed) {
        this.lblAnimationCurrentSpeed = lblAnimationCurrentSpeed;
    }

    @Override
    public void setLblReplayCurrentSpeed(Label lblReplayCurrentSpeed) {
        if (this.lblReplayCurrentSpeed == null) {
            this.lblReplayCurrentSpeed = lblReplayCurrentSpeed;
        }
    }

    @Override
    public void updateReplayCurrentSpeedLabel(int value) {
        if (this.lblReplayCurrentSpeed != null) {
            this.lblReplayCurrentSpeed.setText("Value: " + value);
        }
    }

    @Override
    public void updateAnimationCurrentSpeedLabel(int value) {
        if (this.lblAnimationCurrentSpeed != null) {
            this.lblAnimationCurrentSpeed.setText("Value: " + value);
        }
    }

    /**
     * Sets the image of all image views to covered, which is the
     * initial state of the cells.
     */
    private void initImageViews() {
        for (ImageView[] imageView : this.imageViews) {
            for (ImageView view : imageView) {
                view.setImage(IMG_GRAVEL);
            }
        }
    }

    /**
     * Sets the newly added image view of the grid
     * to gravel.
     */
    private void updateImageViews() {
        for (ImageView[] imageView : this.imageViews) {
            for (ImageView view : imageView) {
                if (view != null && view.getImage() == null) {
                    view.setImage(IMG_GRAVEL);
                }
            }
        }
    }

    @Override
    public void turnIntoGravel(Point point) {
        this.imageViews[point.x()][point.y()].setImage(IMG_GRAVEL);
    }

    @Override
    public void turnIntoTrack(Point point) {
        this.imageViews[point.x()][point.y()].setImage(IMG_TRACK);
    }

    @Override
    public void turnIntoLine(Point point, LineDirection direction) {
        this.imageViews[point.x()][point.y()].setImage(IMG_ARROW);

        switch (direction) {
            case RIGHT_LEFT -> this.imageViews[point.x()][point.y()].setRotate(180);
            case LEFT_RIGHT -> this.imageViews[point.x()][point.y()].setRotate(0);
            case DOWN_TOP -> this.imageViews[point.x()][point.y()].setRotate(270);
            case TOP_DOWN -> this.imageViews[point.x()][point.y()].setRotate(90);
        }
    }

    @Override
    public void drawLine(TrackLine trackLine) {
        if (trackLine != null) {
            switch (trackLine.getDirection()) {
                case LEFT_RIGHT, RIGHT_LEFT -> {
                    for (int row = trackLine.getStartPoint().y(); row <= trackLine.getEndPoint().y(); row++) {
                        Image image = this.imageViews[trackLine.getEndPoint().x()][row].getImage();

                        if (!this.isImageCar(image)) {
                            this.turnIntoLine(new Point(trackLine.getEndPoint().x(), row), trackLine.getDirection());
                        }
                    }
                }
                case TOP_DOWN, DOWN_TOP -> {
                    for (int col = trackLine.getStartPoint().x(); col <= trackLine.getEndPoint().x(); col++) {
                        Image image = this.imageViews[col][trackLine.getEndPoint().y()].getImage();

                        if (!this.isImageCar(image)) {
                            this.turnIntoLine(new Point(col, trackLine.getEndPoint().y()), trackLine.getDirection());
                        }
                    }
                }
            }
        }

        this.trackLine = trackLine;
    }

    @Override
    public void removeLine(TrackLine trackLine) {
        switch (trackLine.getDirection()) {
            case LEFT_RIGHT, RIGHT_LEFT -> {
                for (int row = trackLine.getStartPoint().y(); row <= trackLine.getEndPoint().y(); row++) {
                    this.turnIntoTrack(new Point(trackLine.getEndPoint().x(), row));
                }
            }
            case TOP_DOWN, DOWN_TOP -> {
                for (int col = trackLine.getStartPoint().x(); col <= trackLine.getEndPoint().x(); col++) {
                    this.turnIntoTrack(new Point(col, trackLine.getEndPoint().y()));
                }
            }
        }
    }

    @Override
    public void turnLineIntoTrack(TrackLine trackLine) {
        switch (trackLine.getDirection()) {
            case LEFT_RIGHT, RIGHT_LEFT -> {
                int endY = trackLine.getEndPoint().y();
                int lineX = trackLine.getEndPoint().x();
                for (int row = trackLine.getStartPoint().y(); row <= endY; row++) {
                    this.turnIntoTrack(new Point(lineX, row));
                }
            }
            case TOP_DOWN, DOWN_TOP -> {
                int endX = trackLine.getEndPoint().x();
                int lineY = trackLine.getEndPoint().y();
                for (int col = trackLine.getStartPoint().x(); col <= endX; col++) {
                    this.turnIntoTrack(new Point(col, lineY));
                }
            }
        }
    }

    @Override
    public void reverseLine(TrackLine trackLine) {
        switch (trackLine.getDirection()) {
            case LEFT_RIGHT, RIGHT_LEFT -> {
                for (int row = trackLine.getStartPoint().y(); row <= trackLine.getEndPoint().y(); row++) {
                    this.reverseDirection(new Point(trackLine.getEndPoint().x(), row), trackLine.getDirection());
                }
            }
            case TOP_DOWN, DOWN_TOP -> {
                for (int col = trackLine.getStartPoint().x(); col <= trackLine.getEndPoint().x(); col++) {
                    this.reverseDirection(new Point(col, trackLine.getEndPoint().y()), trackLine.getDirection());
                }
            }
        }
    }

    @Override
    public void reverseDirection(Point point, LineDirection currentDirection) {
        switch (currentDirection) {
            case RIGHT_LEFT -> this.imageViews[point.x()][point.y()].setRotate(0);
            case LEFT_RIGHT -> this.imageViews[point.x()][point.y()].setRotate(-180);
            case DOWN_TOP -> this.imageViews[point.x()][point.y()].setRotate(90);
            case TOP_DOWN -> this.imageViews[point.x()][point.y()].setRotate(-90);
        }
    }

    @Override
    public void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 100);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(IMG_ICON);
        alert.showAndWait();
    }

    @Override
    public boolean confirmMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 100);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(IMG_ICON);
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && (result.get() == ButtonType.OK);
    }

    @Override
    public void setPlayerOnPosition(Point point, int index) {
        this.imageViews[point.x()][point.y()].setImage(IMG_CARS[index]);
    }

    @Override
    public void updatePlayersInfoLabel(Player[] players, int currentPlayer) {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < players.length; i++) {
            info.append(i + 1)
                    .append(") ")
                    .append(players[i].getName())
                    .append(" - ")
                    .append(players[i].getStatus())
                    .append("\n");
        }

        Player player = players[currentPlayer];
        info.append("\nCurrent Player: ").append(currentPlayer + 1);
        info.append("\nSpeed: ").append(player.getSpeed());

        info.append("\n\nColors:\n");
        for (int i = 0; i < players.length; i++) {
            info.append("Player ")
                    .append(i + 1)
                    .append(" : ")
                    .append(PLAYERS_CARS_COLOR[i])
                    .append("\n");
        }

        this.lblPlayersInfo.setText(info.toString());
    }

    @Override
    public void showFinishingPosition(Player player, Point[] points) {
        for (Point point : points) {
            this.beforeMarkingPoints.put(point, this.imageViews[point.x()][point.y()].getImage());
            Image currentImage = this.imageViews[point.x()][point.y()].getImage();

            if (point instanceof PrincipalPoint) {
                this.imageViews[point.x()][point.y()].setImage(IMG_PRINCIPAL_MOVEMENT_CARS[player.getIndex()]);
            } else if (point.equals(player.getPosition())) {
                this.imageViews[point.x()][point.y()].setImage(IMG_CARS[player.getIndex()]);
            } else if (currentImage.equals(IMG_GRAVEL)) {
                this.imageViews[point.x()][point.y()].setImage(IMG_HIGHLIGHT_GRAVEL);
            } else if (!this.isImageCar(currentImage)) {
                this.imageViews[point.x()][point.y()].setImage(IMG_HIGHLIGHT);
            }
        }
    }

    private boolean isImageCar(Image image) {
        boolean isEqual = false;
        for (int i = 0; i < IMG_CARS.length; i++) {
            if (image.equals(IMG_CARS[i]) || image.equals(IMG_PRINCIPAL_MOVEMENT_CARS[i])) {
                isEqual = true;
                break;
            }
        }

        return isEqual;
    }

    @Override
    public void move(Player player, Point point) {
        this.animationInPlay = true;
        Point currentPosition = player.getPosition();
        Point lastPosition = player.getLastPosition();
        ImageView currentPositionImageView = this.imageViews[currentPosition.x()][currentPosition.y()];
        ImageView lastPositionImageView = this.imageViews[lastPosition.x()][lastPosition.y()];
        currentPositionImageView.toFront();
        lastPositionImageView.toFront();

        double cellWidth = lastPositionImageView.getLayoutBounds().getWidth();
        double cellHeight = lastPositionImageView.getLayoutBounds().getHeight();

        double xMovement = (point.x() - lastPosition.x()) * cellWidth;
        double yMovement = (point.y() - lastPosition.y()) * cellHeight;
        TranslateTransition carTranslation = new TranslateTransition(Duration.millis(500), lastPositionImageView);
        carTranslation.setFromX(0);
        carTranslation.setToX(xMovement);
        carTranslation.setFromY(0);
        carTranslation.setToY(yMovement);
        carTranslation.play();

        carTranslation.setOnFinished(e -> {
            this.animationInPlay = false;

            if (!this.imageViews[point.x()][point.y()].getImage().equals(IMG_GRAVEL)) {
                this.gridPane.getChildren().remove(lastPositionImageView);
                this.imageViews[lastPosition.x()][lastPosition.y()] = new ImageView(IMG_CARS[player.getIndex()]);
                ImageView newImageView = this.imageViews[lastPosition.x()][lastPosition.y()];
                this.gridPane.add(newImageView, lastPosition.x(), lastPosition.y());

                int colCount = this.gridPane.getColumnConstraints().size();
                int rowCount = this.gridPane.getRowConstraints().size();
                int gridCellWidth = (int) this.gridPane.getWidth() / colCount;
                int gridCellHeight = (int) this.gridPane.getHeight() / rowCount;

                newImageView.setFitWidth(gridCellWidth);
                newImageView.setFitHeight(gridCellHeight);
                newImageView.setPreserveRatio(false);
                newImageView.setSmooth(true);
                GridPane.setConstraints(newImageView, lastPosition.x(), lastPosition.y());
                newImageView.fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                newImageView.fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));

                this.turnIntoTrack(player.getLastPosition());
                this.imageViews[point.x()][point.y()].setImage(IMG_CARS[player.getIndex()]);
            }
        });
    }

    @Override
    public void turnIntoBeforeShowingFinishingStates(Player currentPlayer, Player[] players, TrackLine line) {
        HashMap<Point, Image> duplicateMarkings = new HashMap<>();
        for (Map.Entry<Point, Image> entry : this.beforeMarkingPoints.entrySet()) {
            Point key = entry.getKey();
            Image value = entry.getValue();

            if (!this.isImageCar(value)) {
                this.imageViews[key.x()][key.y()].setImage(value);
            } else if (!currentPlayer.getLastPosition().equals(key)) {
                duplicateMarkings.put(key, value);
            }
        }

        if (!currentPlayer.getLastPosition().equals(currentPlayer.getPosition())
            &&
            !this.imageViews[currentPlayer.getLastPosition().x()][currentPlayer.getLastPosition().y()].getImage().equals(IMG_ARROW)
            &&
            !this.animationInPlay
        ) {
            this.imageViews[currentPlayer.getLastPosition().x()][currentPlayer.getLastPosition().y()].setImage(IMG_TRACK);
        }

        this.beforeMarkingPoints = duplicateMarkings;

        this.drawLine(line);
        for (Player player : players) {
            this.imageViews[player.getPosition().x()][player.getPosition().y()].setImage(IMG_CARS[player.getIndex()]);
        }
    }

    @Override
    public void replayMenuItemDisabled(boolean status) {
        this.mnItmReplayGame.setDisable(status);
    }

    @Override
    public void displayFinalStatus(Player[] players) {
        StringBuilder info = new StringBuilder();

        info.append("Winner(s):").append("\n");
        for (int i = 0; i < players.length; i++) {
            if (players[i].isWinner()) {
                info.append(i + 1)
                        .append(") ")
                        .append(players[i].getName())
                        .append(" - ")
                        .append(PLAYERS_CARS_COLOR[players[i].getIndex()])
                        .append("\n");
            }
        }
        info.append("\n\nList of all players and their number of moves:").append("\n");
        for (int i = 0; i < players.length; i++) {
            info.append(i + 1)
                    .append(") ")
                    .append(players[i].getName())
                    .append(" - ")
                    .append(PLAYERS_CARS_COLOR[players[i].getIndex()])
                    .append(" - ")
                    .append(players[i].getMoves().size())
                    .append("\n");
        }

        this.showMessage(GAME_ENDED_TITLE, info.toString());
    }

    @Override
    public void turnIntoState(Point point, CellState cellState, LineDirection lineDirection) {
        switch (cellState) {
            case GRAVEL -> this.turnIntoGravel(point);
            case TRACK, CAR -> this.turnIntoTrack(point);
            case LINE -> this.turnIntoLine(point, lineDirection);
        }
    }
}
