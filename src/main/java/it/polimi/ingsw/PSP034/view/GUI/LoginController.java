package it.polimi.ingsw.PSP034.view.GUI;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;


public class LoginController {
    File file = new File("..\\ing-sw-2020-giarduz-grosso-guerrieri\\src\\main\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ObservableList<String> colorChoices = FXCollections.observableArrayList("White", "Grey", "Blue");

    @FXML
    private Pane pane;

    @FXML
    private Label title;

    @FXML
    private TextField enterName;

    @FXML
    private ImageView santoriniLogo;

    private boolean notValid;

    @FXML
    private ChoiceBox<String> chooseColor;

    @FXML
    public void selectedColor() {
        chooseColor.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> filledColor() );
    }

    @FXML
    private Button submit;

    public String getEnterName(){
        return enterName.getText();
    }

    @FXML
    private void initialize(){
        notValid = true;
        chooseColor.setValue("");
        chooseColor.setItems(colorChoices);
        santoriniLogo.setImage(image);
        submit.setDisable(notValid);
    }

    @FXML
    public void filledName(){
        notValid = getEnterName().isEmpty() || chooseColor.getSelectionModel().getSelectedItem().isEmpty();
        submit.setDisable(notValid);
    }

    @FXML
    public void filledColor(){
        notValid = getEnterName().isEmpty() || chooseColor.getSelectionModel().getSelectedItem().isEmpty();
        submit.setDisable(notValid);
    }

    @FXML
    public void setSubmit(ActionEvent e){
        System.out.println(getEnterName());
        System.out.println(chooseColor.getValue());
    }

}
