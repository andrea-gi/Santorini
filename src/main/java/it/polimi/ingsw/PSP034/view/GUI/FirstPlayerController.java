package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerFirstPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**It controls the GUI scene of the first player choice.
 */
public class FirstPlayerController implements GUIController {
    private final ToggleGroup playersGroup = new ToggleGroup();
    private String chosenPlayer;

    @FXML
    private Pane pane;

    @FXML
    private Button submit;

    @FXML
    private RadioButton one;

    @FXML
    private RadioButton two;

    @FXML
    private RadioButton three;

    @FXML
    private void initialize() {
        GUIRequestHub.getInstance().setCurrentController(this);
        submit.setDisable(true);
        one.setToggleGroup(playersGroup);
        two.setToggleGroup(playersGroup);
        three.setToggleGroup(playersGroup);
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        playersGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton) playersGroup.getSelectedToggle();

            if (chosen != null) {
                if (chosen == one)
                    chosenPlayer = one.getText();
                else if (chosen == two)
                    chosenPlayer = two.getText();
                else if (chosen == three)
                    chosenPlayer = three.getText();
                submit.setDisable(false);
            }
        });
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    /** Shows the right number and the right username of all the players, so that the current player can
     * choose which one will start the game.
     * @param names is the array with all the players names
     */
    public void update(String[] names){
        one.setVisible(true);
        two.setVisible(true);
        one.setText(names[0]);
        two.setText(names[1]);
        if (names.length == 3){
            three.setVisible(true);
            three.setText(names[2]);
        }
    }

    /** Sends the name of the first player chosen and disables the Submit button and all the other options.
     * It changes the Submit button text to "Submitted" to highlight that the message has already
     * been sent.
     * @param e is the ActionEvent of the mouse click
     */
    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        one.setDisable(true);
        two.setDisable(true);
        three.setDisable(true);

        GUIRequestHub.getInstance().sendAnswer(new AnswerFirstPlayer(chosenPlayer));
    }
}
