package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerCardsChoice;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ChooseGodsController implements GUIController{
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ObservableSet<CheckBox> chosenGods = FXCollections.observableSet();
    ObservableSet<CheckBox> remainingGods = FXCollections.observableSet();

    @FXML
    private ImageView santoriniLogo;

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
    private Button submit;

    @FXML
    private Tooltip apollotip;
    @FXML
    private Tooltip artemistip;
    @FXML
    private Tooltip athenatip;
    @FXML
    private Tooltip atlastip;
    @FXML
    private Tooltip demetertip;
    @FXML
    private Tooltip hephaestustip;
    @FXML
    private Tooltip minotaurtip;
    @FXML
    private Tooltip pantip;
    @FXML
    private Tooltip prometheustip;
    @FXML
    private Tooltip heratip;
    @FXML
    private Tooltip hestiatip;
    @FXML
    private Tooltip limustip;
    @FXML
    private Tooltip tritontip;
    @FXML
    private Tooltip zeustip;

    private int number;
    private IntegerBinding numberOfSelectedGods = Bindings.size(chosenGods);
    private final ArrayList<String> name = new ArrayList<>();

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        submit.setDisable(true);
        santoriniLogo.setImage(image);
        ArrayList<Tooltip> tp = new ArrayList<>(Arrays.asList(apollotip, artemistip,athenatip, atlastip, demetertip,
                hephaestustip, heratip, hestiatip, minotaurtip, pantip, prometheustip, limustip, tritontip, zeustip));
        for (Tooltip t: tp ) {
            t.setShowDelay(Duration.seconds(0));
        }
        remainingGods.add(Apollo);
        remainingGods.add(Artemis);
        remainingGods.add(Athena);
        remainingGods.add(Atlas);
        remainingGods.add(Demeter);
        remainingGods.add(Hephaestus);
        remainingGods.add(Hera);
        remainingGods.add(Hestia);
        remainingGods.add(Minotaur);
        remainingGods.add(Pan);
        remainingGods.add(Prometheus);
        remainingGods.add(Limus);
        remainingGods.add(Triton);
        remainingGods.add(Zeus);

        for (CheckBox myGod: remainingGods) {
            setMyGods(myGod);
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
