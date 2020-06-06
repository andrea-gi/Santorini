package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.messages.serverConfiguration.AnswerNameColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

/**It controls the GUI scene of the user registration with a name and a color
 */
public class NameColorController implements GUIController{
    private final ToggleGroup colors = new ToggleGroup();

    @FXML
    private Pane pane;

    @FXML
    private TextField enterName;

    @FXML
    private Label error;

    @FXML
    private Button submit;

    @FXML
    private RadioButton red;

    @FXML
    private RadioButton blue;

    @FXML
    private RadioButton magenta;

    private boolean notValid;
    private PlayerColor myColor;
    private ArrayList<String> chosenNames;

    @Override
    public Pane getPane() {
        return pane;
    }

    public String getEnterName(){
        return enterName.getText();
    }

    public void setError(String string){
        error.setText(string);
        error.setWrapText(true);
        error.setId("error");
    }

    @FXML
    private void initialize(){
        GUIRequestHub.getInstance().setCurrentController(this);
        notValid = true;
        red.setToggleGroup(colors);
        blue.setToggleGroup(colors);
        magenta.setToggleGroup(colors);
        red.getStyleClass().remove("radio-button");
        red.setId("redButton");
        red.setDisable(true);
        blue.getStyleClass().remove("radio-button");
        blue.setId("blueButton");
        blue.setDisable(true);
        magenta.getStyleClass().remove("radio-button");
        magenta.setId("magentaButton");
        magenta.setDisable(true);
        colors.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton) colors.getSelectedToggle();

            if (chosen != null) {
                if (chosen == red)
                    myColor = PlayerColor.RED;
                else if (chosen == blue)
                    myColor = PlayerColor.BLUE;
                else if (chosen == magenta)
                    myColor = PlayerColor.MAGENTA;
            }
        });
        submit.setDisable(notValid);

        enterName.setDisable(true);
    }

    /**Invoked on key typed, it checks if the user has chosen a possible name and a color.
     *If so, it shows the Submit button
     */
    @FXML
    public void filledName(){
        notValid = getEnterName().isEmpty() || colors.getSelectedToggle() == null;

        submit.setDisable(notValid);
    }

    /**Invoked on the selection of a toggle, it checks if the user has chosen a possible name and a color.
     * If so, it shows the Submit button
     */
    @FXML
    public void filledColor(){
        notValid = getEnterName().isEmpty() || colors.getSelectedToggle() == null;
        submit.setDisable(notValid);
    }

    /** Sends the name and color chosen and disables the Submit button, the name Textfield and
     * all the colors buttons.
     * It changes the Submit button text to "Submitted" to highlight that the message has already
     * been sent.
     * @param e is the ActionEvent of the mouse click
     */
    @FXML
    public void setSubmit(ActionEvent e){
        submit.setDisable(true);
        submit.setText("SUBMITTED!");
        enterName.setDisable(true);

        if (!getEnterName().matches("^[a-zA-Z0-9_]+$")){
            setError("Only letters, numbers and underscores allowed");
            reInsert();
        }
        else if (getEnterName().length() > 20){
            setError("This name is too long (1-20 characters)");
            reInsert();
        }
        else if (chosenNames.contains(enterName.getText())){
            setError("This name has already been chosen");
            reInsert();
        }
        else {
            setError("");
            GUIRequestHub.getInstance().sendAnswer(new AnswerNameColor(getEnterName(), myColor));
            red.setDisable(true);
            blue.setDisable(true);
            magenta.setDisable(true);
        }
    }

    /** Clears the Textfield and disables the Submit buttons. Invoked after and error in the name input.     *
     */
    private void reInsert(){
        enterName.clear();
        enterName.setDisable(false);
        submit.setDisable(false);
        submit.setText("SUBMIT");
    }

    /** Updates the scene, saving the already chosen names, in order to immediately check if the user wants
     * to choose an unavailable name, and disabling the already chosen colors.     *
     * @param chosenNames are the names chosen by the other players
     * @param availableColors are the colors chosen by the other players
     */
    public void update(String[] chosenNames, PlayerColor[] availableColors){
        this.chosenNames = new ArrayList<>(Arrays.asList(chosenNames));
        enterName.setDisable(false);
        ArrayList<PlayerColor> colorsList = new ArrayList<>(Arrays.asList(availableColors));
        if (colorsList.contains(PlayerColor.MAGENTA))
            magenta.setDisable(false);
        if (colorsList.contains(PlayerColor.BLUE))
            blue.setDisable(false);
        if (colorsList.contains(PlayerColor.RED))
            red.setDisable(false);
    }
}
