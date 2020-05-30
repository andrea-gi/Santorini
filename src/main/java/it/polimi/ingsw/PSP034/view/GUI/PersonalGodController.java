package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPersonalGod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class PersonalGodController implements GUIController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    private final ToggleGroup gods = new ToggleGroup();
    private String chosenGod;

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
        one.setToggleGroup(gods);
        two.setToggleGroup(gods);
        three.setToggleGroup(gods);
        one.getStyleClass().remove("radio-button");
        two.getStyleClass().remove("radio-button");
        three.getStyleClass().remove("radio-button");
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        gods.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton) gods.getSelectedToggle();

            if (chosen != null) {
                if (chosen == one)
                    chosenGod = one.getText();
                else if (chosen == two)
                    chosenGod = two.getText();
                else if (chosen == three)
                    chosenGod = three.getText();
            }
        });
    }

    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");

        GUIRequestHub.getInstance().sendAnswer(new AnswerPersonalGod(chosenGod));
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void update(String[] possibleGods, String[] chosen){
        one.setVisible(true);
        three.setVisible(true);
        one.setText(possibleGods[0]);
        three.setText(possibleGods[1]);
        if (possibleGods.length == 3){
            two.setVisible(true);
            two.setText(possibleGods[2]);
        }
        for (String string: chosen) {
            if (string.equalsIgnoreCase(one.getText()))
                one.setDisable(true);
            if (string.equalsIgnoreCase(two.getText()))
                two.setDisable(true);
            if (string.equalsIgnoreCase(three.getText()))
                three.setDisable(true);
        }

    }
}
