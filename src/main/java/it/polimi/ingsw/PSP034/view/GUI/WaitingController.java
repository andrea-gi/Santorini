package it.polimi.ingsw.PSP034.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class WaitingController implements GUIController{
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());

    @FXML
    private ImageView santoriniLogo;

    @FXML
    private Pane pane;

    @FXML
    private Label title;

    @FXML
    private Label message;

    @Override
    public Pane getPane() {
        return pane;
    }

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        santoriniLogo.setImage(image);
    }

    public void setMyTitle(String string){
        title.setText(string);
    }

    public void setMyMessage(String string) {
        message.setText(string);
    }
}
