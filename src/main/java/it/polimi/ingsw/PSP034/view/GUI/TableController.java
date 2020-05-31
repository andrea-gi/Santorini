package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TableController implements GUIController{
    @FXML
    private Pane pane;

    @FXML
    private GridPane gridTable;

    @FXML
    private GridPane gridCard;

    @FXML
    private Label title;

    @FXML
    private Label message;

    @FXML
    private ImageView cardOne;

    @FXML
    private ImageView cardTwo;

    @FXML
    private ImageView cardThree;

    @FXML
    private Label nameOne;

    @FXML
    private Label nameTwo;

    @FXML
    private Label nameThree;

    @FXML
    private ImageView godOne;

    @FXML
    private ImageView powerOne;

    @FXML
    private ImageView godTwo;

    @FXML
    private ImageView powerTwo;

    @FXML
    private ImageView godThree;

    @FXML
    private ImageView powerThree;

    @FXML
    private void initialize() {
        GUIRequestHub.getInstance().setCurrentController(this);
        pane.setId("boardScene");
        title.setId("boardTitle");
        message.setId("boardSubtitle");
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void setMyTitle(String string){
        title.setText(string);
    }

    public void setMyMessage(String string){
        message.setText(string);
    }

    public void updateCards(String[] godsList, String[] playersList, PlayerColor[] colorsList){
        ImageView[] cards = new ImageView[]{cardOne, cardTwo, cardThree};
        Label[] names = new Label[]{nameOne, nameTwo, nameThree};
        ImageView[] gods = new ImageView[]{godOne, godTwo, godThree};
        ImageView[] powers = new ImageView[]{powerOne, powerTwo, powerThree};
        for (int i = 0; i < godsList.length; i++){
            names[i].setText(playersList[i]);
            names[i].setId("boardPlayerName");
            if (colorsList[i] == PlayerColor.BLUE) {
                cards[i].setImage(new Image("/images/cards/CompleteCardBlue.png"));
            }
            else if (colorsList[i] == PlayerColor.MAGENTA){
                cards[i].setImage(new Image("/images/cards/CompleteCardMagenta.png"));
            }
            else if (colorsList[i] == PlayerColor.RED) {
                cards[i].setImage(new Image("/images/cards/CompleteCardRed.png"));
            }
            powers[i].setImage(new Image(GodPath.getPower(godsList[i])));
            gods[i].setImage(new Image(GodPath.getPath(godsList[i])));
        }
    }
}
