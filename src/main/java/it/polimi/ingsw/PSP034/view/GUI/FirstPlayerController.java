package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerFirstPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class FirstPlayerController implements GUIController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    private final ToggleGroup playersGroup = new ToggleGroup();
    private String chosenPlayer;

    @FXML
    private ImageView santoriniLogo;

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
        santoriniLogo.setImage(image);
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
            }
        });
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void update(String[] names){
        one.setVisible(true);
        three.setVisible(true);
        one.setText(names[0]);
        three.setText(names[1]);
        if (names.length == 3){
            two.setVisible(true);
            two.setText(names[2]);
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