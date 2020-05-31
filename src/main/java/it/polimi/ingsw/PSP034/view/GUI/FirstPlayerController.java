package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerFirstPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;


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
