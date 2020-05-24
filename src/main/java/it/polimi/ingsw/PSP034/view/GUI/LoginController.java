package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;


public class LoginController implements GUIController{
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    private final ToggleGroup colors = new ToggleGroup();

    @FXML
    private Pane pane;

    @FXML
    private Label title;

    @FXML
    private TextField enterName;

    @FXML
    private ImageView santoriniLogo;

    @FXML
    private Button submit;

    @FXML
    private RadioButton red;

    @FXML
    private RadioButton blue;

    @FXML
    private RadioButton magenta;

    private boolean notValid;
    private Color myColor;

    @Override
    public Pane getPane() {
        return pane;
    }

    public String getEnterName(){
        return enterName.getText();
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
        blue.getStyleClass().remove("radio-button");
        blue.setId("blueButton");
        magenta.getStyleClass().remove("radio-button");
        magenta.setId("magentaButton");
        colors.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton) colors.getSelectedToggle();

            if (chosen != null) {
                if (chosen == red)
                    myColor = Color.RED;
                else if (chosen == blue)
                    myColor = Color.BLUE;
                else if (chosen == magenta)
                    myColor = Color.MAGENTA;
            }
        });
        santoriniLogo.setImage(image);
        submit.setDisable(notValid);
    }

    @FXML
    public void filledName(){
        notValid = getEnterName().isEmpty() || colors.getSelectedToggle() == null;
        submit.setDisable(notValid);
    }

    @FXML
    public void filledColor(){
        notValid = getEnterName().isEmpty() || colors.getSelectedToggle() == null;
        submit.setDisable(notValid);
    }

    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        enterName.setDisable(true);
        //ScenePath.setNextScene(pane.getScene(), ScenePath.CHOOSE_GODS);
    }

}
