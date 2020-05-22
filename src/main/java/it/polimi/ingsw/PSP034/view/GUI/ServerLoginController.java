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

public class ServerLoginController {
    File file = new File("src\\main\\resources\\images\\santorini.jpg");
    Image image = new Image(file.toURI().toString());
    ImageView imageViewBackground = new ImageView();

    @FXML
    private Pane pane;
    @FXML
    private ImageView santoriniLogo;
    @FXML
    private TextField enterServerName;
    @FXML
    private TextField enterServerPort;
    @FXML
    private Button submit;

    private String serverName;
    private String serverPort;

    public String getEnterServerName() {
        return enterServerName.getText();
    }

    public String getEnterServerPort() {
        return enterServerPort.getText();
    }

    @FXML
    private void initialize(){
        santoriniLogo.setImage(image);
        imageViewBackground.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        imageViewBackground.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
    }

    public void setSubmit(ActionEvent e){
        if (getEnterServerName().isEmpty())
            serverName = "localhost";
        else
            serverName = getEnterServerName();
        if (getEnterServerPort().isEmpty())
            serverPort = "2020";
        else
            serverPort = getEnterServerPort();
        System.out.println(serverName);
        System.out.println("Port number: " + serverPort);
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        ScenePath.setNextScene(pane.getScene(), ScenePath.WAITING);
    }
}
