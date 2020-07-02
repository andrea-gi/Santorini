package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.clientConfiguration.AnswerIP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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

        if (!checkIP(serverName)){
            resetAfterError("Invalid server address.");
            return;
        }

        if (!checkPort(serverPort)){
            resetAfterError("Invalid server port.");
            return;
        }

        GUIRequestHub.getInstance().setStartedConnection(GUIRequestHub.getInstance().createConnection(new AnswerIP(serverName, Integer.parseInt(serverPort))));
    }

    private boolean checkPort(String port){
        int parsedPort;
        try {
            parsedPort = Integer.parseInt(port);
        } catch (NumberFormatException e){
            return false;
        }
        return (parsedPort >= 0 && parsedPort <= 65535);
    }

    private boolean checkIP(String address){
        if (address.equals("localhost"))
            return true;
        if (address.length() == 0 || address.length() > 15)
            return false;
        String[] addressComponents = address.split("\\.");
        if (addressComponents.length > 4)
            return false;
        for (String chunk : addressComponents){
            int value;
            try{
                value = Integer.parseInt(chunk);
            } catch (NumberFormatException e){
                return false;
            }
            if (value < 0 || value > 255)
                return false;
        }
        return true;
    }

    public void resetAfterError(String myError){
        enterServerName.clear();
        enterServerPort.clear();
        enterServerName.setDisable(false);
        enterServerPort.setDisable(false);
        submit.setText("SUBMIT");
        submit.setDisable(false);
        error.setVisible(true);
        error.setText(myError);
        bar.setVisible(false);
        connecting.setVisible(false);
    }
}
