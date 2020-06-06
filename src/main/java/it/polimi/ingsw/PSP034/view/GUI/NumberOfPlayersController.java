package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerNumber;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

/**It controls the GUI scene of the number of players choice by the first connected player.
 */
public class NumberOfPlayersController implements GUIController {
    @FXML
    private Pane pane;

    @FXML
    private Button submit;

    @FXML
    private RadioButton two;

    @FXML
    private RadioButton three;

    private int number;

    private final ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        submit.setDisable(true);
        two.setToggleGroup(toggleGroup);
        three.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton)toggleGroup.getSelectedToggle();

            if (chosen != null) {
                number = Integer.parseInt(chosen.getText());
                submit.setDisable(false);
            }
        });
    }

    public void getSelectedToggle() {
        submit.setDisable(toggleGroup.getSelectedToggle() == null);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public int getPlayersNumber(){
        return number;
    }

    /** Sends the number of players chosen and disables the Submit button and all the numbers buttons.
     * It changes the Submit button text to "Submitted" to highlight that the message has already
     * been sent.
     * @param e is the ActionEvent of the mouse click
     */
    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        GUIRequestHub.getInstance().sendAnswer(new AnswerNumber(number));
    }
}
