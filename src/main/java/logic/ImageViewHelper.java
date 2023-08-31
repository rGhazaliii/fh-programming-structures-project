package logic;

import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Helper class to handle the addition and removing
 * of the images view on a grid.
 *
 * @author ite105705
 */
public class ImageViewHelper {

    private final GridPane gridPane;

    private final ImageView[][] imageViews;

    public ImageViewHelper(GridPane gridPane, ImageView[][] imageViews) {
        this.gridPane = gridPane;
        this.imageViews = imageViews;
    }

    public void addRowConstraints(int num) {
        int rowCount = this.gridPane.getRowConstraints().size();
        for (int i = rowCount; i < rowCount + num; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            rowConstraints.setMinHeight(10);
            rowConstraints.setPrefHeight(30);
            this.gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    public void addColumnConstraints(int num) {
        int colCount = this.gridPane.getColumnConstraints().size();
        for (int i = colCount; i < colCount + num; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.SOMETIMES);
            columnConstraints.setMinWidth(30);
            columnConstraints.setPrefWidth(10);
            this.gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    public void removeRowConstraints(int index) {
        this.gridPane.getRowConstraints().remove(index);
    }

    public void removeColumnConstraints(int index) {
        this.gridPane.getColumnConstraints().remove(index);
    }

    public ImageView[][] addRowTopImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 0; x < colCount; x++) {
            //creates an empty imageview
            imageViews[x][0] = new ImageView();
            //image has to fit a cell and mustn't preserve ratio
            imageViews[x][0].setFitWidth(cellWidth);
            imageViews[x][0].setFitHeight(cellHeight);
            imageViews[x][0].setPreserveRatio(false);
            imageViews[x][0].setSmooth(true);
            //assign the correct indices for this imageview
            GridPane.setConstraints(imageViews[x][0], x, 0);
            //add the imageview to the cell
            this.gridPane.add(imageViews[x][0], x, 0);
            //the image shall resize when the cell resizes
            imageViews[x][0].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
            imageViews[x][0].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
        }

        for (int x = 0; x < colCount; x++) {
            for (int y = 1; y < rowCount; y++) {
                imageViews[x][y] = this.imageViews[x][y - 1];
                imageViews[x][y].fitWidthProperty().unbind();
                imageViews[x][y].fitHeightProperty().unbind();

                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
            }
        }

        return imageViews;
    }

    public ImageView[][] addRowBottomImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 0; x < colCount; x++) {
            for (int y = 0; y < rowCount - 1; y++) {
                imageViews[x][y] = this.imageViews[x][y];
                imageViews[x][y].fitWidthProperty().unbind();
                imageViews[x][y].fitHeightProperty().unbind();

                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
            }
        }

        for (int x = 0; x < colCount; x++) {
            //creates an empty imageview
            imageViews[x][rowCount - 1] = new ImageView();
            //image has to fit a cell and mustn't preserve ratio
            imageViews[x][rowCount - 1].setFitWidth(cellWidth);
            imageViews[x][rowCount - 1].setFitHeight(cellHeight);
            imageViews[x][rowCount - 1].setPreserveRatio(false);
            imageViews[x][rowCount - 1].setSmooth(true);
            //assign the correct indices for this imageview
            GridPane.setConstraints(imageViews[x][rowCount - 1], x, rowCount - 1);
            //add the imageview to the cell
            this.gridPane.add(imageViews[x][rowCount - 1], x, rowCount - 1);
            //the image shall resize when the cell resizes
            imageViews[x][rowCount - 1].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
            imageViews[x][rowCount - 1].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
        }

