package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPersonalGod;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.Arrays;

public class PersonalGodController implements GUIController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ObservableList<String> godChoices = FXCollections.observableArrayList();

    @FXML
    private ImageView santoriniLogo;

    @FXML
    private Pane pane;

    @FXML
    private Button submit;

    @FXML
    private ChoiceBox<String> chooseGod;
    private boolean notValid;

    @FXML
    private void initialize() {
        GUIRequestHub.getInstance().setCurrentController(this);
        notValid = true;
        santoriniLogo.setImage(image);
        chooseGod.setValue("");
        submit.setDisable(notValid);
    }

    @FXML
    public void selectedGod() {
        chooseGod.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> pickedGod() );
    }

    @FXML
    public void pickedGod(){
        notValid = chooseGod.getSelectionModel().getSelectedItem().isEmpty();
        submit.setDisable(notValid);
    }

    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");

        GUIRequestHub.getInstance().sendAnswer(new AnswerPersonalGod(chooseGod.getAccessibleText()));
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void update(String[] possibleGods, String[] chosen){
        godChoices.addAll(Arrays.asList(possibleGods));
        chooseGod.setItems(godChoices);
        //TODO --gestire i chosen
    }
}
