package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPlaceWorker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

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
            powers[i].setImage(new Image(GodPath.getPower(godsList[i]), 149, 60, true, true));
            gods[i].setImage(new Image(GodPath.getPath(godsList[i]), 79, 137, true, true));
        }

    }

    Sex answerSex;

    public void updatePlaceWorker(Sex sex, PlayerColor color, Sex[][] alreadyOccupied){
        this.answerSex = sex;
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                getTileByIndex(i, j, gridTable).setId("freeTile");
                if (alreadyOccupied[i][j] == null){
                    getTileByIndex(i, j, gridTable).setDisable(false);
                }
            }
        }
    }

    public void onClickTile(MouseEvent e){
        int y = GridPane.getRowIndex((Node) e.getSource());
        int x = GridPane.getColumnIndex((Node) e.getSource());
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                getTileByIndex(i, j, gridTable).setDisable(true);
            }
        }
        GUIRequestHub.getInstance().sendAnswer(new AnswerPlaceWorker(answerSex, x, y));
    }

    public Node getTileByIndex (final int x, final int y, GridPane gridPane) {
        Node myTile = null;
        ObservableList<Node> board = gridPane.getChildren();

        for (Node tile : board) {
            if(GridPane.getRowIndex(tile) == y && GridPane.getColumnIndex(tile) == x) {
                myTile = tile;
                break;
            }
        }

        return myTile;
    }

    public void disableBoard(){
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                getTileByIndex(i, j, gridTable).setDisable(true);
            }
        }
    }

    private int[][] savedBuildings = new int[Constant.DIM][Constant.DIM];
    private Sex[][] savedWorkers = new Sex[Constant.DIM][Constant.DIM];
    private boolean[][] savedDomes = new boolean[Constant.DIM][Constant.DIM];
    private PlayerColor[][] savedColors = new PlayerColor[Constant.DIM][Constant.DIM];

    public void updateAndSaveBoard(SlimBoard slimBoard) {
        int[][] buildings = slimBoard.getBuilding();
        boolean[][] domes = slimBoard.getDome();
        Sex[][] sexes = slimBoard.getSex();
        PlayerColor[][] colors = slimBoard.getColor();

        for (int i = 0; i < Constant.DIM; i++) {
            for (int j = 0; j < Constant.DIM; j++) {
                if (buildings[i][j] != savedBuildings[i][j]
                        || domes[i][j] != savedDomes[i][j]
                        || sexes[i][j] != savedWorkers[i][j]
                        || colors[i][j] != savedColors[i][j]){
                    savedBuildings[i][j] = buildings[i][j];
                    savedDomes[i][j] = domes[i][j];
                    savedWorkers[i][j] = sexes[i][j];
                    savedColors[i][j] = colors[i][j];

                    printSavedTile(i, j);
                }
            }
        }
    }

    private Image maleRed = new Image("/images/MRed.png", 109.5, 109.5, true, true);
    private Image femaleRed = new Image("/images/FRed.png", 109.5, 109.5, true, true);
    private Image maleBlue = new Image("/images/MBlue.png", 109.5, 109.5, true, true);
    private Image femaleBlue = new Image("/images/FBlue.png", 109.5, 109.5, true, true);
    private Image maleMagenta = new Image("/images/MMagenta.png", 109.5, 109.5, true, true);
    private Image femaleMagenta = new Image("/images/FMagenta.png", 109.5, 109.5, true, true);

    private Image levelGround = new Image("/images/empty.png", 109.5, 109.5, true, true);
    private Image levelOne = new Image("/images/level1.png", 109.5, 109.5, true, true);
    private Image levelTwo = new Image("/images/level2.png", 109.5, 109.5, true, true);
    private Image levelThree = new Image("/images/level3.png", 109.5, 109.5, true, true);
    private Image dome = new Image("/images/dome.png", 109.5, 109.5, true, true);

    private void printSavedTile(int x, int y){
        StackPane tile = (StackPane) getTileByIndex(x,y, gridTable);
        ObservableList<Node> list = tile.getChildren();
        list.clear();
        printBuilding(x, y, list);
        printWorker(x, y, list);
    }

    private void printBuilding(int x, int y, ObservableList<Node> list){
        int buildingToPrint = savedBuildings[x][y];
        ArrayList<Image> buildingImages = new ArrayList<>();
        switch (buildingToPrint){
            case 0:
                buildingImages.add(levelGround);
                break;
            case 1:
                buildingImages.add(levelOne);
                break;
            case 2:
                buildingImages.add(levelTwo);
                break;
            case 3:
                buildingImages.add(levelThree);
                break;
        }
        if (savedDomes[x][y])
            buildingImages.add(dome);

        for (Image image: buildingImages){
            list.add(new ImageView(image));
        }

    }

    private void printWorker(int x, int y, ObservableList<Node> list){
        Sex sexToPrint = savedWorkers[x][y];
        PlayerColor colorToPrint = savedColors[x][y];
        Image workerImage = null;

        if (sexToPrint == null || colorToPrint == null){
            return;
        }

        if (sexToPrint == Sex.MALE){
            switch (colorToPrint){
                case BLUE:
                    workerImage = maleBlue;
                    break;
                case MAGENTA:
                    workerImage = maleMagenta;
                    break;
                case RED:
                    workerImage = maleRed;
                    break;
            }
        }
        else  if (sexToPrint == Sex.FEMALE){
            switch (colorToPrint){
                case BLUE:
                    workerImage = femaleBlue;
                    break;
                case MAGENTA:
                    workerImage = femaleMagenta;
                    break;
                case RED:
                    workerImage = femaleRed;
                    break;
            }
        }

        if (workerImage != null)
            list.add(new ImageView(workerImage));

    }

}
