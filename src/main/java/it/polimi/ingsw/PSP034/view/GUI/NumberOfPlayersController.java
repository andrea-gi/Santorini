package it.polimi.ingsw.PSP034.view.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class NumberOfPlayersController implements GUIController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());

    @FXML
    private ImageView santoriniLogo;

    @FXML
    private Pane pane;

    @FXML
    private Button submit;

    @FXML
    private RadioButton two;

    @FXML
    private RadioButton three;

    private int number;

    private final ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        submit.setDisable(true);
        two.setToggleGroup(toggleGroup);
        three.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton)toggleGroup.getSelectedToggle();

            if (chosen != null) {
                number = Integer.parseInt(chosen.getText());
                submit.setDisable(false);
            }
        });
        santoriniLogo.setImage(image);
    }

    public void getSelectedToggle() {
        submit.setDisable(false);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public int getPlayersNumber(){
        return number;
    }

    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        ScenePath.setNextScene(pane.getScene(), ScenePath.LOGIN);
    }
}
