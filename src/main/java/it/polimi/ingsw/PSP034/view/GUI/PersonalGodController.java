package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPersonalGod;
import it.polimi.ingsw.PSP034.view.GodDescription;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**It controls the GUI scene of the personal god choice between the cards picked by the god like player.
 */
public class PersonalGodController implements GUIController {
    private final ToggleGroup gods = new ToggleGroup();
    String chosenGod;
    //private ArrayList<String> myGods = new ArrayList<>();

    @FXML
    private Pane pane;

    @FXML
    private Button submit;

    @FXML
    private RadioButton one;

    @FXML
    private RadioButton two;

    @FXML
    private RadioButton three;

    @FXML
    private Label labelOne;

    @FXML
    private Label labelTwo;

    @FXML
    private Label labelThree;

    @FXML
    private ImageView powerOne;

    @FXML
    private ImageView powerTwo;

    @FXML
    private ImageView powerThree;

    @FXML
    private void initialize() {
        GUIRequestHub.getInstance().setCurrentController(this);
        submit.setDisable(true);
        one.setToggleGroup(gods);
        two.setToggleGroup(gods);
        three.setToggleGroup(gods);
        one.getStyleClass().remove("radio-button");
        one.setId("god");
        two.getStyleClass().remove("radio-button");
        two.setId("god");
        three.getStyleClass().remove("radio-button");
        three.setId("god");
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        powerOne.setVisible(false);
        powerTwo.setVisible(false);
        powerThree.setVisible(false);
        gods.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {

            RadioButton chosen = (RadioButton) gods.getSelectedToggle();

            if (chosen != null) {
                if (chosen == one) {
                    chosenGod = one.getText();
                }
                else if (chosen == two)
                    chosenGod = two.getText();
                else if (chosen == three)
                    chosenGod = three.getText();
                setGodBackground(chosen, chosenGod, true);
            }
        });
    }

    @FXML
    public void setChosenGod() {
        submit.setDisable(gods.getSelectedToggle() == null);
    }

    /** Sends the chosen god and disables the Submit button and all the other gods.
     * It changes the Submit button text to "Submitted" to highlight that the message has already
     * been sent.
     * @param e is the ActionEvent of the mouse click
     */
    @FXML
    public void setSubmit(ActionEvent e){
        one.setDisable(true);
        two.setDisable(true);
        three.setDisable(true);
        submit.setDisable(true);
        submit.setText("SUBMITTED!");

        GUIRequestHub.getInstance().sendAnswer(new AnswerPersonalGod(chosenGod));
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    RadioButton previousButton = null;
    String previousGod = null;


    /** Sets the god image according to selection. If the god is selected, it shows the god on a gold podium.
     * Otherwise, the podium is in grey marble.     *
     * @param radioButton is the selected or unselected god
     * @param god is the name of the god
     * @param golden indicates if the podium must be gold
     */
    private void setGodBackground(RadioButton radioButton, String god, boolean golden){
        String path;
        if (golden){
            restorePreviousBackground();
            this.previousButton = radioButton;
            this.previousGod = god;
            path = GodPath.getGoldPath(god);
        }
        else{
            path = GodPath.getPath(god);
        }
        Image godCard = new Image(getClass().getResource(path).toExternalForm(), 177, 226, true, true);
        radioButton.setBackground(new Background(new BackgroundImage(
                godCard,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));
    }

    /** Sets the old background on an unselected god.
     *
     */
    private void restorePreviousBackground(){
        if (this.previousButton != null && this.previousGod != null) {
            this.previousButton.setBackground(Background.EMPTY);
            setGodBackground(this.previousButton, this.previousGod, false);
        }
    }

    /** Updates the scene with the right possible gods and disables the already chosen ones.
     * @param possibleGods is the list of all the possible cards picked by the god like player
     * @param chosen are the gods already chosen by other players
     */
    public void update(String[] possibleGods, String[] chosen){
        //myGods.addAll(Arrays.asList(possibleGods));
        RadioButton[] buttons = new RadioButton[]{one, two, three};
        Label[] labels = new Label[]{labelOne, labelTwo, labelThree};
        ImageView[] powers = new ImageView[]{powerOne, powerTwo, powerThree};
        String name;
        for (int i = 0; i < possibleGods.length+chosen.length; i++){
            if (i < possibleGods.length){
                name = possibleGods[i];
            }
            else{
                name = chosen[i-(possibleGods.length)];
                buttons[i].setDisable(true);
            }
            setGodBackground(buttons[i], name, false);
            buttons[i].setText(name);
            labels[i].setWrapText(true);
            labels[i].setText(name.toUpperCase());
            labels[i].setId("godName");
            buttons[i].setId("godButton");
            buttons[i].setVisible(true);
            powers[i].setVisible(true);
            powers[i].setImage(new Image(GodPath.getPower(name), 119.2, 48, true, true));
            Tooltip powerTooltip = new Tooltip(GodDescription.getPower(name));
            Tooltip.install(powers[i],powerTooltip);
            powerTooltip.setShowDelay(Duration.seconds(0.01));
            powerTooltip.setMaxWidth(200);
        }

    }
}
