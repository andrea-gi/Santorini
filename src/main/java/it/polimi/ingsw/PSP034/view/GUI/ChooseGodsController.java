package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerCardsChoice;
import it.polimi.ingsw.PSP034.view.GodDescription;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**It controls the GUI scene of the divinity cards' choice by the god-like player.
 */
public class ChooseGodsController implements GUIController{
    ObservableSet<CheckBox> chosenGods = FXCollections.observableSet();
    ObservableSet<CheckBox> remainingGods = FXCollections.observableSet();

    @FXML
    private Pane pane;

    @FXML
    private CheckBox Apollo;
    @FXML
    private CheckBox Artemis;
    @FXML
    private CheckBox Athena;
    @FXML
    private CheckBox Atlas;
    @FXML
    private CheckBox Demeter;
    @FXML
    private CheckBox Hephaestus;
    @FXML
    private CheckBox Minotaur;
    @FXML
    private CheckBox Pan;
    @FXML
    private CheckBox Prometheus;
    @FXML
    private CheckBox Hera;
    @FXML
    private CheckBox Hestia;
    @FXML
    private CheckBox Limus;
    @FXML
    private CheckBox Triton;
    @FXML
    private CheckBox Zeus;

    @FXML
    private ImageView godImage;
    @FXML
    private ImageView powerImage;
    @FXML
    private Label powerDescription;

    @FXML
    private Button submit;

    private int number;
    private final IntegerBinding numberOfSelectedGods = Bindings.size(chosenGods);
    private final ArrayList<String> name = new ArrayList<>();

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        submit.setDisable(true);
        godImage.setVisible(false);
        powerImage.setVisible(false);
        powerDescription.setVisible(false);
        CheckBox[] allPossibleGods = new CheckBox[]{Apollo, Artemis, Athena, Atlas, Demeter, Hephaestus, Hera, Hestia,
            Minotaur, Pan, Prometheus, Limus, Triton, Zeus};

        remainingGods.addAll(Arrays.asList(allPossibleGods));

        for (CheckBox myGod: remainingGods) {
            setMyGods(myGod);
            myGod.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    godImage.setImage(new Image(GodPath.getPath(myGod.getText()), 280, 356, true, true));
                    godImage.setVisible(true);
                    powerImage.setImage(new Image(GodPath.getPower(myGod.getText()), 149, 60, true, true));
                    powerImage.setVisible(true);
                    powerDescription.setText(GodDescription.getPower(myGod.getText()));
                    powerDescription.setId("powerDescription");
                    powerDescription.setVisible(true);
                }
                else {
                    godImage.setVisible(false);
                    powerImage.setVisible(false);
                    powerDescription.setVisible(false);
                }
            });
        }

        numberOfSelectedGods.addListener((observer, oldNumber, newNumber) -> {
            if (number <= newNumber.intValue()) {
                remainingGods.forEach(god -> god.setDisable(true));
                submit.setDisable(false);
            } else {
                remainingGods.forEach(god -> god.setDisable(false));
                submit.setDisable(true);
            }
        });

    }

    /** Checks if the god is selected: if so, it is added to a list of chosen gods, else it is added
     * to the list of remaining gods.
     * @param myGod is the selected or unselected god
     */
    @FXML
    public void setMyGods(CheckBox myGod){
        if (myGod.isSelected()) {
            chosenGods.add(myGod);
        } else {
            remainingGods.add(myGod);
        }

        myGod.selectedProperty().addListener((observer, selectedGod, newGod) -> {
            if (newGod) {
                remainingGods.remove(myGod);
                chosenGods.add(myGod);
            } else {
                chosenGods.remove(myGod);
                remainingGods.add(myGod);
            }

        });

    }

    @Override
    public Pane getPane() {
        return pane;
    }


    /** Sends the chosen gods and disables the Submit button and all the gods Checkboxes.
     * It changes the Submit button text to "Submitted" to highlight that the message has already
     * been sent.
     * @param e is the ActionEvent of the mouse click
     */
    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        for (CheckBox myGod: remainingGods) {
            myGod.setDisable(true);
        }
        for (CheckBox myGod: chosenGods) {
            myGod.setDisable(true);
        }
        submit.setText("SUBMITTED!");
        for (CheckBox checkBox : chosenGods) {
            name.add(checkBox.getText());
        }
        GUIRequestHub.getInstance().sendAnswer(new AnswerCardsChoice(name.toArray(new String[0])));
    }

    public void update(int playerNumber){
        this.number = playerNumber;
    }

}