        return imageViews;
    }

    public ImageView[][] addColumnLeftImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int y = 0; y < rowCount; y++) {
            //creates an empty imageview
            imageViews[0][y] = new ImageView();
            //image has to fit a cell and mustn't preserve ratio
            imageViews[0][y].setFitWidth(cellWidth);
            imageViews[0][y].setFitHeight(cellHeight);
            imageViews[0][y].setPreserveRatio(false);
            imageViews[0][y].setSmooth(true);
            //assign the correct indices for this imageview
            GridPane.setConstraints(imageViews[0][y], 0, y);
            //add the imageview to the cell
            this.gridPane.add(imageViews[0][y], 0, y);
            //the image shall resize when the cell resizes
            imageViews[0][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
            imageViews[0][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
        }

        for (int x = 1; x < colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                imageViews[x][y] = this.imageViews[x - 1][y];
                imageViews[x][y].fitWidthProperty().unbind();
                imageViews[x][y].fitHeightProperty().unbind();

                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
            }
        }

        return imageViews;
    }

    public ImageView[][] addColumnRightImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 0; x < colCount - 1; x++) {
            for (int y = 0; y < rowCount; y++) {
                imageViews[x][y] = this.imageViews[x][y];
                imageViews[x][y].fitWidthProperty().unbind();
                imageViews[x][y].fitHeightProperty().unbind();

                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
            }
        }

        for (int y = 0; y < rowCount; y++) {
            //creates an empty imageview
            imageViews[colCount - 1][y] = new ImageView();
            //image has to fit a cell and mustn't preserve ratio
            imageViews[colCount - 1][y].setFitWidth(cellWidth);
            imageViews[colCount - 1][y].setFitHeight(cellHeight);
            imageViews[colCount - 1][y].setPreserveRatio(false);
            imageViews[colCount - 1][y].setSmooth(true);
            //assign the correct indices for this imageview
            GridPane.setConstraints(imageViews[colCount - 1][y], colCount - 1, y);
            //add the imageview to the cell
            this.gridPane.add(imageViews[colCount - 1][y], colCount - 1, y);
            //the image shall resize when the cell resizes
            imageViews[colCount - 1][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
            imageViews[colCount - 1][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
        }

        return imageViews;
    }

    public ImageView[][] removeRowTopImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        this.gridPane.getChildren().clear();

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 0; x < colCount; x++) {
            for (int y = 1; y <= rowCount; y++) {
                imageViews[x][y - 1] = this.imageViews[x][y];
                imageViews[x][y - 1].fitWidthProperty().unbind();
                imageViews[x][y - 1].fitHeightProperty().unbind();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y - 1].setFitWidth(cellWidth);
                imageViews[x][y - 1].setFitHeight(cellHeight);
                imageViews[x][y - 1].setPreserveRatio(false);
                imageViews[x][y - 1].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y - 1], x, y - 1);
                //the image shall resize when the cell resizes
                imageViews[x][y - 1].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y - 1].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
                //add the imageview to the cell
                this.gridPane.add(imageViews[x][y - 1], x, y - 1);
            }
        }

        return imageViews;
    }

    public ImageView[][] removeRowBottomImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        this.gridPane.getChildren().clear();

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 0; x < colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                imageViews[x][y] = this.imageViews[x][y];
                imageViews[x][y].fitWidthProperty().unbind();
                imageViews[x][y].fitHeightProperty().unbind();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);
                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
                //add the imageview to the cell
                this.gridPane.add(imageViews[x][y], x, y);
            }
        }

        return imageViews;
    }

    public ImageView[][] removeColumnLeftImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        this.gridPane.getChildren().clear();

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 1; x <= colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                imageViews[x - 1][y] = this.imageViews[x][y];
                imageViews[x - 1][y].fitWidthProperty().unbind();
                imageViews[x - 1][y].fitHeightProperty().unbind();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x - 1][y].setFitWidth(cellWidth);
                imageViews[x - 1][y].setFitHeight(cellHeight);
                imageViews[x - 1][y].setPreserveRatio(false);
                imageViews[x - 1][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x - 1][y], x - 1, y);
                //the image shall resize when the cell resizes
                imageViews[x - 1][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x - 1][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
                //add the imageview to the cell
                this.gridPane.add(imageViews[x - 1][y], x - 1, y);
            }
        }

        return imageViews;
    }

    public ImageView[][] removeColumnRightImages() {
        int colCount = this.gridPane.getColumnConstraints().size();
        int rowCount = this.gridPane.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colCount][rowCount];
        this.gridPane.getChildren().clear();

        // bind each Imageview to a cell of the grid pane
        int cellWidth = (int) this.gridPane.getWidth() / colCount;
        int cellHeight = (int) this.gridPane.getHeight() / rowCount;
        for (int x = 0; x <= colCount - 1; x++) {
            for (int y = 0; y < rowCount; y++) {
                imageViews[x][y] = this.imageViews[x][y];
                imageViews[x][y].fitWidthProperty().unbind();
                imageViews[x][y].fitHeightProperty().unbind();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indices for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);
                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(this.gridPane.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(this.gridPane.heightProperty().divide(rowCount));
                //add the imageview to the cell
                this.gridPane.add(imageViews[x][y], x, y);
            }
        }

        return imageViews;
    }
}
