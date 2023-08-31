package gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// TODO: If the game starts only with AI player, cells would have problem
// TODO: AI and player have problems on showing available moves and animation

/**
 * Main class for the user interface.
 *
 * @author ite105705
 */
public class UserInterfaceController implements Initializable {

    /**
     * The GridPane that displays the grid of the cells.
     */
    @FXML
    private GridPane grdPnTrackField;

    /**
     * The label that displays the information of the players which are
     * participating in the game.
     */
    @FXML
    private Label lblPlayersInfo;

    /**
     * The label that displays current speed of the game animation.
     */
    @FXML
    private Label lblAnimationCurrentSpeed;

    /**
     * The label that displays current speed of the game replay.
     */
    @FXML
    private Label lblReplayCurrentSpeed;

    /**
     * The menu item which replays the game.
     */
    @FXML
    private MenuItem mnItmReplayGame;

    /**
     * Holds the reference to the current instance of the game.
     */
    private RaceTrack raceTrack;

    /**
     * Holds the two-dimensional array of the ImageViews that
     * are placed in the GridPane.
     */
    private ImageView[][] gridImageView;

    /**
     * Holds the reference to the main window stage of the game.
     */
    private Stage mainStage;

    /**
     * Reference to the main window gui of the game.
     */
    private JavaFxGUI gui;

    /**
     * Reference to the current mouse event on the board.
     */
    private MouseEvent mouseEvent;

    /**
     * The current point on the track field which was recently clicked.
     */
    private Point clickedPosition;

