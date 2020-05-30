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
import java.util.ArrayList;
import java.util.Arrays;

public class PersonalGodController implements GUIController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    private final ToggleGroup gods = new ToggleGroup();
    String chosenGod;
    private ArrayList<String> myGods = new ArrayList<>();

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
                if (chosen == one) {
                    chosenGod = one.getText();
                }
                else if (chosen == two)
                    chosenGod = two.getText();
                else if (chosen == three)
                    chosenGod = three.getText();
                setGodBackground(chosen, chosenGod, true);
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

    RadioButton previousButton = null;
    String previousGod = null;

    private void setGodBackground(RadioButton radioButton, String god, boolean golden){
        String path;
        if (golden){
            restorePreviousBackground();
            this.previousButton = radioButton;
            this.previousGod = god;
            path = GodPath.getGoldPath(god);
        }
        else{
            path = GodPath.getPath(god);
        }
        Image godCard = new Image(getClass().getResource(path).toExternalForm(), 215, 300, true, true);
        radioButton.setBackground(new Background(new BackgroundImage(
                godCard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));
    }

    private void restorePreviousBackground(){
        if (this.previousButton != null && this.previousGod != null) {
            this.previousButton.setBackground(Background.EMPTY);
            setGodBackground(this.previousButton, this.previousGod, false);
        }
    }

    public void update(String[] possibleGods, String[] chosen){
        myGods.addAll(Arrays.asList(possibleGods));
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
            setGodBackground(buttons[i], name, false);
            buttons[i].setText(name);
            buttons[i].setId("godButton");
            buttons[i].setVisible(true);
        }

    }
}
