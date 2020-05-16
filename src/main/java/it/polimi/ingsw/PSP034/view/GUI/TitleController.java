package it.polimi.ingsw.PSP034.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.io.File;

public class TitleController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ImageView imageViewBackground = new ImageView();

    @FXML
    private ImageView santoriniLogo;
    @FXML
    private Button play;

    @FXML
    private void initialize(){
        santoriniLogo.setImage(image);
        imageViewBackground.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        imageViewBackground.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        play.setId("buttonPlay");
    }

    public void setPlay(ActionEvent e){
        System.out.println("Game started");
    }
}
