package it.polimi.ingsw.PSP034.view.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**It controls the GUI dialogs, blocking all interaction but the click on the Ok button.
 */
public class DialogController implements GUIController{

    @FXML
    private BorderPane pane;

    @FXML
    private Button ok;

    @FXML
    private Label title;

    @FXML
    private Label label;

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        pane.setId("dialogScene");
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void setLabel(String label) {
        this.label.setText(label);
        this.label.setId("dialogLabel");
    }

    public void setMyTitle(String title) {
        this.title.setText(title);
        this.title.setId("dialogTitle");
    }

    /**
     * Dismisses the dialog, unblocking the client and allowing them to handle other messages
     * If the dialog has an error message, it closes the application.
     * @param e is the ActionEvent of the mouse click
     */
    public void onOk(ActionEvent e){
        ScenePath.dismissDialog((Stage) pane.getScene().getWindow());
        GUIRequestHub.getInstance().setCanHandleRequest(true);
        if (title.getText().equals("Game Started") || title.getText().equals("Error")) {
            Platform.exit();
            System.exit(0);
        }
    }
}
