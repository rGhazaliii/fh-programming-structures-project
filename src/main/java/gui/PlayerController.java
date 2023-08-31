package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import logic.Player;
import logic.PlayerStatus;

import java.util.ArrayList;

public class PlayerController extends Application {

    /**
     * Player 1 text field containing its name.
     */
    @FXML
    private TextField txtFldPlayer1;

    /**
     * Player 1 toggle group indicating its status.
     */
    @FXML
    private ToggleGroup player1Status;

    /**
     * Player 2 text field containing its name.
     */
    @FXML
    private TextField txtFldPlayer2;

    /**
     * Player 2 toggle group indicating its status.
     */
    @FXML
    private ToggleGroup player2Status;

    /**
     * Player 3 text field containing its name.
     */
    @FXML
    private TextField txtFldPlayer3;

    /**
     * Player 3 toggle group indicating its status.
     */
    @FXML
    private ToggleGroup player3Status;

    /**
     * Player 4 text field containing its name.
     */
    @FXML
    private TextField txtFldPlayer4;

    /**
     * Player 4 toggle group indicating its status.
     */
    @FXML
    private ToggleGroup player4Status;

    /**
     * Holds the reference to the userInterfaceController.
     */
    private UserInterfaceController userInterfaceController;

    /**
     * List of the players chosen in the settings window.
     */
    private final ArrayList<Player> trackPlayers;

    public PlayerController() {
        this.trackPlayers = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    /**
     * Setter method of the userInterfaceController field.
     *
     * @param userInterfaceController the given userInterfaceController
     */
    public void setUserInterfaceController(UserInterfaceController userInterfaceController) {
        this.userInterfaceController = userInterfaceController;
    }

    @FXML
    private void onBtnStartGame(ActionEvent actionEvent) {
        this.createActivePlayer(this.player1Status, this.txtFldPlayer1, 0);
        this.createActivePlayer(this.player2Status, this.txtFldPlayer2, 1);
        this.createActivePlayer(this.player3Status, this.txtFldPlayer3, 2);
        this.createActivePlayer(this.player4Status, this.txtFldPlayer4, 3);

        Player[] players = new Player[this.trackPlayers.size()];
        for (int i = 0; i < players.length; i++) {
            players[i] = this.trackPlayers.get(i);
        }

        if (players.length == 0) {
            players = new Player[]{new Player(JavaFxGUI.PLAYER_DEFAULT_NAME, PlayerStatus.AI, 0)};
        }

        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
        this.userInterfaceController.startGame(players);
    }

    private void createActivePlayer(ToggleGroup status, TextField playerName, int index) {
        RadioButton playerStatus = (RadioButton) status.getSelectedToggle();
        if (playerStatus != null) {
            String statusText = playerStatus.getText();
            PlayerStatus playerStatusValue = switch (statusText) {
                case JavaFxGUI.STATUS_INACTIVE -> PlayerStatus.INACTIVE;
                case JavaFxGUI.STATUS_USER -> PlayerStatus.USER;
                case JavaFxGUI.STATUS_AI -> PlayerStatus.AI;
                default -> null;
            };

            if (!statusText.equals(JavaFxGUI.STATUS_INACTIVE)) {
                this.trackPlayers.add(new Player(playerName.getText(), playerStatusValue, index));
            }
        }
    }
}
