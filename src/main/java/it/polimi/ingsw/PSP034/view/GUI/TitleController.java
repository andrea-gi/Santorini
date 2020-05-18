package it.polimi.ingsw.PSP034.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class TitleController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    File filePos = new File("src\\main\\resources\\images\\title_poseidon.png");
    Image imagePos = new Image(filePos.toURI().toString());
    File fileAph = new File("src\\main\\resources\\images\\title_aphrodite.png");
    Image imageAph = new Image(fileAph.toURI().toString());
    File cloud = new File("src\\main\\resources\\images\\cloud3_modeselect.png");
    Image myCloud = new Image(cloud.toURI().toString());

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView santoriniLogo;
    @FXML
    private Button play;
    @FXML
    private ImageView poseidon;
    @FXML
    private ImageView aphrodite;
    @FXML
    private ImageView clouds;
    @FXML
    private ImageView clouds2;

    @FXML
    private void initialize(){
        anchorPane.setId("title");
        santoriniLogo.setImage(image);
        poseidon.setImage(imagePos);
        aphrodite.setImage(imageAph);
        clouds.setImage(myCloud);
        clouds2.setImage(myCloud);
        play.setId("buttonPlay");
    }

    public void setPlay(ActionEvent e){
        System.out.println("Game started");
    }
}