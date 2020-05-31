package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerNameColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class NameColorController implements GUIController{
    private final ToggleGroup colors = new ToggleGroup();

    @FXML
    private Pane pane;

    @FXML
    private TextField enterName;

    @FXML
    private Label error;

    @FXML
    private Button submit;

    @FXML
    private RadioButton red;

    @FXML
    private RadioButton blue;

    @FXML
    private RadioButton magenta;

    private boolean notValid;
    private PlayerColor myColor;
    private ArrayList<String> chosenNames;

    @Override
    public Pane getPane() {
        return pane;
    }

    public String getEnterName(){
        return enterName.getText();
    }

    public void setError(String string){
        error.setText(string);
        error.setWrapText(true);
        error.setId("error");
    }

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        notValid = true;
        red.setToggleGroup(colors);
        blue.setToggleGroup(colors);
        magenta.setToggleGroup(colors);
        red.getStyleClass().remove("radio-button");
        red.setId("redButton");
        red.setDisable(true);
        blue.getStyleClass().remove("radio-button");
        blue.setId("blueButton");
        blue.setDisable(true);
        magenta.getStyleClass().remove("radio-button");
        magenta.setId("magentaButton");
        magenta.setDisable(true);
        colors.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton) colors.getSelectedToggle();

            if (chosen != null) {
                if (chosen == red)
                    myColor = PlayerColor.RED;
                else if (chosen == blue)
                    myColor = PlayerColor.BLUE;
                else if (chosen == magenta)
                    myColor = PlayerColor.MAGENTA;
            }
        });
        submit.setDisable(notValid);

        enterName.setDisable(true);
    }

    @FXML
    public void filledName(){
        notValid = getEnterName().isEmpty() || colors.getSelectedToggle() == null;

        submit.setDisable(notValid);
    }

    @FXML
    public void filledColor(){
        notValid = getEnterName().isEmpty() || colors.getSelectedToggle() == null || getEnterName().length() > 20 || !getEnterName().matches("^[a-zA-Z0-9_]+$");
        submit.setDisable(notValid);
    }

    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        enterName.setDisable(true);

        if (!getEnterName().matches("^[a-zA-Z0-9_]+$")){
            setError("Only letters, numbers and underscores allowed");
            reInsert();
        }
        else if (getEnterName().length() > 20){
            setError("This name is too long (1-20 characters)");
            reInsert();
        }
        else if (chosenNames.contains(enterName.getText())){
            setError("This name has already been chosen");
            reInsert();
        }
        else {
            setError("");
            GUIRequestHub.getInstance().sendAnswer(new AnswerNameColor(getEnterName(), myColor));
            red.setDisable(true);
            blue.setDisable(true);
            magenta.setDisable(true);
        }
    }

    private void reInsert(){
        enterName.clear();
        enterName.setDisable(false);
        submit.setDisable(false);
        submit.setText("SUBMIT");
    }

    public void update(String[] chosenNames, PlayerColor[] availableColors){
        this.chosenNames = new ArrayList<>(Arrays.asList(chosenNames));
        enterName.setDisable(false);
        ArrayList<PlayerColor> colorsList = new ArrayList<>(Arrays.asList(availableColors));
        if (colorsList.contains(PlayerColor.MAGENTA))
            magenta.setDisable(false);
        if (colorsList.contains(PlayerColor.BLUE))
            blue.setDisable(false);
        if (colorsList.contains(PlayerColor.RED))
            red.setDisable(false);
    }
}
