package it.polimi.ingsw.PSP034.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**It controls the GUI scene of the waiting moments during the registration and setup of all the players.
 */
public class WaitingController implements GUIController{

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
    }

    public void setMyTitle(String string){
        title.setText(string);
    }

    public void setMyMessage(String string) {
        message.setText(string);
    }
}
