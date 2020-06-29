package it.polimi.ingsw.PSP034.view.GUI;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.SlimBoard;
import it.polimi.ingsw.PSP034.messages.playPhase.AnswerAction;
import it.polimi.ingsw.PSP034.messages.playPhase.AnswerBooleanChoice;
import it.polimi.ingsw.PSP034.messages.playPhase.RequestAction;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;
import it.polimi.ingsw.PSP034.messages.setupPhase.AnswerPlaceWorker;
import it.polimi.ingsw.PSP034.view.GodDescription;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.util.Duration;

import java.util.ArrayList;

/** It controls the GUI scene of the board
 */
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

    Sex answerSex;

    private CurrentPhase currentPhase;

    Rectangle blackOverlay = new Rectangle(1280,720, Color.rgb(0,0,0,0.6));

    private enum CurrentPhase{
        PLACE_WORKER,
        WORKER_CHOICE,
        MOVE_REQUEST,
        BUILD_REQUEST,
    }

    private enum EndGameButtons{
        PLAY_AGAIN,
        KEEP_WATCHING
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

    /** Gets the card using the player's name
     * @param name is the player's name
     * @return the anchorPane where it lays the player's card
     */
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

    /** Sets the opacity of the card in case of game over of a single player
     * @param name is the player's name
     * @param opacity indicates the level of opacity desired
     */
    void setCardOpacity(String name, double opacity){
        AnchorPane loserCard = getCardByUser(name);
        if (loserCard != null){
            loserCard.setOpacity(opacity);
        }
    }

    /** Gets the image of the god depending on the player's name
     * @param name is the name of the player
     * @return the god image associated to the player
     */
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

    /** Sets the cards on the left side of the scene depending on the number of players, their names, colors
     * and gods chosen. The lists given as parameters are in matching corresponding order
     * @param godsList is the list of gods
     * @param playersList is the list of players' names
     * @param colorsList is the list of colors
     */
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
            Tooltip powerTooltip = new Tooltip(GodDescription.getPower(godsList[i]));
            Tooltip.install(powers[i],powerTooltip);
            powerTooltip.setShowDelay(Duration.seconds(0.01));
            powerTooltip.setMaxWidth(200);
        }

    }

    /** Enables or disables the tiles depending on the presence of other players.
     * @param sex is the sex of the worker that the player is positioning on the board
     * @param alreadyOccupied is the matrix of workers already on the board
     */
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

    /** Shows and enables the toggle button for the choice of the power
     */
    public void updatePower(){
        togglePower.setVisible(true);
        submitPower.setVisible(true);
        submitPower.setDisable(false);
        togglePower.setDisable(false);
        togglePower.setSelected(false);
    }

    /** Sets the victory message showing the winner god
     * @param winner is the winner
     * @param loser is the loser, if there is any. Otherwise, is an empty string
     */
    public void updateWin(String winner, String loser){
        Image winnerGod;
        FadeTransition transOverlay = new FadeTransition(Duration.seconds(2), blackOverlay);
        transOverlay.setFromValue(.20);
        transOverlay.setToValue(1);
        transOverlay.play();
        pane.getChildren().add(blackOverlay);;
        winnerGod = getGodImageByUser(winner);
        ImageView winnerGodImageView = new ImageView(winnerGod);
        if (winnerGod!=null) {
            FadeTransition transGod = new FadeTransition(Duration.seconds(2), winnerGodImageView);
            transGod.setFromValue(0);
            transGod.setToValue(1);
            transGod.play();
            StackPane.setAlignment(winnerGodImageView, Pos.CENTER);
            StackPane.setMargin(winnerGodImageView, new Insets(0,0,100,0));
            pane.getChildren().add(winnerGodImageView);
        }
        ImageView victory = new ImageView(new Image("/images/victory.png", 1280, 720, true, true));
        FadeTransition transVictory = new FadeTransition(Duration.seconds(2), victory);
        transVictory.setFromValue(0);
        transVictory.setToValue(1);
        transVictory.play();
        pane.getChildren().add(victory);
        victoryMessage.setText("YOU WIN!");
        StackPane.setAlignment( victoryMessage, Pos.BOTTOM_CENTER );
        FadeTransition transMessage = new FadeTransition(Duration.seconds(2), victoryMessage);
        transMessage.setFromValue(0);
        transMessage.setToValue(1);
        transMessage.play();
        pane.getChildren().add(victoryMessage);
        Label loserMessage = null;
        if (!loser.isEmpty()){
            setCardOpacity(loser, 0.4);
            loserMessage = new Label(loser + " lost!");
            loserMessage.setId("loserLabel");
            pane.getChildren().add(loserMessage);
            StackPane.setAlignment(loserMessage, Pos.TOP_CENTER);
        }
        showButtonsEndGame(EndGameButtons.PLAY_AGAIN, winnerGodImageView, loserMessage);
    }

    /** Sets the losing message
     * @param winner is the winner name, if there is any. In this case, it shows the winner god.
     *               Otherwise, if there is no winner, it is an empty string
     * @param losers is the list of all the losers names, whose cards will be set with opacity, in order
     *               to immediately recognize who is no longer in the game
     */
    public void updateLose(String winner, String[] losers){
        ImageView victory;
        pane.getChildren().add(blackOverlay);
        FadeTransition transOverlay = new FadeTransition(Duration.seconds(2), blackOverlay);
        transOverlay.setFromValue(.20);
        transOverlay.setToValue(1);
        transOverlay.play();
        Image winnerGod = getGodImageByUser(winner);

        for (String loser : losers) {
            setCardOpacity(loser, 0.4);
        }

        Label winnerName = new Label();
        if (!winner.isEmpty()) {
            victory  = new ImageView(new Image("images/victory.png", 1280, 720, true, true));
            FadeTransition transVictory = new FadeTransition(Duration.seconds(2), victory);
            transVictory.setFromValue(0);
            transVictory.setToValue(1);
            transVictory.play();
            ImageView winnerGodImageView = new ImageView();
            if (winnerGod!=null) {
                winnerGodImageView = new ImageView(winnerGod);
                StackPane.setAlignment(winnerGodImageView, Pos.CENTER);
                StackPane.setMargin(winnerGodImageView, new Insets(0,0,100,0));
                pane.getChildren().add(winnerGodImageView);
                FadeTransition transGod = new FadeTransition(Duration.seconds(2), winnerGodImageView);
                transGod.setFromValue(0);
                transGod.setToValue(1);
                transGod.play();
            }
            winnerName.setText(winner + " won!");
            winnerName.setId("loserLabel");
            pane.getChildren().add(winnerName);
            StackPane.setAlignment(winnerName, Pos.TOP_CENTER);
            StackPane.setAlignment( victoryMessage, Pos.BOTTOM_CENTER );
            victoryMessage.setText("GAME OVER");
            showButtonsEndGame(EndGameButtons.PLAY_AGAIN, victory, winnerName);
        }
        else{
            //keep watching or quit
            victory = new ImageView(new Image("/images/defeat.png", 1280, 720, true, true));
            showButtonsEndGame(EndGameButtons.KEEP_WATCHING, victory, winnerName);
            StackPane.setAlignment( victoryMessage, Pos.CENTER );
            victoryMessage.setId("personalDefeat");
            victoryMessage.setText("YOU LOST");
        }
        pane.getChildren().add(victory);
        pane.getChildren().add(victoryMessage);
        FadeTransition transMessage = new FadeTransition(Duration.seconds(2), victoryMessage);
        transMessage.setFromValue(0);
        transMessage.setToValue(1);
        transMessage.play();
    }

    /** Shows the play again or keep watching button and the exit button at the end of a specific
     *  player's game
     * @param button it specifies if it is a play again or keep watching button
     * @param victory is the victory image to delete from screen
     * @param winnerName is the message to delete from screen
     */
    //TODO -- gestire il keep watching se non pigia il bottone
    private void showButtonsEndGame(EndGameButtons button, ImageView victory, Label winnerName){
        Button quit = new Button();
        quit.setContentDisplay(ContentDisplay.BOTTOM);
        quit.setId("quit");
        quit.setMinSize(70, 110);
        quit.setMaxSize(70,110);
        pane.getChildren().add(quit);
        StackPane.setAlignment(quit, Pos.BOTTOM_LEFT);
        StackPane.setMargin(quit, new Insets(0,0,15,10));
        quit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));

        Button continueGame = new Button();
        if(button == EndGameButtons.KEEP_WATCHING)
            continueGame.setId("keepWatching");
        else if(button == EndGameButtons.PLAY_AGAIN)
            continueGame.setId("playAgain");
        continueGame.setContentDisplay(ContentDisplay.BOTTOM);

        continueGame.setMinSize(75, 110);
        continueGame.setMaxSize(75, 110);

        pane.getChildren().add(continueGame);
        StackPane.setAlignment(continueGame, Pos.BOTTOM_LEFT);
        StackPane.setMargin(continueGame, new Insets(0, 0,10,110));

        if (button == EndGameButtons.KEEP_WATCHING) {
            continueGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                pane.getChildren().remove(continueGame);
                pane.getChildren().remove(quit);
                pane.getChildren().remove(victoryMessage);
                pane.getChildren().remove(winnerName);
                pane.getChildren().remove(victory);
                pane.getChildren().remove(blackOverlay);
            });
        }
        else if (button == EndGameButtons.PLAY_AGAIN){
            continueGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                restart();
            });
        }
    }

    /**Lets the game restart with the IP request
     */
    public void restart(){
        pane.getChildren().removeAll();
        Scene scene = GUIRequestHub.getInstance().getCurrentController().getPane().getScene();
        ScenePath.setNextScene(scene, ScenePath.SERVER_LOGIN);
    }

    /** Enables the interaction with a tile, depending on the game phase and the status of the tile
     *  (enabled or disables)
     * @param e is the mouse event of the click
     */
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

    /** Sends the information about the willingness of using the power
     * @param e is the action event of the click on the submit button
     */
    public void onClickSubmitPower(ActionEvent e){
        GUIRequestHub.getInstance().sendAnswer(new AnswerBooleanChoice(togglePower.isSelected()));
        submitPower.setDisable(true);
        togglePower.setDisable(true);
        submitPower.setVisible(false);
        togglePower.setVisible(false);
        message.setVisible(false);
    }

    /** Enables or disables only the tiles of the workers who can perform an action
     * @param x is the column index
     * @param y is the row index
     */
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

    /** Gets the tile depending on its column and row indexes.
     * @param x is the column index
     * @param y is the row index
     * @param gridPane is the gridPane selected
     * @return the right tile
     */
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

    /** Highlights only the possible tiles where the selected worker can perform an action
     * @param startX is the column index of the worker
     * @param startY is the row index of the worker
     * @param possibleDirections is the array of possible directions
     */
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

    /**Enables the tile, allowing responsiveness on the tile
     * @param x is the column index
     * @param y is the row index
     */
    private void enableTile(int x, int y){
        canSend[x][y] = true;
        ObservableList<Node> childrenStack = ((StackPane) getTileByIndex(x, y, gridTable)).getChildren();
        int stackSize = childrenStack.size();
        if (stackSize == 0){
            childrenStack.add(new ImageView(overlay));
            stackSize = childrenStack.size();
        }

        if (childrenStack.get(stackSize-1).getId() != null && (childrenStack.get(stackSize-1).getId().equals("workerOnBoard")
                ||  childrenStack.get(stackSize-1).getId().equals("highlightWorker"))) {
            childrenStack.get(stackSize - 2).setId("enabledTile");
            childrenStack.get(stackSize - 1).setId("highlightWorker");
        }
        else
            childrenStack.get(stackSize-1).setId("enabledTile");
    }

    /** Disables responsiveness on the tile
     * @param x is the column index
     * @param y is the row index
     */
    private void disableTile(int x, int y){
        canSend[x][y] = false;
        ObservableList<Node> childrenStack = ((StackPane) getTileByIndex(x, y, gridTable)).getChildren();
        int stackSize = childrenStack.size();
        if (stackSize == 0){
            childrenStack.add(new ImageView(overlay));
            stackSize = childrenStack.size();
        }

        if (childrenStack.get(stackSize-1).getId() != null && (childrenStack.get(stackSize-1).getId().equals("workerOnBoard") ||
                childrenStack.get(stackSize-1).getId().equals("highlightWorker"))) {
            childrenStack.get(stackSize - 2).setId("disabledTile");
            childrenStack.get(stackSize - 1).setId("workerOnBoard");
        }
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

    /** Saves and prints the changed tiles of the updated board
     * @param slimBoard is the slimboard received from the server
     */
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

    /**
     * Updates the worker's choice depending on the possible actions.
     * @param request   Action to be managed.
     */
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
