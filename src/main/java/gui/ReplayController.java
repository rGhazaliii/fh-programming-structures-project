package gui;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import logic.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReplayController implements Initializable {

    /**
     * The GridPane that displays the grid of the cells.
     */
    @FXML
    private GridPane grdPnTrackField;

    /**
     * Holds the two-dimensional array of the ImageViews that
     * are placed in the GridPane.
     */
    private ImageView[][] gridImageView;

    /**
     * Reference to the main window gui of the game.
     */
    private JavaFxGUI gui;

    /**
     * Board of the game.
     */
    private Board board;

    /**
     * Players of the game.
     */
    private Player[] players;

    /**
     * Speed of the replay.
     */
    private static int speed = 1;

    /**
     * Minimum value for the replay speed.
     */
    private static final double MIN_SPEED_LEVEL = 1;

    /**
     * Maximum value for the replay speed.
     */
    private static final double MAX_SPEED_LEVEL = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gridImageView = this.initImages();
        this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView);
    }

    private ImageView[][] initImages() {
        int colCount = this.grdPnTrackField.getColumnConstraints().size();
        int rowCount = this.grdPnTrackField.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        this.grdPnTrackField.getChildren().clear();

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.grdPnTrackField.getWidth() / colCount;
        int cellHeight = (int) this.grdPnTrackField.getHeight() / rowCount;
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
                this.grdPnTrackField.add(imageViews[x][y], x, y);
                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.grdPnTrackField.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.grdPnTrackField.heightProperty().divide(rowCount));
            }
        }

        return imageViews;
    }

    private void reinitializeGUI() {
        this.gui = new JavaFxGUI(this.grdPnTrackField, this.gridImageView);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public static void increaseSpeed() {
        if (speed < MAX_SPEED_LEVEL) {
            speed++;
        }
    }

    public static void decreaseSpeed() {
        if (speed > MIN_SPEED_LEVEL) {
            speed--;
        }
    }

    public static int getSpeed() {
        return speed;
    }

    public void play() {
        this.adjustBoardSize();
        this.reinitializeGUI();
        this.adjustBoardCells();
        this.gui.drawLine(this.board.getStartAndFinishLine());

        List<PathTransition> pathTransitionList = new ArrayList<>();
        if (this.players != null) {
            for (Player currentPlayer : this.players) {
                Point currentPosition = currentPlayer.getMoves().get(0);
                this.gui.setPlayerOnPosition(currentPosition, currentPlayer.getIndex());

                Node node = this.gridImageView[currentPosition.x()][currentPosition.y()];
                node.toFront();

                Path path = new Path();
                double cellWidth = node.getLayoutBounds().getWidth();
                double cellHeight = node.getLayoutBounds().getHeight();
                path.getElements().add(new MoveTo(cellWidth / 2, cellHeight / 2));

                double currentXLine = cellWidth / 2;
                double currentYLine = cellHeight / 2;
                for (int j = 0; j < currentPlayer.getMoves().size(); j++) {
                    Point currentMovementPosition = currentPlayer.getMoves().get(j);
                    int xMovement = currentMovementPosition.x() - currentPosition.x();
                    int yMovement = currentMovementPosition.y() - currentPosition.y();

                    if (xMovement != 0 && yMovement != 0) {
                        double newX = (xMovement * cellWidth);
                        double newY = (yMovement * cellHeight);
                        currentXLine += newX;
                        currentYLine += newY;

                        path.getElements().add(new LineTo(currentXLine, currentYLine));
                    } else if (xMovement != 0) {
                        double newX = (xMovement * cellWidth);
                        currentXLine += newX;

                        path.getElements().add(new LineTo(currentXLine, currentYLine));
                    } else if (yMovement != 0) {
                        double newY = (yMovement * cellHeight);
                        currentYLine += newY;

                        path.getElements().add(new LineTo(currentXLine, currentYLine));
                    }

                    currentPosition = currentMovementPosition;
                }

                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.seconds((double) currentPlayer.getMoves().size() / speed));
                pathTransition.setNode(node);
                pathTransition.setPath(path);
                pathTransition.setAutoReverse(true);
                pathTransitionList.add(pathTransition);
            }

            for (PathTransition pathTransition : pathTransitionList) {
                pathTransition.play();
            }
        }
    }

    private void adjustBoardSize() {
        ImageViewHelper imageViewHelper = new ImageViewHelper(this.grdPnTrackField, this.gridImageView);
        int rowDiff = this.board.getCells()[0].length - this.grdPnTrackField.getRowConstraints().size();
        int colDiff = this.board.getCells().length - this.grdPnTrackField.getColumnConstraints().size();

        if (rowDiff > 0) {
            imageViewHelper.addRowConstraints(rowDiff);
            this.gridImageView = imageViewHelper.addRowBottomImages();
        }
        if (colDiff > 0) {
            imageViewHelper.addColumnConstraints(colDiff);
            this.gridImageView = imageViewHelper.addColumnRightImages();
        }
    }

    private void adjustBoardCells() {
        Cell[][] cells = this.board.getCells();
        LineDirection lineDirection = this.board.getStartAndFinishLine().getDirection();
        for (int row = 0; row < cells[0].length; row++) {
            for (int col = 0; col < cells.length; col++) {
                this.gui.turnIntoState(new Point(col, row), cells[col][row].getCurrentState(), lineDirection);
            }
        }
    }
}
