package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.clientConfiguration.RequestIP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TitleController implements GUIController{

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button play;

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        anchorPane.setId("titleScene");
        play.setId("buttonPlay");
    }

    public void setPlay(ActionEvent e) {
        GUIRequestHub.getInstance().handleRequest(new RequestIP());
    }

    @Override
    public Pane getPane() {
        return anchorPane;
    }
}