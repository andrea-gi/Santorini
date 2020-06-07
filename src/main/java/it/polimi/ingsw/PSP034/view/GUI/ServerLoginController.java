package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

/**It controls the GUI scene of the connection to a server and to a port.
 */
public class ServerLoginController implements GUIController{
    @FXML
    private Pane pane;
    @FXML
    private TextField enterServerName;
    @FXML
    private TextField enterServerPort;
    @FXML
    private Button submit;
    @FXML
    private Label error;
    @FXML
    private Label connecting;
    @FXML
    private ProgressBar bar;

    private String serverName;
    private String serverPort;

    @Override
    public Pane getPane() {
        return pane;
    }

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        error.setId("error");
        bar.setVisible(false);
        connecting.setVisible(false);
    }

    /** Sends the chosen server ip and port and disables the Submit button and all the textfields.
     * If the server address and port are not filled, they are automatically set to "localhost" and "2020".
     * It changes the Submit button text to "Submitted" to highlight that the message has already
     * been sent.
     * @param e is the ActionEvent of the mouse click
     */
    public void setSubmit(ActionEvent e){
        serverName = enterServerName.getText().isEmpty() ? "localhost" : enterServerName.getText();
        serverPort = enterServerPort.getText().isEmpty() ? "2020" : enterServerPort.getText();
        bar.setVisible(true);
        connecting.setVisible(true);
        error.setVisible(false);
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        enterServerName.setDisable(true);
        enterServerPort.setDisable(true);
        GUIRequestHub.getInstance().setStartedConnection(GUIRequestHub.getInstance().createConnection(new AnswerIP(serverName, Integer.parseInt(serverPort))));
    }

    public void resetAfterError(){
        enterServerName.clear();
        enterServerPort.clear();
        enterServerName.setDisable(false);
        enterServerPort.setDisable(false);
        submit.setText("SUBMIT");
        submit.setDisable(false);
        error.setVisible(true);
        error.setText("Could not establish connection. Try again!");
        bar.setVisible(false);
        connecting.setVisible(false);
    }
}
