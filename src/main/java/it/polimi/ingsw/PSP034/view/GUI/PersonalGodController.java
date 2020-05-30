package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPersonalGod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class PersonalGodController implements GUIController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    private final ToggleGroup gods = new ToggleGroup();
    String chosenGod;

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
        one.setId("god");
        two.getStyleClass().remove("radio-button");
        two.setId("god");
        three.getStyleClass().remove("radio-button");
        three.setId("god");
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
    public void setChosenGod() {
        submit.setDisable(gods.getSelectedToggle() == null);
    }

    @FXML
    public void setSubmit(ActionEvent e){
        one.setDisable(true);
        two.setDisable(true);
        three.setDisable(true);
        submit.setDisable(true);
        submit.setText("SUBMITTED!");

        GUIRequestHub.getInstance().sendAnswer(new AnswerPersonalGod(chosenGod));
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void update(String[] possibleGods, String[] chosen){
        RadioButton[] buttons = new RadioButton[]{one, two, three};
        String name;
        for (int i = 0; i < possibleGods.length+chosen.length; i++){
            if (i < possibleGods.length){
                name = possibleGods[i];
            }
            else{
                name = chosen[i-(possibleGods.length)];
                buttons[i].setDisable(true);
            }
            Image godCard = new Image(getClass().getResource(GodPath.getPath(name)).toExternalForm(), 215, 300, true, true);
            buttons[i].setBackground(new Background(new BackgroundImage(godCard,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
            buttons[i].setText(name);
            buttons[i].setId("godButton");
            buttons[i].setVisible(true);
        }

    }
}