    /**
     * Setter method of the mainStage field.
     *
     * @param stage the given stage
     */
    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    /**
     * This is where you need to add code that should happen during
     * initialization and then change the java doc comment.
     *
     * @param location  probably not used
     * @param resources probably not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.editTrack();
    }

    /**
     * Returns a two-dimensional array of ImageViews, which are all children
     * of gridPane and where each is assigned to a cell of the gridPane.
     *
     * @param grdPn the given GridPane
     * @return the two-dimensional array of ImageViews
     */
    private ImageView[][] initImages(GridPane grdPn) {
        int colCount = grdPn.getColumnConstraints().size();
        int rowCount = grdPn.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        this.grdPnTrackField.getChildren().clear();

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) grdPn.getWidth() / colCount;
        int cellHeight = (int) grdPn.getHeight() / rowCount;
        for (int x = 0; x < colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                //creates an empty imageview
                imageViews[x][y] = new ImageView();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);
                //add the imageview to the cell
                grdPn.add(imageViews[x][y], x, y);
                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(grdPn.heightProperty().divide(rowCount));
            }
        }

        return imageViews;
    }

    /**
     * Sets the current x-axis and y-axis value of the track field
     * based on the clicked area on the board.
     *
     * @param clickedX x-axis value of the mouse event
     * @param clickedY y-axis value of the mouse event
     */
    private void setCurrentPosition(int clickedX, int clickedY) {
        this.clickedPosition = new Point(-1, -1);

        for (ImageView[] imageViewRow : this.gridImageView) {
            for (ImageView imageView : imageViewRow) {
                if (imageView.getBoundsInParent().contains(clickedX, clickedY)) {
                    this.clickedPosition = new Point(GridPane.getColumnIndex(imageView), GridPane.getRowIndex(imageView), clickedX, clickedY);
                }
            }
        }
    }

    /**
     * Initializes image views and start the game by creating a new
     * instance of the RaceTrack class.
     */
    private void editTrack() {
        this.gridImageView = this.initImages(this.grdPnTrackField);
        this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame);
        this.raceTrack = new RaceTrack(
                this.gui,
                this.grdPnTrackField.getColumnConstraints().size(),
                this.grdPnTrackField.getRowConstraints().size()
        );
    }

    /**
     * Starts the game and user cannot edit the track anymore.
     * Sets the cars of the players on the track and starts the
     * game loop.
     */
    public void startGame(Player[] players) {
        this.raceTrack = new RaceTrack(this.gui, this.raceTrack.getBoard(), players);
        this.raceTrack.startGame();
    }

    /**
     * Reacts on clicking the gridPane.
     *
     * @param mouseEvent event responsible for calling this method
     */
    @FXML
    private void onMouseClickedTrackField(MouseEvent mouseEvent) {
        this.setCurrentPosition((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (this.clickedPosition.x() >= 0 && this.clickedPosition.y() >= 0) {
            System.out.println("clicked on " + this.clickedPosition.x() + " " + this.clickedPosition.y());
            this.handleClickedOnTrack(clickedPosition, mouseEvent);
        }
    }

    /**
     * Handles the click on the track field.
     *
     * @param point      the point which was clicked on
     * @param mouseEvent the clicked mouse event
     */
    private void handleClickedOnTrack(Point point, MouseEvent mouseEvent) {
        if (!this.raceTrack.isGameEnded()) {
            switch (mouseEvent.getButton()) {
                case PRIMARY -> { // Left click
                    if (this.raceTrack.isPlacementEnded()) {
                        this.raceTrack.move(point);
                    } else if (this.raceTrack.isGameStarted()) {
                        this.raceTrack.placePlayerOnBoard(point);
                    } else {
                        this.raceTrack.turnIntoTrack(point);
                    }
                }
                case SECONDARY -> { // Right click
                    if (!this.raceTrack.isPlacementEnded()) {
                        if (this.raceTrack.getBoard().isLine(point)) {
                            this.raceTrack.reverseLine();
                        } else {
                            this.raceTrack.turnIntoGravel(point);
                        }
                    }
                }
                case MIDDLE -> {
                    if (!this.raceTrack.isPlacementEnded()) {
                        if (this.raceTrack.getBoard().isLine(point)) {
                            this.gui.removeLine(this.raceTrack.getBoard().getStartAndFinishLine());
                            this.raceTrack.getBoard().rotateLine(point);
                            this.gui.drawLine(this.raceTrack.getBoard().getStartAndFinishLine());
                        } else if (this.raceTrack.getBoard().getStartAndFinishLine().isDrawn()) {
                            this.gui.turnLineIntoTrack(this.raceTrack.getBoard().getStartAndFinishLine());
                            this.raceTrack.getBoard().turnLineIntoTrack();

                            this.raceTrack.turnIntoLine(point);
                        } else {
                            this.raceTrack.turnIntoLine(point);
                        }
                    }
                }
            }
        }

        System.out.println(this.raceTrack.getBoard());
        System.out.println("=============================");
    }

    /**
     * Handles the event that is triggered when the menu item Quit is
     * selected. Here the program is terminated.
     */
    @FXML
    private void onClickMnItmQuit() {
        Stage stage = (Stage) this.grdPnTrackField.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the event drag detection of the track field.
     */
    @FXML
    private void onDragDetectedTrackField(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;

        // Initiates a drag-and-drop gesture
        Dragboard dragboard = this.grdPnTrackField.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Adds the source text to the drag board
        ClipboardContent content = new ClipboardContent();
        content.putString("");
        dragboard.setContent(content);
        mouseEvent.consume();
    }

    /**
     * Handles the event drag over of the track field, by setting
     * the cell which is crossed by the mouse.
     *
     * @param dragEvent the input drag event
     */
    @FXML
    private void onDragOverTrackField(DragEvent dragEvent) {
        this.setCurrentPosition((int) dragEvent.getX(), (int) dragEvent.getY());
        if (this.clickedPosition.x() >= 0 && this.clickedPosition.y() >= 0) {
            System.out.println("dragged on " + this.clickedPosition.x() + " " + this.clickedPosition.y());
            this.handleClickedOnTrack(clickedPosition, this.mouseEvent);
        }
    }

    /**
     * Handles the event of the start new race menu item.
     *
     * @param actionEvent the input action event
     */
    @FXML
    private void onClickMnItmStartNewRace(ActionEvent actionEvent) throws IOException {
        if (!this.raceTrack.isGameStarted()) {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("PlayerInterface.fxml"));
            Scene settingsScene = new Scene(fxmlLoader.load());
            Stage playersConfigurationWindow = new Stage();
            playersConfigurationWindow.setMinWidth(600);
            playersConfigurationWindow.setMinHeight(550);
            playersConfigurationWindow.setTitle("Players Configuration");
            playersConfigurationWindow.setScene(settingsScene);
            playersConfigurationWindow.getIcons().add(JavaFxGUI.IMG_ICON);
            playersConfigurationWindow.initModality(Modality.WINDOW_MODAL);
            playersConfigurationWindow.initOwner(this.mainStage);
            playersConfigurationWindow.show();

            PlayerController playerController = fxmlLoader.getController();
            playerController.setUserInterfaceController(this);
        } else {
            this.gui.showMessage(JavaFxGUI.GAME_STARTED_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    /**
     * Handles the event of the new track menu item.
     *
     * @param actionEvent the input action event
     */
    @FXML
    private void onClickMnItmNewTrack(ActionEvent actionEvent) {
        if (this.raceTrack.isGameStarted() &&
            this.gui.confirmMessage(JavaFxGUI.NEW_TRACK_TITLE, JavaFxGUI.NEW_TRACK_MESSAGE)) {
            this.editTrack();
            this.gui.replayMenuItemDisabled(true);
        }
    }

    @FXML
    private void onClickMnItmLoadTrack(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json Files", "*.json"));
            File file = fileChooser.showOpenDialog(this.mainStage);

            if (file != null) {
                try {
                    String content = Files.readString(Path.of(file.getPath()));
                    JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();

                    JsonElement line = jsonObject.get("direction");
                    LineDirection lineDirection = TrackLine.toLineDirection(line.toString());

                    JsonArray track = jsonObject.getAsJsonArray("track");
                    CellState[][] cells = new CellState[track.size()][track.get(0).getAsJsonArray().size()];
                    Point lineStartPoint = null, lineEndPoint = null;
                    for (int row = 0; row < track.size(); row++) {
                        for (int col = 0; col < track.get(row).getAsJsonArray().size(); col++) {
                            cells[row][col] = Cell.toCellState(track.get(row).getAsJsonArray().get(col).toString());
                            Point point = new Point(col, row);
                            this.gui.turnIntoState(new Point(col, row), cells[row][col], lineDirection);

                            if (cells[row][col] == CellState.LINE && lineStartPoint == null) {
                                lineStartPoint = point;
                            }
                            if (cells[row][col] == CellState.LINE) {
                                lineEndPoint = point;
                            }
                        }
                    }

                    Board board = new Board(cells, lineDirection);
                    if (lineStartPoint != null) {
                        TrackLine trackLine = new TrackLine(lineStartPoint, lineEndPoint, lineDirection);
                        board.setStartAndFinishLine(trackLine);
                        board.getStartAndFinishLine().setIsDrawn(true);
                    }

                    JsonArray players = jsonObject.getAsJsonArray("player");
                    List<Player> activePlayers = new ArrayList<>();
                    for (int i = 0; i < players.size(); i++) {
                        JsonObject currentPlayer = players.get(i).getAsJsonObject();
                        boolean isActive = currentPlayer.get("active").getAsBoolean();
                        boolean isAI = currentPlayer.get("ai").getAsBoolean();

                        if (isActive) {
                            Player newPlayer = new Player(currentPlayer.get("name").getAsString(), Player.toPlayerStatus(true, isAI), i);
                            JsonArray currentPosition = currentPlayer.get("current").getAsJsonArray();
                            newPlayer.setPosition(new Point(currentPosition.get(0).getAsInt(), currentPosition.get(1).getAsInt()));

                            JsonArray lastPosition = currentPlayer.get("last").getAsJsonArray();
                            newPlayer.setLastPosition(new Point(lastPosition.get(0).getAsInt(), lastPosition.get(1).getAsInt()));

                            newPlayer.setCrossedLine(currentPlayer.get("lap").getAsInt());

                            activePlayers.add(newPlayer);
                        }
                    }

                    Player[] gamePlayers;
                    if (!activePlayers.isEmpty()) {
                        gamePlayers = new Player[activePlayers.size()];
                        for (int i = 0; i < activePlayers.size(); i++) {
                            gamePlayers[i] = activePlayers.get(i);
                            this.raceTrack.setCurrentPlayerIndex(i);
                            this.raceTrack.setPlayerPosition(gamePlayers[i].getPosition());
                        }
                    } else {
                        gamePlayers = new Player[1];
                        gamePlayers[0] = new Player("", PlayerStatus.AI, 0);
                    }

                    this.raceTrack = new RaceTrack(this.gui, board, gamePlayers);
                    this.raceTrack.setCurrentPlayerIndex(jsonObject.get("currentPlayer").getAsInt());
                    this.raceTrack.startGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    private void onClickMnItmSaveCurrentTrack(ActionEvent actionEvent) {
        if (this.raceTrack.isPlacementEnded() || this.raceTrack.isGameStarted()) {
            String game = this.raceTrack.save();

            //Creating a File chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json Files", "*.json"));
            File file = fileChooser.showSaveDialog(this.mainStage);

            if (file != null) {
                try {
                    PrintWriter writer;
                    writer = new PrintWriter(file);
                    writer.println(game);
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        } else {
            this.gui.showMessage(JavaFxGUI.INVALID_SAVE_STATE_TITLE, JavaFxGUI.INVALID_SAVE_STATE_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmAddRowTop(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.addRowConstraints(1);
            this.gridImageView = imageViewHelper.addRowTopImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.ROW_ADD_TOP
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmAddRowBottom(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.addRowConstraints(1);
            this.gridImageView = imageViewHelper.addRowBottomImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.ROW_ADD_BOTTOM
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmRemoveRowTop(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.removeRowConstraints(0);
            this.gridImageView = imageViewHelper.removeRowTopImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.ROW_REMOVE_TOP
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmRemoveRowBottom(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.removeRowConstraints(this.grdPnTrackField.getRowConstraints().size() - 1);
            this.gridImageView = imageViewHelper.removeRowBottomImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.ROW_REMOVE_BOTTOM
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmAddColumnLeft(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.addColumnConstraints(1);
            this.gridImageView = imageViewHelper.addColumnLeftImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.COL_ADD_LEFT
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmAddColumnRight(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.addColumnConstraints(1);
            this.gridImageView = imageViewHelper.addColumnRightImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.COL_ADD_RIGHT
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmRemoveColumnLeft(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.removeColumnConstraints(0);
            this.gridImageView = imageViewHelper.removeColumnLeftImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.COL_REMOVE_LEFT
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmRemoveColumnRight(ActionEvent actionEvent) {
        if (!this.raceTrack.isGameStarted()) {
            ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
            imageViewHelper.removeColumnConstraints(this.grdPnTrackField.getColumnConstraints().size() - 1);
            this.gridImageView = imageViewHelper.removeColumnRightImages();

            this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView, this.lblPlayersInfo, this.mnItmReplayGame, true);
            this.raceTrack = new RaceTrack(
                    this.gui,
                    this.raceTrack.getBoard(),
                    BoardUpdate.COL_REMOVE_RIGHT
            );
        } else {
            this.gui.showMessage(JavaFxGUI.SETTING_UNCHANGEABLE_TITLE, JavaFxGUI.GAME_STARTED_ALREADY_MESSAGE);
        }
    }

    @FXML
    private void onClickMnItmReplayGame(ActionEvent actionEvent) {
        if (this.raceTrack.isGameEnded()) {
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("ReplayInterface.fxml"));
            Scene replayScene = null;
            try {
                replayScene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage replayWindow = new Stage();
            replayWindow.setTitle("Game Replay");
            replayWindow.setScene(replayScene);
            replayWindow.getIcons().add(JavaFxGUI.IMG_ICON);
            replayWindow.initModality(Modality.WINDOW_MODAL);
            replayWindow.initOwner(this.mainStage);
            replayWindow.show();

            ReplayController replayController = fxmlLoader.getController();
            replayController.setBoard(this.raceTrack.getBoard());
            replayController.setPlayers(this.raceTrack.getPlayers());
            replayController.play();
        } else {
            this.gui.showMessage(JavaFxGUI.GAME_NOT_ENDED_TITLE, JavaFxGUI.REPLAY_CANNOT_BE_PLAYED);
        }
    }

    @FXML
    private void onClickMnItmReplaySpeedIncrease(ActionEvent actionEvent) {
        this.gui.setLblReplayCurrentSpeed(this.lblReplayCurrentSpeed);
        ReplayController.increaseSpeed();
        this.gui.updateReplayCurrentSpeedLabel(ReplayController.getSpeed());
    }

    @FXML
    private void onClickMnItmReplaySpeedDecrease(ActionEvent actionEvent) {
        this.gui.setLblReplayCurrentSpeed(this.lblReplayCurrentSpeed);
        ReplayController.decreaseSpeed();
        this.gui.updateReplayCurrentSpeedLabel(ReplayController.getSpeed());
    }

    @FXML
    private void onClickMnItmAnimationSpeedIncrease(ActionEvent actionEvent) {
        this.gui.setLblAnimationCurrentSpeed(this.lblAnimationCurrentSpeed);
        RaceTrack.increaseSpeed();
        this.gui.updateAnimationCurrentSpeedLabel(RaceTrack.getSpeed());
    }

    @FXML
    private void onClickMnItmAnimationSpeedDecrease(ActionEvent actionEvent) {
        this.gui.setLblAnimationCurrentSpeed(this.lblAnimationCurrentSpeed);
        RaceTrack.decreaseSpeed();
        this.gui.updateAnimationCurrentSpeedLabel(RaceTrack.getSpeed());
    }
}
