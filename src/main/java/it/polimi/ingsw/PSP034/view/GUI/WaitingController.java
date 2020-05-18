package it.polimi.ingsw.PSP034.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.File;

public class WaitingController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ImageView imageViewBackground = new ImageView();

    @FXML
    private ImageView santoriniLogo;

    @FXML
    private Label lobby;

    @FXML
    private void initialize(){
        santoriniLogo.setImage(image);
        imageViewBackground.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        imageViewBackground.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
    }
}
