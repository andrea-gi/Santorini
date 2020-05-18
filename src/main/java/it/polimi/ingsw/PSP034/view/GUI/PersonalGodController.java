package it.polimi.ingsw.PSP034.view.GUI;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class PersonalGodController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ObservableList<String> godChoices = FXCollections.observableArrayList("Apollo", "Artemis", "Athena");

    @FXML
    private ImageView santoriniLogo;

    @FXML
    private Button submit;

    @FXML
    private ChoiceBox<String> chooseGod;
    private boolean notValid;

    @FXML
    private void initialize() {
        notValid = true;
        santoriniLogo.setImage(image);
        chooseGod.setValue("");
        chooseGod.setItems(godChoices);
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
        System.out.println("Chosen god: " + chooseGod.getValue());
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
    }

}
