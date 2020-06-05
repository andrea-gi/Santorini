package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.playPhase.AnswerAction;
import it.polimi.ingsw.PSP034.messages.playPhase.AnswerBooleanChoice;
import it.polimi.ingsw.PSP034.messages.playPhase.RequestAction;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;
import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPlaceWorker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

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
    private ImageView usePower;

    @FXML
    private Label description;

    @FXML
    private Button submitPower;

    @FXML
    private ToggleButton togglePower;

    Label victoryMessage = new Label();

    private final boolean[][] canSend = new boolean[Constant.DIM][Constant.DIM];

    @FXML
    private void initialize() {
        GUIRequestHub.getInstance().setCurrentController(this);
        pane.setId("boardScene");
        title.setId("boardTitle");
        message.setId("boardSubtitle");
        description.setId("boardSubtitle");
        togglePower.setVisible(false);
        submitPower.setVisible(false);
        togglePower.setId("selectPower");
        submitPower.setDisable(true);
        usePower.setImage(new Image("images/box.png"));
        usePower.setVisible(true);
        victoryMessage.setId("victoryLabel");
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    void setMyTitle(String string){
        title.setText(string);
    }

    void setMyDescription(String string){
        description.setText(string);
    }

    void setVisibleBox(boolean bool){
        usePower.setVisible(bool);
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
                cards[i].setImage(new Image("/images/cards/CompleteCardBlue.png", 189, 280, true, true));
            }
            else if (colorsList[i] == PlayerColor.MAGENTA){
                cards[i].setImage(new Image("/images/cards/CompleteCardMagenta.png", 189, 280, true, true));
            }
            else if (colorsList[i] == PlayerColor.RED) {
                cards[i].setImage(new Image("/images/cards/CompleteCardRed.png", 189, 280, true, true));
            }
            powers[i].setImage(new Image(GodPath.getPower(godsList[i]), 149, 60, true, true));
            gods[i].setImage(new Image(GodPath.getPath(godsList[i]), 105, 137, true, true));
        }

    }

    Sex answerSex;

    private enum CurrentPhase{
        PLACE_WORKER,
        WORKER_CHOICE,
        MOVE_REQUEST,
        BUILD_REQUEST,
        POWER_REQUEST;
    }

    private CurrentPhase currentPhase;

    public void updatePlaceWorker(Sex sex, Sex[][] alreadyOccupied){
        currentPhase = CurrentPhase.PLACE_WORKER;
        this.answerSex = sex;
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                if (alreadyOccupied[i][j] == null){
                    enableTile(i, j);
                }
                else{
                    disableTile(i, j);
                }
            }
        }
    }

    public void updatePower(){
        togglePower.setVisible(true);
        submitPower.setVisible(true);
        submitPower.setDisable(false);
        togglePower.setDisable(false);
        togglePower.setSelected(false);
    }

    Rectangle blackOverlay = new Rectangle(1280,720, Color.rgb(0,0,0,0.6));

    public void updateWin(String winner, String loser){
        Image winnerGod;
        pane.getChildren().add(blackOverlay);
        winnerGod = getGodImageByUser(winner);
        if (winnerGod!=null)
            pane.getChildren().add(new ImageView(winnerGod));
        pane.getChildren().add(new ImageView(new Image("/images/victory.png", 1280, 720, true, true)));
        victoryMessage.setText("YOU WIN!");
        pane.getChildren().add(victoryMessage);
        StackPane.setAlignment( victoryMessage, Pos.BOTTOM_CENTER );
        if (!loser.isEmpty()){
            setCardOpacity(loser, 0.4);
            Label loserMessage = new Label(loser + " lost! That means...");
            loserMessage.setId("loserLabel");
            pane.getChildren().add(loserMessage);
            StackPane.setAlignment(loserMessage, Pos.TOP_CENTER);
        }
    }

    private AnchorPane getCardByUser(String name){
        Node card = null;
        if(name.equals(nameOne.getText()))
            card = getTileByIndex(0,0, gridCard);
        else if (name.equals(nameTwo.getText()))
            card = getTileByIndex(1,0, gridCard);
        else if (name.equals(nameThree.getText()))
            card = getTileByIndex(2,0, gridCard);

        if (card instanceof AnchorPane)
            return (AnchorPane) card;
        else return null;
    }

    void setCardOpacity(String name, double opacity){
        AnchorPane loserCard = getCardByUser(name);
        if (loserCard != null){
            loserCard.setOpacity(opacity);
        }
    }

    private Image getGodImageByUser(String name){
        Image image = null;
        if(name.equals(nameOne.getText())) {
            if (godOne.getImage() != null)
                image = new Image(godOne.getImage().getUrl());
        }
        else if (name.equals(nameTwo.getText())) {
            if (godTwo.getImage() != null)
                image = new Image(godTwo.getImage().getUrl());
        }
        else if (name.equals(nameThree.getText())) {
            if (godThree.getImage() != null)
                image = new Image(godThree.getImage().getUrl());
        }

        return image;
    }

    public void updateLose(String winner, String[] losers){
        ImageView victory;
        pane.getChildren().add(blackOverlay);
        victoryMessage.setText("YOU LOST");
        Image winnerGod = getGodImageByUser(winner);

        for (String loser : losers) {
            setCardOpacity(loser, 0.4);
        }

        Label winnerName = new Label();
        if (!winner.isEmpty()) {
            victory  = new ImageView(new Image("images/victory.png", 1280, 720, true, true));
            if (winnerGod!=null)
                pane.getChildren().add(new ImageView(winnerGod));
            //StackPane.setAlignment(winnerGod, Pos.CENTER);
            winnerName.setText(winner + " won! That means...");
            winnerName.setId("loserLabel");
            pane.getChildren().add(winnerName);
            StackPane.setAlignment(winnerName, Pos.TOP_CENTER);
            StackPane.setAlignment( victoryMessage, Pos.BOTTOM_CENTER );
        }
        else{
            //keep watching or quit
            victory = new ImageView(new Image("/images/defeat.png", 1280, 720, true, true));
            RadioButton keepWatching = new RadioButton();
            RadioButton quit = new RadioButton();
            ToggleGroup buttons = new ToggleGroup();
            keepWatching.setToggleGroup(buttons);
            keepWatching.getStyleClass().remove("radio-button");
            quit.getStyleClass().remove("radio-button");
            keepWatching.setText("Keep Watching");
            keepWatching.setWrapText(true);
            keepWatching.setId("keepWatching");
            quit.setToggleGroup(buttons);
            quit.setText("EXIT");
            quit.setId("quit");
            quit.setMinSize(55, 53);
            quit.setMaxSize(55,53);
            keepWatching.setMinSize(60, 60);
            keepWatching.setMaxSize(60, 60);
            pane.getChildren().add(quit);
            StackPane.setAlignment(quit, Pos.BOTTOM_LEFT);
            StackPane.setMargin(quit, new Insets(0,90,90,0));
            keepWatching.setToggleGroup(buttons);
            pane.getChildren().add(keepWatching);
            StackPane.setAlignment(keepWatching, Pos.BOTTOM_LEFT);
            StackPane.setMargin(keepWatching, new Insets(0, 90,10,0));
            quit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
            ImageView finalVictory = victory;
            keepWatching.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
                keepWatching.setVisible(false);
                quit.setVisible(false);
                victoryMessage.setVisible(false);
                winnerName.setVisible(false);
                finalVictory.setVisible(false);
                blackOverlay.setVisible(false);
            });
            StackPane.setAlignment( victoryMessage, Pos.CENTER );
        }
        pane.getChildren().add(victory);
        pane.getChildren().add(victoryMessage);
    }

    public void onClickTile(MouseEvent e){
        int y = GridPane.getRowIndex((Node) e.getSource());
        int x = GridPane.getColumnIndex((Node) e.getSource());

        if (!canSend[x][y]) {
            return; //TODO -- gestione input errato
        }

        Answer answerToSend = null;
        switch (currentPhase){
            case PLACE_WORKER: //setup
                answerToSend = new AnswerPlaceWorker(answerSex, x, y);
                break;
            case WORKER_CHOICE: //play action
                chooseActionWorker(x,y);
                currentPhase = playActionPhase == TurnPhase.MOVE ? CurrentPhase.MOVE_REQUEST : CurrentPhase.BUILD_REQUEST;
                break;
            case MOVE_REQUEST:
            case BUILD_REQUEST:
                if ((answerSex == Sex.MALE && x == femaleXAction && y == femaleYAction)
                        || (answerSex == Sex.FEMALE && x == maleXAction && y == maleYAction))
                    chooseActionWorker(x, y);
                else {
                    if (answerSex == Sex.MALE)
                        answerToSend = new AnswerAction(answerSex, Directions.offsetToDirection(x - maleXAction, y - maleYAction));
                    else if (answerSex == Sex.FEMALE)
                        answerToSend = new AnswerAction(answerSex, Directions.offsetToDirection(x - femaleXAction, y - femaleYAction));
                }
                break;
        }
        if (answerToSend != null){
            disableAll();
            GUIRequestHub.getInstance().sendAnswer(answerToSend); //TODO gestire errore click su disable
        }

    }

    public void onClickSubmitPower(ActionEvent e){
        GUIRequestHub.getInstance().sendAnswer(new AnswerBooleanChoice(togglePower.isSelected()));
        submitPower.setDisable(true);
        togglePower.setDisable(true);
        submitPower.setVisible(false);
        togglePower.setVisible(false);
    }

    private void chooseActionWorker(int x, int y){
        disableAll();
        Directions[] chosenDirections = null;
        if (x == maleXAction && y == maleYAction){
            answerSex = Sex.MALE;
            chosenDirections = maleDirections;
            if(femaleDirections.length > 0)
                enableTile(femaleXAction, femaleYAction);
        }
        else if (x == femaleXAction && y == femaleYAction){
            answerSex = Sex.FEMALE;
            chosenDirections = femaleDirections;
            if(maleDirections.length > 0)
                enableTile(maleXAction, maleYAction);
        }
        highlightPossibleTiles(x, y, chosenDirections);
    }

    public Node getTileByIndex (final int x, final int y, GridPane gridPane) { //TODO -- creare due metodi --> valore corretto senza cast in out
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

    private void enableTile(int x, int y){
        canSend[x][y] = true;
        ObservableList<Node> childrenStack = ((StackPane) getTileByIndex(x, y, gridTable)).getChildren();
        int stackSize = childrenStack.size();
        if (stackSize == 0){
            childrenStack.add(new ImageView(overlay));
            stackSize = childrenStack.size();
        }

        if (childrenStack.get(stackSize-1).getId() != null && childrenStack.get(stackSize-1).getId().equals("workerOnBoard"))
            childrenStack.get(stackSize-2).setId("enabledTile");
        else
            childrenStack.get(stackSize-1).setId("enabledTile");
    }

    private void disableTile(int x, int y){
        canSend[x][y] = false;
        ObservableList<Node> childrenStack = ((StackPane) getTileByIndex(x, y, gridTable)).getChildren();
        int stackSize = childrenStack.size();
        if (stackSize == 0){
            childrenStack.add(new ImageView(overlay));
            stackSize = childrenStack.size();
        }

        if (childrenStack.get(stackSize-1).getId() != null && childrenStack.get(stackSize-1).getId().equals("workerOnBoard"))
            childrenStack.get(stackSize-2).setId("disabledTile");
        else
            childrenStack.get(stackSize-1).setId("disabledTile");
    }

    private void disableAll(){
        for (int i = 0; i < Constant.DIM; i++){
            for (int j = 0; j < Constant.DIM; j++){
                disableTile(i, j);
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

    private Directions[] maleDirections = null;
    private Directions[] femaleDirections = null;
    private int maleXAction;
    private int maleYAction;
    private int femaleXAction;
    private int femaleYAction;
    private TurnPhase playActionPhase = null;

    public void updatePlayAction(RequestAction request){
        maleDirections = request.getPossibleMaleDirections();
        femaleDirections = request.getPossibleFemaleDirections();
        maleXAction = request.getXMale();
        maleYAction = request.getYMale();
        femaleXAction = request.getXFemale();
        femaleYAction = request.getYFemale();
        playActionPhase = request.getNextPhase();

        currentPhase = CurrentPhase.WORKER_CHOICE;

        if (request.getRequiredActions()[0] == RequiredActions.REQUEST_WORKER){
            if (request.getPossibleMaleDirections().length > 0)
                enableTile(request.getXMale(), request.getYMale());
            if (request.getPossibleFemaleDirections().length > 0)
                enableTile(request.getXFemale(), request.getYFemale());
        }
        else if (request.getRequiredActions()[0] == RequiredActions.REQUIRED_MALE)
            enableTile(request.getXMale(), request.getYMale());
        else if (request.getRequiredActions()[0] == RequiredActions.REQUIRED_FEMALE)
            enableTile(request.getXFemale(), request.getYFemale());
    }

    private void highlightPossibleTiles(int startX, int startY, Directions[] possibleDirections){
        if (possibleDirections == null){
            //throw new GameException("GUI001", "Fatal error");
            //TODO -- eccezione
            return;
        }
        for (Directions direction: possibleDirections){
            int xOffset = Directions.directionToXOffset(direction);
            int yOffset = Directions.directionToYOffset(direction);
            enableTile(startX + xOffset, startY + yOffset);
        }
    }

    private Image maleRed = new Image("/images/workers/maleRed.png", 109.5, 109.5, true, true);
    private Image femaleRed = new Image("/images/workers/femaleRed.png", 109.5, 109.5, true, true);
    private Image maleBlue = new Image("/images/workers/maleBlue.png", 109.5, 109.5, true, true);
    private Image femaleBlue = new Image("/images/workers/femaleBlue.png", 109.5, 109.5, true, true);
    private Image maleMagenta = new Image("/images/workers/maleMagenta.png", 109.5, 109.5, true, true);
    private Image femaleMagenta = new Image("/images/workers/femaleMagenta.png", 109.5, 109.5, true, true);

    private Image levelGround = new Image("/images/buildings/empty.png", 109.5, 109.5, true, true);
    private Image levelOne = new Image("/images/buildings/level1.png", 109.5, 109.5, true, true);
    private Image levelTwo = new Image("/images/buildings/level2.png", 109.5, 109.5, true, true);
    private Image levelThree = new Image("/images/buildings/level3.png", 109.5, 109.5, true, true);
    private Image dome = new Image("/images/buildings/dome.png", 109.5, 109.5, true, true);
    private Image overlay = new Image("/images/buildings/overlay.png", 109.5, 109.5, true, true);

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

        buildingImages.add(overlay);

        for (Image image: buildingImages){
            ImageView imageView = new ImageView(image);
            list.add(imageView);
            if (image.equals(overlay))
                imageView.setId("disabledTile");
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

        if (workerImage != null) {
            ImageView imageView = new ImageView(workerImage);
            list.add(imageView);
            imageView.setId("workerOnBoard");
        }
    }

}
